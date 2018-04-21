package pruebas.servlet;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReproducirCancion {

	public static void execute(String login, String idSesion, String titulo,
			String artista, String album) {
		try {
			URL url = new URL(Probar.URL_SERVER + "ReproducirCancion");
			Map<String, Object> params = new LinkedHashMap<>();
			
			params.put("titulo", titulo);
			params.put("artista", artista);
			params.put("album", album);
			StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	            if (postData.length() != 0)
	                postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	                    "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length",
	                String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Cookie", "login=" + login +
					"; idSesion=" + idSesion);
	        conn.getOutputStream().write(postDataBytes);
	        
	        // Leemos la canción
	        InputStream response = conn.getInputStream();
	        byte[] array = new byte[1000]; // buffer temporal de lectura.
			int leido = response.read(array);
			FileOutputStream fos = new FileOutputStream("/home/david/music.mp3");
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = response.read(array);
			}
			fos.close();
			System.out.print("ReproducirCancion --> ");
        	System.out.println("CORRECTO!");
	        
	        // Leemos los parámetros
	        /*InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        
	        // Comprobamos los parámetros
	        System.out.print("BorrarListaDeReproduccion --> ");
	        if(error != null) {
	        	System.out.println(error);
	        }
	        else{
	        	// Lectura del ficehro de música
				byte[] array = new byte[1000]; // buffer temporal de lectura.
				int leido = response.read(array);
				FileOutputStream fos = new FileOutputStream("/home/david/music.mp3");
				while (leido > 0) {
					fos.write(array, 0, leido);
					leido = response.read(array);
				}
				fos.close();
	        	System.out.println("CORRECTO!");
	        }*/
		}
		catch(MalformedURLException e) {
			System.out.println("URL no existente");
		}
		catch(Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
		}
	}
}
