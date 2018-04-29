package modelo.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

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
	private String rutaBase = "/usr/local/apache-tomcat-9.0.7/webapps/ps/music/";
       
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String tituloCancion = new String();
		String nombreArtista = new String();
		String nombreAlbum = new String();
		String genero = new String();
		
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			System.out.println("Comenzando subida de fichero...");
			if (!new File(rutaBase + nombreUsuario + "/").exists()) {
	        	Files.createDirectory(new File(rutaBase + nombreUsuario + "/").toPath());
	        }
			
			// Retrieves <input type="file" name="file" multiple="true">
			List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
			for (Part filePart : fileParts) {
				System.out.println("Subiendo fichero...");
		        //fileName = Paths.get(filePart.getName()).getFileName().toString();
		        InputStream fileContent = filePart.getInputStream();
		        BodyContentHandler handler = new BodyContentHandler();
		        FileInputStream inputstream = (FileInputStream) filePart.getInputStream();
		        Metadata metadata = new Metadata();
		        Parser parser = new Mp3Parser();
		        ParseContext parseCtx = new ParseContext();
		        
		        try {
					parser.parse(inputstream, handler, metadata, parseCtx);
				} catch (SAXException | TikaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        if (new File("music/" + nombreUsuario + "/" + metadata.get("title") + ".mp3").exists()) {
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
		        	File mus = new File(rutaBase + nombreUsuario + "/" + metadata.get("title") + ".mp3");
			        Files.createFile(mus.toPath());
			        Files.copy(fileContent, mus.toPath(), StandardCopyOption.REPLACE_EXISTING);
			        //InputStream input = new FileInputStream(mus);
			        
			        tituloCancion = metadata.get("title");
			        nombreArtista = metadata.get("xmpDM:artist");
			        nombreAlbum = metadata.get("xmpDM:album");
			        genero = metadata.get("xmpDM:genre");
		        }
		        
				try {
					ImplementacionFachada f = new ImplementacionFachada();
					f.existeSesionUsuario(nombreUsuario, idSesion);
					f.anyadirCancionUsuario(new cancionVO(tituloCancion, nombreArtista,
							nombreAlbum, genero, nombreUsuario, rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3"));
					out.println(obj.toJSONString());
				}
				catch(SesionInexistente e) {
					// Metemos el objeto de error en el JSON
					obj.put("error", e.toString());
					
					// Respondemos con el fichero JSON
					out.println(obj.toJSONString());
				}
				catch (CancionYaExiste c) {
					// Metemos un array vacío en el JSON
					obj.put("CancionYaExiste", c.toString());
					
					// Respondemos con el fichero JSON
					out.println(obj.toJSONString());
				}
				catch(SQLException e){
					e.printStackTrace();
					// Metemos el objeto de error en el JSON
					obj.put("error", e.toString());
					
					// Respondemos con el fichero JSON
					out.println(obj.toJSONString());
				}
		    }
				
		}
	}
}
