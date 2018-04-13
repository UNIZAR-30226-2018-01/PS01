package modelo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.SesionInexistente;

/**
 * Servlet implementation class SeguirUsuario
 */
@WebServlet("/SeguirUsuario")
public class SeguirUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreSeguidor = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String nombreSeguido = request.getParameter("nombreSeguido");
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreSeguidor == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreSeguidor, idSesion);
				f.seguir(nombreSeguidor, nombreSeguido);
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SQLException e){
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", "Error SQL en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
