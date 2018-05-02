package modelo.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * Servlet implementation class ReproducirCancion
 */
@WebServlet("/ReproducirCancion")
public class ReproducirCancion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReproducirCancion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String titulo = request.getParameter("titulo");
		String artista = request.getParameter("artista");
		String album = request.getParameter("album");
		String uploader = request.getParameter("uploader");
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(titulo == null || artista == null || album == null || uploader == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "Titulo, artista, album  y uploader no pueden ser nulos");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				//f.anyadirReproduccion(nombreUsuario, titulo, artista, album, uploader);
				String ruta = f.obtenerRuta(titulo, artista, album, nombreUsuario);
				
				// Enviamos la canción
				FileInputStream in = new FileInputStream(ruta);
				byte[] buffer = new byte[4096];
				int length;
				while ((length = in.read(buffer)) > 0){
					String text = new String(buffer, "UTF-8");
					char[] chars = text.toCharArray();
				    out.write(chars, 0, length);
				}
				in.close();
				out.flush();
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
