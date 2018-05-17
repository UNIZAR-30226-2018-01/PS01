package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;

/**
 * Servlet que, dado un artista, devuelve todos sus albums
 * Recibe:
 * 	-Las cookies de login e idSesion
 * 	-El parámetro "artista"
 * Devuelve:
 * 	-Un JSON con la clave "albums", cuyo valor asociado es un array de strings
 * 	 en el que cada componente se corresponde con el nombre de un album del
 *   artista
 *  -Un JSON con la clave "error" si algo ha ido mal
 */
@WebServlet("/ObtenerAlbumsArtista")
public class ObtenerAlbumsArtista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerAlbumsArtista() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Definición de variables
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		String artista = request.getParameter("artista");
		ImplementacionFachada f = new ImplementacionFachada();
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(artista == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Artista no recibido");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				obj = f.getAlbumsArtista(artista, nombreUsuario);
				out.println(obj.toJSONString());
			}
			catch(Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String sStackTrace = sw.toString();
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}

}
