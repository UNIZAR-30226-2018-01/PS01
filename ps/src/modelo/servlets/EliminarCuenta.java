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
import modelo.excepcion.UsuarioExistente;

/**
 * Servlet que elimina una cuenta del servidor
 * Recibe:
 * 	-Las cookies de login e idSesion
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
				f.existeNombreUsuario(nombreUsuario);
				
				// Metemos el objeto de error en el JSON
				obj.put("error", "El usuario " + nombreUsuario + 
						" no está registrado");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(UsuarioExistente e) {
				try {
					// Eliminamos la cuenta si existe el usuario
					f.eliminarCuenta(nombreUsuario);
					out.println(obj.toJSONString());
				}
				catch(Exception x) {
					// Metemos el objeto de error en el JSON
					obj.put("error", x.toString());
					
					// Respondemos con el fichero JSON
					out.println(obj.toJSONString());
				}
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
