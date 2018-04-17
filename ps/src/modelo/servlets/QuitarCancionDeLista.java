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
import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionNoExisteEnLista;
import modelo.excepcion.SesionInexistente;

@WebServlet("/QuitarCancionDeLista")
public class QuitarCancionDeLista extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String tituloCancion = request.getParameter("tituloCancion");
		String nombreArtista = request.getParameter("nombreArtista");
		String nombreAlbum = request.getParameter("nombreAlbum");
		String nombreLista = request.getParameter("nombreLista");
		
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
				f.quitarCancionDeLista(new formarVO(tituloCancion, nombreArtista, nombreAlbum, nombreLista, nombreUsuario));
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (CancionNoExisteEnLista l) {
				obj.put("CancionYaExisteEnLista", l.toString());

				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (SQLException e){
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", "Error SQL en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
