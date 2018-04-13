package modelo.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ContentHandler;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionYaExiste;
import modelo.excepcion.SesionInexistente;

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
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String tituloCancion = "cancion_2";//request.getParameter("tituloCancion");
		String nombreArtista = "artista_1";//request.getParameter("nombreArtista");
		String nombreAlbum = "album_1";//request.getParameter("nombreAlbum");
		String genero = "genero_1";//request.getParameter("genero");
		
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
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
					}
		        	catch (CancionYaExiste c) {
		        		// Metemos un array vacío en el JSON
						obj.put("CancionYaExiste", c.toString());
						
						// Respondemos con el fichero JSON
						out.println(obj.toJSONString());
					}
		        }
		        else {
		        	File mus = new File(rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3");
			        Files.createFile(mus.toPath());
			        Files.copy(fileContent, mus.toPath(), StandardCopyOption.REPLACE_EXISTING);
			        InputStream input = new FileInputStream(mus);
			        DefaultHandler handler = new DefaultHandler();
			        Metadata metadata = new Metadata();
			        Parser parser = new Mp3Parser();
			        ParseContext parseCtx = new ParseContext();
			        try {
						parser.parse(input, handler, metadata, parseCtx);
					} catch (SAXException | TikaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        input.close();
			        String[] metadataNames = metadata.names();
		        }
		    }
			System.out.println("Fichero subido");
			System.out.println("Insertando canción en la base de datos 1...");
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				f.existeSesionUsuario(nombreUsuario, idSesion);
				f.anyadirCancionUsuario(new cancionVO(tituloCancion, nombreArtista,
						nombreAlbum, genero, nombreUsuario, "music/" + nombreUsuario + "/" + tituloCancion + ".mp3"));
				System.out.println("Canción guardada con éxito");
			}
			catch(SesionInexistente e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", "Usuario no logeado en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch (CancionYaExiste c) {
				System.out.println("Excepción CancionYaExiste");
				// Metemos un array vacío en el JSON
				obj.put("CancionYaExiste", c.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
			catch(SQLException e){
				e.printStackTrace();
				// Metemos el objeto de error en el JSON
				obj.put("error", "Error SQL en el servidor");
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
