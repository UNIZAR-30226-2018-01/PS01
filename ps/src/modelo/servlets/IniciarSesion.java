package modelo.servlets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.LoginInexistente;
import modelo.excepcion.SesionExistente;
import javax.servlet.annotation.WebServlet;
import org.json.simple.*;

/*
 * Servlet que inicia una sesión del usuario en el servidor.
 * Recibe como parámetros:
 * 	-nombre, que es el nombre del usuario.
 * 	-contrasenya, que es la contraseña del usuario.
 * Si ha ido bien, devuelve un JSON con las claves nombre e idSesion.
 * Si ha ido mal, devolverá un JSON con la clave "error".
 */
@WebServlet("/IniciarSesion")
public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Definición de variables
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		String nombre = request.getParameter("nombre");
		String hashPass = FuncionesAuxiliares.crearHash(request.getParameter("contrasenya"));
		String idSesion = FuncionesAuxiliares.
				crearHash(System.currentTimeMillis() + hashPass); // Genera un id de sesion
	
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
				new ImplementacionFachada().existeUsuario(nombre, hashPass);
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
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
