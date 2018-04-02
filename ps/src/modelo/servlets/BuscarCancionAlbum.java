package modelo.servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.SesionInexistente;
import modelo.clasesVO.cancionVO;

/*
 * Servlet que busca una canción en la BD dado un título.
 * Recibe como parámetro el album de la canción (parámetro album) y dos
 * cookies, la del nombre de usuario (login) y la de el id de sesión (idSesión)
 * y devuelve un vector dinámico llamado "canciones" de cancionesVO.
 */
@WebServlet("/BuscarCancionAlbum")
public class BuscarCancionAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
	
		// Recuperamos los parámetros
		String album = request.getParameter("album");
		Cookie[] c = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(c, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(c, "idSesion");
		
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			errors.put("CookiesNulas", "El usuario no está logueado.");
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		else{
			try{
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				Vector<cancionVO> v = f.buscarCancionPorAlbum(album, nombreUsuario);
				request.setAttribute("canciones", v);
			}
			catch(SesionInexistente e) {
				errors.put("CookiesNulas", "El usuario no está logueado.");
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch (CancionNoExiste e) {
				errors.put("CancionNoExiste", "La cancion buscada no existe");
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch(SQLException e){
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
		}
	}
}
