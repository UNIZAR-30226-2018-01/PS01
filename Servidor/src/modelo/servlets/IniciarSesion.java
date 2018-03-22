package modelo.servlets;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import modelo.ImplementacionFachada;
import modelo.excepcion.LoginInexistente;
import modelo.excepcion.SesionExistente;
import javax.servlet.annotation.WebServlet;

/*
 * Servlet que se utiliza para autentificar al usuario en el servidor, es decir,
 * para iniciar sesión. Los parametros que ha de recibir en el request son
 * 'nombre', que se corresponde con el nombre de usuario, y hashPass, que se
 * corresponde con el hash de la contraseña del usuario.
 * Si ha ido bien, devuelve las siguientes Cookies:
 * 	-<"login", nombre>
 *  -<"idSesion", idSesion>
 * Si ha ido mal, puede devolverá en el request el siguiente parámetro:
 *   -UsuarioInexistente, lo cual significa que ningún usuario se corresponde
 *   con el nombre y contraseña proporcionados
 */
@WebServlet("/IniciarSesion")
public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGINA_ACTUAL = "inicio.jsp";
	private static final String PAGINA_SIG = "inicio.jsp";
	
	/*
	 * Función auxiliar para calcular el hash. De Stackoverflow
	 */
	private static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	
	/*
	 * Pre: ---
	 * Post: Función que crea el identificador de la sesión.
	 */
	private static String crearIdSesion(String s) {
		String sha1 = "";
	    try {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(s.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return sha1;
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		String idSesion = crearIdSesion(); // Genera un id de sesion
		
		// Recuperamos los parámetros
		String nombre = request.getParameter("nombre");
		String hashPass = request.getParameter("hashPass");
	
		// Comprobamos los parámetros recibidos
		if ((nombre == null) || (nombre.trim().equals("")) || (hashPass == null)
			|| (hashPass.trim().equals(""))) {
			errors.put("LoginIncorrecto", "Parámetros incorrectos");
		}
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
			dispatcher.forward(request, response);
		}
		else{ // Los parámetros estaban bien
			try{
				// Comprobamos si existe el usuario con ese nombre y hash
				new ImplementacionFachada().iniciarSesion(nombre, hashPass);
				new ImplementacionFachada().nuevaSesion(nombre, idSesion);
				response.addCookie(new Cookie ("login", nombre));
				response.addCookie(new Cookie ("idSesion", idSesion));
				response.sendRedirect(PAGINA_SIG);
			}
			catch(LoginInexistente e) {
				request.setAttribute("LoginIncorrecto", e.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
			catch(SesionExistente e) {
				response.addCookie(new Cookie ("login", nombre));
				response.addCookie(new Cookie ("idSesion", idSesion));
				response.sendRedirect(PAGINA_SIG);
			}
			catch(SQLException e){
				RequestDispatcher dispatcher=request.getRequestDispatcher(PAGINA_ACTUAL);
				dispatcher.forward(request, response);
			}
		}
	}
}
