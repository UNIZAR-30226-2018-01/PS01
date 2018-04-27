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

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.SesionInexistente;

/**
 * Servlet implementation class BorrarCancion
 */
@WebServlet("/BorrarCancion")
public class BorrarCancion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {		
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String uploader = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String tituloCancion = request.getParameter("tituloCancion");
		String nombreArtista = request.getParameter("nombreArtista");
		String nombreAlbum = request.getParameter("nombreAlbum");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		// Comprobamos que no haya parámetros incorrecto
		if (uploader == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (tituloCancion == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Título de canción no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (nombreArtista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Nombre de artista no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (nombreAlbum == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Nombre de álbum no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(uploader, idSesion);
				f.quitarCancionUsuario(new cancionVO(tituloCancion, nombreArtista, nombreAlbum, "", uploader, ""));
				out.println(obj.toJSONString());
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (CancionNoExiste e) {
				// Metemos un array vacío en el JSON
				obj.put("CancionInexistente", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (SQLException e){
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
