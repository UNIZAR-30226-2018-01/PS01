package modelo.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet("/ServletBase")
public class ServletBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
	// Variable para guardar los errores

	// Recuperamos los parámetros

	// Comprobamos que no haya parámetros incorrecto
	if (true){
		;
	}
	else{
		try{
			;
		}
		catch(Exception e){
			/* Capturar excepción generada por el DAO en caso de error*/
			;
		}
	}
}
}
