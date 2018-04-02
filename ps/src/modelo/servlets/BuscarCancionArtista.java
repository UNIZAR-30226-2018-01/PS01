package modelo.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.SesionInexistente;

/*
 * Servlet que busca una(s) canción(s) en la BD dado un artista.
 * Recibe como parámetro el nombre del artista (parámetro artista) y dos
 * cookies, la del nombre de usuario (login) y la de el id de sesión (idSesión)
 * y devuelve un vector dinámico llamado "canciones" de cancionesVO.
 */
@WebServlet("/BuscarCancionArtista")
public class BuscarCancionArtista extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarCancionArtista() {
        super();
        // TODO Auto-generated constructor stub
    }


    public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
	
		// Recuperamos los parámetros
		String artista = request.getParameter("artista");
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
				Vector<cancionVO> v = f.buscarCancionPorArtista(artista, nombreUsuario);
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
