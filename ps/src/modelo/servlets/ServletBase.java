package modelo.servlets;

import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet("/ServletBase")
public class ServletBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
	
		// Recuperamos los parámetros
	
		// Comprobamos que no haya parámetros incorrecto
		if (true){
			;
		}
		else{
			try{
				;
			}
			/*catch(ExcepcionTuya e) {
				;
			}*/
			catch(Exception /* SQLException */e){
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
		}
	}
}
