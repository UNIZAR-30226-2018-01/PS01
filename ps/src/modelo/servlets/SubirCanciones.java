package modelo.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import modelo.ImplementacionFachada;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionYaExiste;

/**
 * Servlet implementation class SubirCanciones
 */
@WebServlet("/SubirCanciones")
@MultipartConfig(fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*10, maxRequestSize=1024*1024*5*5)
public class SubirCanciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaBase = "/Users/albertomurrodrigo/Documents/dg/PS01/ps/music/";
       
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Variable para guardar los errores
		HashMap<String, String> errors = new HashMap <String, String>();
		
		// Recuperamos los parámetros y las cookies
		String nombreUsuario = new String();
		String tituloCancion = "cancion_2";//request.getParameter("tituloCancion");
		String nombreArtista = "artista_1";//request.getParameter("nombreArtista");
		String nombreAlbum = "album_1";//request.getParameter("nombreAlbum");
		String genero = "genero_1";//request.getParameter("genero");
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null){
			for(Cookie i : cookies){
				if(i.getName().equals("login")){
					nombreUsuario = i.getValue();
					break;
				}
			}
		}
		else {
			errors.put("CookiesNulas", "El usuario no está logueado.");
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		
		if (!new File(rutaBase + nombreUsuario + "/").exists()) {
        	Files.createDirectory(new File(rutaBase + nombreUsuario + "/").toPath());
        }
		
		// Retrieves <input type="file" name="file" multiple="true">
		List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
		for (Part filePart : fileParts) {
	        //fileName = Paths.get(filePart.getName()).getFileName().toString();
	        InputStream fileContent = filePart.getInputStream();
	        if (new File("music/" + nombreUsuario + "/" + tituloCancion + ".mp3").exists()) {
	        	try {
					throw new CancionYaExiste("La cancion " + tituloCancion + " perteneciente al álbum"
							+ " " + nombreAlbum + " subida por el usuario "
							+ nombreUsuario + " ya existe.");
				} catch (CancionYaExiste c) {
					request.setAttribute("CancionYaExiste", c.toString());
					RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
					dispatcher.forward(request, response);
				}
	        }
	        else {
		        Files.createFile(new File(rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3").toPath());
		        Files.copy(fileContent, new File(rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3").toPath(), StandardCopyOption.REPLACE_EXISTING);
	        }
	    }
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		else {
			try {
				new ImplementacionFachada().anyadirCancionUsuario(new cancionVO(tituloCancion, nombreArtista,
						nombreAlbum, genero, nombreUsuario, "music/" + nombreUsuario + "/" + tituloCancion + ".mp3"));
			}
			catch (CancionYaExiste c) {
				request.setAttribute("CancionYaExiste", c.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
			catch (SQLException s) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
