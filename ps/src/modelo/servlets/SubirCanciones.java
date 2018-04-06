package modelo.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
		String nombreUsuario = "alberto";
		String tituloCancion = "cancion_2";//request.getParameter("tituloCancion");
		String nombreArtista = "artista_1";//request.getParameter("nombreArtista");
		String nombreAlbum = "album_1";//request.getParameter("nombreAlbum");
		String genero = "genero_1";//request.getParameter("genero");
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null){
			System.out.println("Cookies no nulas.");
			for(Cookie i : cookies){
				if(i.getName().equals("login")){
					nombreUsuario = i.getValue();
					break;
				}
			}
		}
		else {
			System.out.println("Cookies nulas.");
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
			System.out.println("Subiendo fichero...");
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
		System.out.println("Fichero subido");
		
		if(!errors.isEmpty()){ // Los parámetros eran incorrectos
			System.out.println("El hashmap de errores es no nulo.");
			request.setAttribute("errores", errors);
			RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
			dispatcher.forward(request, response);
		}
		else {
			System.out.println("Insertando canción en la base de datos 1...");
			try {
				new ImplementacionFachada().anyadirCancionUsuario(new cancionVO(tituloCancion, nombreArtista,
						nombreAlbum, genero, nombreUsuario, "music/" + nombreUsuario + "/" + tituloCancion + ".mp3"));
				System.out.println("Canción guardada con éxito");
			}
			catch (CancionYaExiste c) {
				System.out.println("Excepción CancionYaExiste");
				request.setAttribute("CancionYaExiste", c.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
			catch (SQLException s) {
				System.out.println(s.toString());
				RequestDispatcher dispatcher=request.getRequestDispatcher("inicio.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
