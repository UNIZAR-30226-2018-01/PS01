package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.compartirVO;

/**
 * Servlet que permite eliminar una compartición de una canción
 * Recibe:
 * 	-Las cookies de login e idSesion
 *  -"ruta", que es la ruta de la canción a descompartir
 * Devuelve:
 * 	-Un JSON vacío si todo ha ido bien
 *  -Un JSON con la clave error si algo ha ido mal
 */
@WebServlet("/EliminarComparticion")
public class EliminarComparticion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		String ruta = request.getParameter("ruta");
		
		if (nombreUsuario == null || idSesion == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(ruta == null) {
			obj.put("error", "Parámetro \"ruta\" no recibido");
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				f.eliminarComparticion(new compartirVO(ruta, "", nombreUsuario));
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
