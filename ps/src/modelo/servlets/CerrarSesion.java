package modelo.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.ImplementacionFachada;
import modelo.excepcion.*;

@WebServlet("/CerrarSesion")
public class CerrarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			Cookie j = null, k = null;
			Cookie[] cookies = request.getCookies();
			String nombreUsuario = new String();
			String idSesion = new String();
			
			if(cookies != null){
				for(Cookie i : cookies){
					if(i.getName().equals("login")){
						j = i;
						nombreUsuario = i.getValue();
					}
					if(i.getName().equals("idSesion")) {
						k = i;
						idSesion = i.getValue();
					}
				}
				j.setMaxAge(0);
				k.setMaxAge(0);
				response.addCookie(j);
				response.addCookie(k);
				new ImplementacionFachada().cerrarSesion(nombreUsuario, idSesion);
				response.sendRedirect(PAGINA_SIG);
			}
		}
		catch (SesionInexistente u) {
			request.setAttribute("ErrorDesloguear", u.toString());
			RequestDispatcher dispatcher = request.getRequestDispatcher(PAGINA_ACTUAL);
			dispatcher.forward(request, response);
		}
		catch (SQLException s) {
			RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
			dispatcher.forward(request, response);
		}
	}
}
