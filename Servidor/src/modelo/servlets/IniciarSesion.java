package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.ImplementacionFachada;
import modelo.excepcion.LoginInexistente;
import javax.servlet.annotation.WebServlet;

/*
 * Servlet que se utiliza para autentificar al usuario en el servidor, es decir,
 * para iniciar sesión. Los parametros que ha de recibir en el request son
 * "nombre", que se corresponde con el nombre de usuario, y hashPass, que se
 * corresponde con el hash de la contraseña del usuario. Si ha ido mal, puede
 * devolverá en el request el siguiente parámetro:
 *   -UsuarioInexistente, lo cual significa que ningún usuario se corresponde
 *   con el nombre y contraseña proporcionados
 */
@WebServlet("/IniciarSesion")
public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	/*
	 * Pre: ---
	 * Post: Función que crea el identificador de la sesión.
	 */
	private static String crearIdSesion() {
		return new String();
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
			
		// Recuperamos los parámetros
		String nombre = request.getParameter("nombre");
		String hashPass = request.getParameter("hashPass");
	
		// Comprobamos los parámetros recibidos
		if ((nombre == null) || (nombre.trim().equals("")) || (hashPass == null)
			|| (hashPass.trim().equals(""))) {
			errors.put("LoginIncorrecto", "Parámetros incorrectos");
		}
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
			dispatcher.forward(request, response);
		}
		else{ // Los parámetros estaban bien
			try{
				new ImplementacionFachada().iniciarSesion(nombre, hashPass);
				Cookie cookieLogin = new Cookie ("login", nombre);
				Cookie cookieClave = new Cookie ("idSesion", crearIdSesion());
				response.addCookie(cookieLogin);
				response.addCookie(cookieClave);
				response.sendRedirect(PAGINA_SIG);
			}
			catch(LoginInexistente e) {
				request.setAttribute("LoginIncorrecto", e.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch(SQLException e){
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
		}
	}
}
