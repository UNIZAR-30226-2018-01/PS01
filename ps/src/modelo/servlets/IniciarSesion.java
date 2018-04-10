package modelo.servlets;

import java.io.*;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
//import java.util.Formatter;
//import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.LoginInexistente;
import modelo.excepcion.SesionExistente;
import javax.servlet.annotation.WebServlet;
import org.json.simple.*;

/*
 * Servlet que se utiliza para autentificar al usuario en el servidor, es decir,
 * para iniciar sesión. Los parametros que ha de recibir en el request son
 * 'nombre', que se corresponde con el nombre de usuario, y hashPass, que se
 * corresponde con el hash de la contraseña del usuario.
 * Si ha ido bien, devuelve las siguientes Cookies:
 * 	-<"login", nombre>
 *  -<"idSesion", idSesion>
 * Si ha ido mal, puede devolverá en el request el siguiente parámetro:
 *   -UsuarioInexistente, lo cual significa que ningún usuario se corresponde
 *   con el nombre y contraseña proporcionados
 */
@WebServlet("/IniciarSesion")
public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Definición de variables
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		String nombre = request.getParameter("nombre");
		String hashPass = request.getParameter("hashPass");
		String idSesion = FuncionesAuxiliares.
				crearHash(System.currentTimeMillis() + nombre); // Genera un id de sesion
	
		// Comprobamos los parámetros recibidos
		if ((nombre == null) || (nombre.trim().equals("")) || (hashPass == null)
			|| (hashPass.trim().equals(""))) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Parámetros incorrectos");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else{ // Los parámetros estaban bien
			try{
				// Comprobamos si existe el usuario con ese nombre y hash
				new ImplementacionFachada().iniciarSesion(nombre, hashPass);
				new ImplementacionFachada().nuevaSesion(nombre, idSesion);
				
				// Metemos los objetos en un objeto JSON
				obj.put("login", nombre);
				obj.put("idSesion", idSesion);
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(LoginInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SesionExistente e) {
				// Metemos los objetos en un objeto JSON
				obj.put("login", nombre);
				obj.put("idSesion", idSesion);
				
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
