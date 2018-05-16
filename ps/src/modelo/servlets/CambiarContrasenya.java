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
import modelo.excepcion.ErrorCambiarPass;
import modelo.excepcion.SesionInexistente;

/**
 * Servlet que cambia la contraseña del usuario
 * Recibe:
 * 	-Las cookies de login y de idSesion
 * 	-'viejaPass', que será la vieja contrasenya
 *  -'nuevaContrasenya', que será la nueva contrasenya
 *  -'nuevaContrasenyaR', que será la nueva contrasenya
 * Devuelve:
 *  -Un JSON vacío si todo ha ido bien
 *  -Un JSON con la clave error si ha habido algún problema
 */
@WebServlet("/CambiarContrasenya")
public class CambiarContrasenya extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CambiarContrasenya() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Definición de variables
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		String viejaPass = request.getParameter("viejaPass");
		String nuevaPass = request.getParameter("nuevaContrasenya");
		String nuevaPassR = request.getParameter("nuevaContrasenyaR");
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		// Nueva contraseña incorrecta
		else if(!nuevaPass.equals(nuevaPassR) ||
				!FuncionesAuxiliares.comprobarContrasenya(nuevaPass)) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "La contrasenya no es válida");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				f.cambiarContrasenyaUsuario(nombreUsuario, viejaPass, nuevaPass);
				out.println(obj.toJSONString());
			}
			catch (SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (ErrorCambiarPass e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (SQLException e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}	
	}
}
