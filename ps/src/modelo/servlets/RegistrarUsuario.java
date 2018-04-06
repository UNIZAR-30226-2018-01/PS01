package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;

import modelo.ImplementacionFachada;
import modelo.excepcion.UsuarioYaRegistrado;
import javax.servlet.annotation.WebServlet;


/* Servlet que se utiliza para registrar a un usuario en el servidor. Los
 * parametros que ha de recibir en el request son "nombre", que se corresponde
 * con el nombre de usuario, y hashPass, que se corresponde con el hash de la
 * contraseña del usuario. Si ha ido mal, puede devolver en el request los
 * siguientes parámetros:
 *   -InicioSesion, lo cual significa que el nombre de usuario escogido o la
 *   contraseña escogida es inválida.
 *   -UsuarioYaRegistrado, lo cual significa que ya hay un usuario registrado
 *   con el nombre proporcionado por el usuario
 */
@WebServlet("/RegistrarUsuario")
public class RegistrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
	
		// Recuperamos los parámetros
		String nombre = request.getParameter("nombre");
		String hashPass = request.getParameter("hashPass");
	
		// Comprobamos los parámetros recibidos
		if ((nombre == null) || (nombre.trim().equals("")) || (hashPass == null)
			  || (hashPass.trim().equals("")) || nombre.length()<4 || 
			  nombre.length()>32) {
			errors.put("registroUsuario", "Parámetros incorrectos");
		}
		
		if (!errors.isEmpty()){ // Los parámetros recibidos son incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
			dispatcher.forward(request, response);
		}
		else{ // Los parámetros recibidos son correctos
			try{
				new ImplementacionFachada().registrarUsuario(nombre, hashPass);
				response.sendRedirect(PAGINA_SIG);
			}
			catch(UsuarioYaRegistrado e){
				request.setAttribute("UsuarioYaRegistrado", e.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch(SQLException e) {
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
		}
	}
}
