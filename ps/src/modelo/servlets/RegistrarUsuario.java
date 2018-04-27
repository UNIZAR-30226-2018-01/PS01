package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.UsuarioYaRegistrado;
import javax.servlet.annotation.WebServlet;
import org.json.simple.JSONObject;

/* Servlet que registra un usuario en el servidor.
 * Recibe como parámetros:
 * 	-nombre
 * 	-contrasenya
 * 	-contrasenyaRepetida
 * Si ha ido bien, devuelve un JSON vacío.
 * Si ha ido mal, devolverá un JSON con la clave "error".
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
		String pass = request.getParameter("contrasenya");
		String passR = request.getParameter("contrasenyaRepetida");
	
		// Comprobamos los parámetros recibidos
		if (!FuncionesAuxiliares.comprobarNombre(nombre)) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "El usuario no es válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		// Contraseña incorrecta
		else if(!FuncionesAuxiliares.comprobarContrasenya(pass)
				|| !pass.equals(passR)) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "La contraseña no es válida");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else{ // Los parámetros recibidos son correctos
			try{
				new ImplementacionFachada().registrarUsuario(nombre,
						FuncionesAuxiliares.crearHash(pass));
				
				// Respondemos con el fichero JSON vacío
				out.println(obj.toJSONString());
			}
			catch(UsuarioYaRegistrado e){
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SQLException e) {
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
