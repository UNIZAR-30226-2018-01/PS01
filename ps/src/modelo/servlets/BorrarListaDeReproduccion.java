package modelo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.listaReproduccionVO;
import modelo.excepcion.ListaNoExiste;
import modelo.excepcion.SesionInexistente;

@WebServlet("/BorrarListaDeReproduccion")
public class BorrarListaDeReproduccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String nombreLista = request.getParameter("nombreLista");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				f.borrarListaDeReproduccion(new listaReproduccionVO(nombreLista, nombreUsuario));
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (ListaNoExiste l) {
				obj.put("LoginIncorrecto", l.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SQLException e){
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
