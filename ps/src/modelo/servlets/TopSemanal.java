package modelo.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet que devuelve las 10 canciones más escuchadas la última semana
 * Recibe:
 * 	-Las cookies de login e idSesion
 * Devuelve:
 * 	-Si ha ido bien, un JSON con la clave "canciones", cuyo valor asociado es
 * 	 un array de canciones. Cada una de estas canciones tiene las claves
 * 	 tituloCancion, nombreArtista y nombreAlbum
 *  -Si ha ido mal, un JSON con la clave "error".
 */
@WebServlet("/TopSemanal")
public class TopSemanal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopSemanal() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Definición de variables
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		ImplementacionFachada f = new ImplementacionFachada();
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				f.existeSesionUsuario(nombreUsuario, idSesion);
				obj = f.topSemanal();
				out.println(obj.toJSONString());
			}
			catch(Exception e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
