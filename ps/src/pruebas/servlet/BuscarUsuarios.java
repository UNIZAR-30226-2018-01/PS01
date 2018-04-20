package pruebas.servlet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BuscarUsuarios {

	public static void execute(String login, String idSesion, String nombre) {
		try {
			// Creamos las cosas que son necesarias
			URL url = new URL(Probar.URL_SERVER + "BuscarUsuarios");
			Map<String, Object> params = new LinkedHashMap<>();
	 
			// Metemos los parámetros necesarios y los tratamos
	        params.put("usuario", nombre);
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	            if (postData.length() != 0)
	                postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	                    "UTF-8"));
	        }
	        
	        // Enviamos los parámetros
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
	        
	        // Leemos los parámetros
	        InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        JSONArray usuarios = (JSONArray) jsonObject.get("usuarios");
	        
	        // Comprobamos los parámetros
	        System.out.print("BuscarUsuarios --> ");
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
