package modelo.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.ImplementacionFachada;
import modelo.excepcion.ErrorDejarDeSeguir;

/**
 * Servlet implementation class DejarDeSeguirUsuario
 */
@WebServlet("/DejarDeSeguirUsuario")
public class DejarDeSeguirUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
		// Recuperamos los parámetros y las cookies
		String nombreSeguidor = new String();
		String nombreSeguido = request.getParameter("nombreSeguido");
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null){
			for(Cookie i : cookies){
				if(i.getName().equals("login")){
					nombreSeguidor = i.getValue();
					break;
				}
			}
		}
		else {
			errors.put("CookiesNulas", "El usuario no está logueado.");
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		
		if(!errors.isEmpty()){
			// Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		else {
			try {
				new ImplementacionFachada().dejarDeSeguir(nombreSeguidor, nombreSeguido);
			}
			catch (ErrorDejarDeSeguir s) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
			catch (SQLException s) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
