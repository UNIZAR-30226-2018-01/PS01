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
import modelo.excepcion.NoHayListas;
import modelo.excepcion.NoSeguido;
import modelo.excepcion.SesionInexistente;

/**
 * Servlet que le devuelve al usuario un JSON con la clave "listas", cuyo valor
 * asociado es un array de strings que contiene el nombre de todas las listas
 * de 'user'.
 * Ha de recibir como parámetro dos cookies, la del login y la del idSesion,
 * además de un parámetro llamado "user", que será el usuario del que se 
 * recuperarán sus listas de reproducción. 'user' ha de ser o él mismo o una
 * persona a la que sigue.
 * En caso de que haya habido algún error, no devolverá la clave listas y, en
 * cambio, devolverá una clave llamada "error".
 */
@WebServlet("/MostrarListasReproduccion")
public class MostrarListasReproduccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Definición de variables
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String user = request.getParameter("user");
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(user == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "No se ha especificado un usuario del que extraer las listas");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				// Si no es una lista propia, comprobar que sea de un seguido
				if(!user.equals(nombreUsuario)) {
					f.loSigue(nombreUsuario, user);
				}
				obj = f.obtenerListasReproducción(user);
				out.println(obj.toJSONString());
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(NoHayListas e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(NoSeguido e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
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
