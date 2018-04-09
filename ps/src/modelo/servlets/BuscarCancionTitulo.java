package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.SesionInexistente;
import org.json.simple.*;

/*
 * Servlet que busca una canción en la BD dado un título.
 * Recibe como parámetro el título de la canción (parámetro titulo) y dos
 * cookies, la del nombre de usuario (login) y la de el id de sesión (idSesión).
 * Devuelve un json con las siguientes claves:
 * 	-error, si el usuario no está logeado en el servidor
 *  -CancionInexistente, si no existe la canción buscada
 *  -canciones, que consiste en un array en los que cada componente está
 *  compuesta por una canción
 */
@WebServlet("/BuscarCancionTitulo")
public class BuscarCancionTitulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Definición de variables
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		String titulo = request.getParameter("titulo");
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else{
			try{
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				obj = f.buscarCancionPorTitulo(titulo, nombreUsuario);
				out.println(obj.toJSONString());
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (CancionNoExiste e) {
				// Metemos un array vacío en el JSON
				obj.put("CancionInexistente", "La canción buscada no existe");
				
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
