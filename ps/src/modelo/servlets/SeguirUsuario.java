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

/**
 * Servlet implementation class SeguirUsuario
 */
@WebServlet("/SeguirUsuario")
public class SeguirUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
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
				new ImplementacionFachada().seguir(nombreSeguidor, nombreSeguido);
			}
			catch (SQLException s) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
