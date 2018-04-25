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

/*
 * Servlet que devuelve todos los géneros musicales que hay en la BD
 * Recibe:
 * 	-Las cookies de login e idSesión
 * Devuelve:
 * 	-Si ha ido bien, un JSON con la clave "generos", cuyo valor asociado es un
 *   array de strings, en el que cada componente incluye un estilo musical
 *  -Si ha ido mal, un JSON con la clave "error".
 */
@WebServlet("/ObtenerGeneros")
public class ObtenerGeneros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerGeneros() {
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
				obj = f.getGeneros(nombreUsuario);
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
