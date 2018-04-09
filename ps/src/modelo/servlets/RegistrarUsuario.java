package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.ImplementacionFachada;
import modelo.excepcion.UsuarioYaRegistrado;
import javax.servlet.annotation.WebServlet;
import org.json.simple.JSONObject;


/* Servlet que se utiliza para registrar a un usuario en el servidor. Los
 * parametros que ha de recibir en el request son "nombre", que se corresponde
 * con el nombre de usuario, y hashPass, que se corresponde con el hash de la
 * contraseña del usuario.
 * Si ha ido bien devuelve un json sin claves, es decir, vacío. 
 * Si ha ido mal, puede devolver las siguientes claves:
 * 	-error, lo cual significa que el usuario o contraseña proporcionados
 *   no cumplen con la sintaxis (tamaño, etc)
 *  -UsuarioRegistrado, que indica que el usuario proporcionado ya existe,
 *   con lo que no se puede registrar
 */
@WebServlet("/RegistrarUsuario")
public class RegistrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Definición de variables
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		String nombre = request.getParameter("nombre");
		String hashPass = request.getParameter("hashPass");
	
		// Comprobamos los parámetros recibidos
		if ((nombre == null) || (nombre.trim().equals("")) || (hashPass == null)
			  || (hashPass.trim().equals("")) || nombre.length()<4 || 
			  nombre.length()>32) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Parámetros incorrectos");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else{ // Los parámetros recibidos son correctos
			try{
				new ImplementacionFachada().registrarUsuario(nombre, hashPass);
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(UsuarioYaRegistrado e){
				// Metemos el objeto de error en el JSON
				obj.put("UsuarioRegistrado", "El usuario proporcionado ya existe");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SQLException e) {
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", "Error SQL en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
