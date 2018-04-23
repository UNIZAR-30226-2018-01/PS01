package modelo.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONObject;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;

/**
 * Servlet que permite el cambio de la foto de perfil
 * Recibe:
 * 	-Las cookies de login e idSesion
 *  -Un parámetro 'foto' del tipo file, que se corresponderá con la foto a subir
 * Devuelve:
 * 	-Un JSON vacío si todo ha ido bien
 *  -Un JSON con la clave error si algo ha ido mal
 */
@WebServlet("/CambiarFotoPerfil")
@MultipartConfig
public class CambiarFotoPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CambiarFotoPerfil() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String rutaBase = "/usr/local/apache-tomcat-9.0.7/webapps/ps/images/";

    public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		Part foto = request.getPart("foto");
		OutputStream img = null;
		InputStream filecontent = null;
		
		// Usuario no logeado
		if (nombreUsuario == null || idSesion == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if (foto == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "No se ha adjuntado ninguna foto");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
}
		else {
			try {
				// Comprobamos que el usuario esté logeado
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				
				// Guardamos la imágen en el servidor
				img = new FileOutputStream(new File(rutaBase + nombreUsuario));
				filecontent = foto.getInputStream();
				int read = 0;
		        final byte[] bytes = new byte[1024];
		        while ((read = filecontent.read(bytes)) != -1) {
		            img.write(bytes, 0, read);
		        }
		        
		        // Actualizamos la ruta de la imagen en la BD
		        f.actualizarImagen(nombreUsuario, rutaBase + nombreUsuario);
		        
		        // Respondemos con un JSON vacío
		        out.println(obj.toJSONString());
			}
			catch(Exception e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			finally {
		        if (img != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
		    }
		}
    }
}
