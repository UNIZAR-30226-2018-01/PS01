package modelo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.ImplementacionFachada;
import modelo.excepcion.*;

@WebServlet("/CerrarSesion")
public class CerrarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		Cookie j = null, k = null;
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = new String();
		String idSesion = new String();
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		try {
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
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				f.cerrarSesion(nombreUsuario, idSesion);
			}
		}
		catch(SesionInexistente e) {
			// Metemos el objeto de error en el JSON
			obj.put("error", e.toString());
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		catch(SQLException e){
			e.printStackTrace();
			// Metemos el objeto de error en el JSON
			obj.put("error", "Error SQL en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
	}
}
