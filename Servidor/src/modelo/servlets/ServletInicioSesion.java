package modelo.servlets;

import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.ImplementacionFachada;
import modelo.excepcion.LoginInexistente;
import javax.servlet.annotation.WebServlet;


@WebServlet("/ServletInicioSesion")
public class ServletInicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA = "inicio.jsp";
	
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
			errors.put("inicioSesion", "Parámetros incorrectos");
		}
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA);
			dispatcher.forward(request, response);
		}
		else{ // Los parámetros estaban bien
			try{
				new ImplementacionFachada().iniciarSesion(nombre, hashPass);
				Cookie cookieLogin = new Cookie ("login", nombre);
				Cookie cookieClave = new Cookie ("idSesion", crearIdSesion());
				response.addCookie(cookieLogin);
				response.addCookie(cookieClave);
				response.sendRedirect("inicio.jsp");
			}
			catch(LoginInexistente e) {
				request.setAttribute("errores", e.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA);
				dispatcher.forward(request, response);
			}
			catch(Exception e){
				errors.put("Fallo", e.toString());
				request.setAttribute("errores", errors);
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA);
				dispatcher.forward(request, response);
			}
		}
	}
}
