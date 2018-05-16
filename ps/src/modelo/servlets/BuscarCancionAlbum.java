package modelo.servlets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.SesionInexistente;
import org.json.simple.*;

/*
 * Servlet que busca una canción en la BD dado un album.
 * Recibe como parámetro el album de la canción (parámetro album) y dos
 * cookies, la del nombre de usuario (login) y la de el id de sesión (idSesión).
 * Si todo ha ido bien, devuelve un JSON con la clave canciones, cuyo valor
 * asociado será un array de JSONs. Cada uno de estos JSONs tendrá las claves
 * tittuloCancion, nombreArtisa, nombreAlbum y genero.
 * En caso de que haya habido algún error, no devuelve la clave canciones y,
 * en cambio, devuelve un JSON con la clave error.
 */
@WebServlet("/BuscarCancionAlbum")
public class BuscarCancionAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Definición de variables
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		String album = request.getParameter("album");
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(album == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "No se ha especificado album");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else{
			try{
				ImplementacionFachada f = new ImplementacionFachada();
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				obj = f.buscarCancionPorAlbum(album, nombreUsuario);
				out.println(obj.toJSONString());
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (CancionNoExiste e) {
				// Metemos un array vacío en el JSON
				obj.put("error", e.toString());
				
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
