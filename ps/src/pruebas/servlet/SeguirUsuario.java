package pruebas.servlet;

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

public class SeguirUsuario {
	public static void execute(String login, String idSesion, String seguido) {
		try {
			URL url = new URL(Probar.URL_SERVER + "SeguirUsuario");
			Map<String, Object> params = new LinkedHashMap<>();
			
			params.put("login", login);
			params.put("idSesion", idSesion);
			params.put("seguido", seguido);
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
	        // Leemos los parámetros
	        InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        String YaSeguido = (String) jsonObject.get("YaSeguido");
	        String SesionInexistente = (String) jsonObject.get("SesionInexistente");
	        
	        // Comprobamos los parámetros
	        System.out.print("SeguirUsuario --> ");
	        if(error != null) {
	        	System.out.println(error);
	        }
	        else if (YaSeguido != null) {
	        	System.out.println(YaSeguido);
	        }
	        else if (SesionInexistente != null) {
	        	System.out.println(SesionInexistente);
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
