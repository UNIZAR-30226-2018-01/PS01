package modelo.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONObject;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionYaExiste;

/**
 * Servlet implementation class SubirCanciones
 */
@WebServlet("/SubirCanciones")
@MultipartConfig(fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*20, maxRequestSize=1024*1024*5*5)
public class SubirCanciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaBase = "/usr/local/apache-tomcat-9.0.7/webapps/ps/music/";
       
	public void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// Recuperamos los parámetros y las cookies
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String tituloCancion = new String();
		String nombreArtista = new String();
		String nombreAlbum = new String();
		String genero = new String();
		String lista = request.getParameter("lista");
		
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
			List<Part> fileParts = request.getParts().stream().filter(part -> "fichero".equals(part.getName())).collect(Collectors.toList());
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
			        if (metadata.get("title") != null && new File(rutaBase + nombreUsuario + "/" + metadata.get("title") + ".mp3").exists()) {
						throw new CancionYaExiste("La cancion " + metadata.get("title") + " perteneciente al album"
								+ " " + metadata.get("xmpDM:album") + " subida por el usuario "
								+ nombreUsuario + " ya existe.");
			        }
			        else {
			        	if (metadata.get("title") == null || metadata.get("title").replaceAll("\\s","").equals("")) {
							tituloCancion = "Cancion" + new ImplementacionFachada().solicitarId();
			        	}
			        	else {
			        		tituloCancion = metadata.get("title");
			        	}
			        	File mus = new File(rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3");
				        Files.createFile(mus.toPath());
				        Files.copy(fileContent, mus.toPath(), StandardCopyOption.REPLACE_EXISTING);
				        
				        String aux1 = metadata.get("xmpDM:artist");
				        String aux2 = metadata.get("xmpDM:album");
				        String aux3 = metadata.get("xmpDM:genre");
				        if(aux1 == null || aux1.replaceAll("\\s","").equals("")) {
				        	nombreArtista = "Desconocido";
				        }
				        else {
				        	nombreArtista = aux1;
				        }
				        if(aux2 == null || aux2.replaceAll("\\s","").equals("")) {
				        	nombreAlbum = "Desconocido";
				        }
				        else {
				        	nombreAlbum = aux2;
				        }
				        if(aux3 == null || aux3.replaceAll("\\s","").equals("")) {
				        	genero = "Desconocido";
				        }
				        else {
				        	genero = aux3;
				        }     
			        }
			        
			        String ruta_imagen = new String();
			        Mp3File song = new Mp3File(rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3");
			        if (song.hasId3v2Tag()){
			             ID3v2 id3v2tag = song.getId3v2Tag();
			             byte[] imageData = id3v2tag.getAlbumImage();
			             if (imageData != null) {
				             //converting the bytes to an image
				             BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
				             File f = new File(rutaBase + nombreUsuario + "/" + tituloCancion + ".jpg");
				             ImageIO.write(img, "jpg", f);
				             ruta_imagen = "../ps/music/" + nombreUsuario + "/" + tituloCancion + ".jpg";
			             }
			             else {
			            	 ruta_imagen = "nada";
			             }
			        }
			        
					ImplementacionFachada f = new ImplementacionFachada();
					FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
					f.anyadirCancionUsuario(new cancionVO(
							tituloCancion, nombreArtista, nombreAlbum, genero, nombreUsuario,
							rutaBase + nombreUsuario + "/" + tituloCancion + ".mp3",
							ruta_imagen), lista);
					out.println(obj.toJSONString());
				}
				catch(Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String sStackTrace = sw.toString();
					// Metemos el objeto de error en el JSON
					obj.put("error", e.toString());
					
					// Respondemos con el fichero JSON
					out.println(obj.toJSONString());
				}
		    }
				
		}
	}
}
