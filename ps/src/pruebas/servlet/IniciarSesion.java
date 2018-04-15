package pruebas.servlet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.*;
import org.json.simple.parser.*;

/*
 * Prueba el servlet de inicio de sesión, introduciendo un usuario y la
 * contraseña de este usuario
 */
public class IniciarSesion {

	public static void main(String[] args) {
		try {
			// Creamos las cosas que son necesarios
			URL url = new URL(Datos.URL_SERVER + "IniciarSesion");
			Map<String, Object> params = new LinkedHashMap<>();
	 
			// Metemos los parámetros necesarios y los tratamos
	        params.put("nombre", Datos.USER);
	        params.put("contrasenya", Datos.PASS);
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	            if (postData.length() != 0)
	                postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	                    "UTF-8"));
	        }
	        
	        // Enviamos los parámetros al servlet
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length",
	                String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        // Leemos la respuesta del servlet
	        InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        String login = (String) jsonObject.get("login");
	        String idSesion = (String) jsonObject.get("idSesion");
	        
	        // Comprobamos los parámetros
	        System.out.print("IniciarSesion --> ");
	        if(error != null) {
	        	System.out.println(error);
	        }
	        else if(login==null) {
	        	System.out.println("Error en el login recibido");
	        }
	        else if(idSesion==null) {
	        	System.out.println("Error en el id de sesion recibido");
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
