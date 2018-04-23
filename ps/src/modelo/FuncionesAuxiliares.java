package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Vector;
import java.sql.Connection;

public class FuncionesAuxiliares {
	private FuncionesAuxiliares() {}
	
	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	public static Connection obtenerConexion() throws SQLException {
		try {
			Context initContext = new InitialContext();
			Context c = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) c.lookup("jdbc/pool"); 
			return ds.getConnection();
		}
		catch(NamingException e) {
			System.out.println("Error al obtener conexion del pool");
			System.out.println(e.toString());
			return null;
		}
	}
	
	/*
	 * Pre:
	 * Post: Dado un array de string c y un string s, busca en c si existe una
	 * cookie cuya clave sea s. De ser así, devuelve el valor de esa cookie.
	 * En caso contrario, devuelve null.
	 */
	public static String obtenerCookie(Cookie[] c, String s) {
		for(Cookie i : c) {
			if(i.getName().equals(s)) {
				return i.getValue();
			}
		}
		return null;
	}

	/*
	 * Pre:
	 * Post: Dado un ResultSet, devuelve un vector con todos los valores para
	 * 		 la columna 'c'. Si no había valores, devuelve un Vector vacío
	 */
	public static JSONObject obtenerValorColumna(ResultSet r, String c)
			throws SQLException {
		try {
			JSONObject obj = new JSONObject();
			JSONArray v = new JSONArray();
			r.beforeFirst();
			while(r.next()) {
				v.add(r.getString(c));
			}
			
			obj.put(c, v);
			return obj;
			//return v;
		}
		catch(Exception e) {
			throw e;
		}
	}
		
	
	/*
	 * Pre: ---
	 * Post: Función que a partir de un String genera su hash
	 */
	public static String crearHash(String s) {
		try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve falso si:
	 * 			-nombre es nulo
	 * 			-nombre contiene espacios
	 * 			-La longitud de nombre es menor que 4
	 * 			-La longitud de nombre es mayor que 15
	 * 			-nombre no empieza por una letra
	 * 			-nombre contiene caracteres que no son alfanuméricos
	 * 		  En caso contrario, devuelve verdad
	 */
	public static boolean comprobarNombre(String nombre) {
		if(nombre == null || nombre.contains(" ") || nombre.length()<4 ||
				nombre.length()>15 || !Character.isLetter(nombre.charAt(0))) {
			return false;
		}
		else {
			boolean seguir = true;
			for(int i=1; i<nombre.length() && seguir; i++) {
				if(!Character.isLetterOrDigit(nombre.charAt(i))) {
					seguir = false;
				}
			}
			return seguir;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve falso si:
	 * 			-contrasenya es nula
	 * 			-contrasenya contiene espacios
	 * 			-contrasenya tiene menos de 4 caracteres
	 * 			-contrasenya tiene mas de 4 caracteres
	 * 			-contrasenya tiene caracteres que no son alfanumericos
	 * 			-contrasenya no tiene ninguna letra mayuscula
	 * 			-contrasenya no tiene ningún número
	 */
	public static boolean comprobarContrasenya(String contrasenya) {
		if(contrasenya == null || contrasenya.contains(" ") || contrasenya.length()<4 ||
				contrasenya.length()>15) {
			return false;
		}
		else {
			boolean hayNumero = false;
			boolean hayMayuscula = false;
			boolean seguir = true;
			for(int i=0; i<contrasenya.length() && seguir; i++) {
				if(!Character.isLetterOrDigit(contrasenya.charAt(i))) {
					seguir = false;
				}
				else {
					if(Character.isUpperCase(contrasenya.charAt(i))) {
						hayMayuscula = true;
					}
					else if (Character.isDigit(contrasenya.charAt(i))) {
						hayNumero = true;
					}
				}
			}
			return hayNumero && hayMayuscula && seguir;
		}
	}
}
