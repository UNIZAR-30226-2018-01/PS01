package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.SesionInexistente;

/*
 * Servlet que añade una canción a una lista de reproducción
 * Recibe:
 * 	-Las cookies de login e idSesion
 *  -El parámetro "ruta", que es la ruta de la canción a meter en la lista
 *  -El parámetro "nombreLista", que es el nombre de la lista en donde se añadirá
 *   la canción.
 * Devuelve:
 *  -Un JSON vacío si todo ha ido bien
 * 	-Un JSON con la clave "error" o "CancionYaExisteEnLista" si algo ha ido mal
 */
@WebServlet("/AnyadirCancionALista")
public class AnyadirCancionALista extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {		
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String ruta = request.getParameter("ruta");
		String nombreLista = request.getParameter("nombreLista");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (ruta == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Ruta no válida");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (nombreLista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Nombre de lista no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				f.anyadirCancionALista(new formarVO(ruta, nombreLista, nombreUsuario));
				out.println(obj.toJSONString());
			}
			catch (CancionExisteEnLista l) {
				obj.put("CancionYaExisteEnLista", l.toString());

				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (Exception e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
