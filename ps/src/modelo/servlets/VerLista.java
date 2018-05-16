package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
import modelo.excepcion.NoHayCanciones;
import modelo.excepcion.SesionInexistente;

/**
 * Servlet para ver el contenido de una lista de reproducción
 * Recibe:
 * 	-Las cookies de login e idSesion
 *  -"nombreLista", que se corresponde con el nombre de la lista
 *  -"nombreCreadorLista", que se corresponde con el nombre del creador de la lista
 * Devuelve:
 *  -Un JSON con la clave "canciones", cuyo valor asociado es un array de
 *   canciones, en el que cada canción contiene un array de strings con
 *   los valores "tituloCancion", "nombreArtista", "nombreAlbum", "genero"
 *   y "ruta".
 *  -Un JSON con la clave "NoHayCanciones" o "error" si algo ha ido mal
 *  
 */
@WebServlet("/VerLista")
public class VerLista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		
		// Recuperamos los parámetros
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		String nombreLista = request.getParameter("nombreLista");
		String creadorLista = request.getParameter("nombreCreadorLista");
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (nombreLista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Nombre de lista no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());			
		}
		else if (creadorLista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Creador de lista no válido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());			
		}
		else{
			try{
				ImplementacionFachada f = new ImplementacionFachada();
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				obj = f.verLista(new listaReproduccionVO(nombreLista, creadorLista));
				out.println(obj.toJSONString());
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(NoHayCanciones e) {
				obj.put("NoHayCanciones", e.toString());

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
