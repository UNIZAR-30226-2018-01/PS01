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
import modelo.clasesVO.listaReproduccionVO;
import modelo.excepcion.*;

@WebServlet("/CrearListaDeReproduccion")
public class CrearListaDeReproduccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
		// Recuperamos los parámetros y las cookies
		String nombreUsuario = new String();
		String nombreLista = request.getParameter("nombreLista");
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null){
			for(Cookie i : cookies){
				if(i.getName().equals("login")){
					nombreUsuario = i.getValue();
					break;
				}
			}
		}
		else {
			errors.put("CookiesNulas", "El usuario no está logueado.");
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		else {
			try {
				new ImplementacionFachada().crearListaDeReproduccion(new listaReproduccionVO(nombreLista, nombreUsuario));
			}
			catch (ListaYaExiste l) {
				request.setAttribute("ListaYaExiste", l.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch (SQLException s) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
