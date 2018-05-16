package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
import modelo.excepcion.UsuarioExistente;

/**
 * Servlet que elimina una cuenta del servidor
 * Recibe:
 * 	-Las cookies de login e idSesion
 * 	-Un parámetro 'pass', que contiene la contraseña del usuario
 * Devuelve:
 * 	-Un JSON vacío si todo ha ido bien
 *  -Un JSON con la clave error si algo ha ido mal
 */
@WebServlet("/EliminarCuenta")
public class EliminarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarCuenta() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Definición de variables
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		String pass = request.getParameter("pass");
		ImplementacionFachada f = new ImplementacionFachada();
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(pass == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Contraseña no recibida");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				f.existeUsuario(nombreUsuario,
						FuncionesAuxiliares.crearHash(pass));
				f.eliminarCuenta(nombreUsuario);
				FuncionesAuxiliares.borrarUsuario(nombreUsuario);
				
				// Respondemos con el fichero JSON vacío
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
