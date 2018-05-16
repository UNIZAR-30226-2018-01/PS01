package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.listaReproduccionVO;

/*
 * Servlet que crea una lista de reproducción para un determinado usuario.
 * Recibe:
 * 	-Las cookies login e idSesion
 *  -Un parámetro 'nombreLista', que será el nombre que el nombre que se quiere
 *  para la lista
 * Devuelve:
 * 	-Un JSON vacío si todo ha ido bien
 * 	-Un JSON con la clave error si se ha producido algún error
 */
@WebServlet("/CrearListaDeReproduccion")
public class CrearListaDeReproduccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String nombreLista = request.getParameter("nombreLista");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(nombreLista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Nombre de lista no proporcionado");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				f.crearListaDeReproduccion(new listaReproduccionVO(nombreLista, nombreUsuario));
				out.println(obj.toJSONString());
			}
			catch (Exception l) {
				obj.put("error", l.toString());

				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
