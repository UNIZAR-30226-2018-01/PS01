package pruebas.servlet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CerrarSesion {

	public static void execute(String login, String idSesion) {
		try {
	        // Enviamos los parámetros
			URL url = new URL(Probar.URL_SERVER + "CerrarSesion");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Cookie", "login=" + login +
	        						"; idSesion=" + idSesion);
	        
	        // Leemos los parámetros
	        InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        
	        // Comprobamos los parámetros
	        System.out.print("CerrarSesion --> ");
	        if(error != null) {
	        	System.out.println(error);
	        }
	        else{
	        	System.out.println("CORRECTO!");
	        } 
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
