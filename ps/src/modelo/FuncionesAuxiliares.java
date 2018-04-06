package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import java.util.Vector;
import java.sql.Connection;

public class FuncionesAuxiliares {
	public static final String URL_SERVER = "http://127.0.0.1:8080/ps/";

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
	public static Vector<String> obtenerValorColumna(ResultSet r, String c)
			throws SQLException {
		try {
			Vector<String> v = new Vector<String>();
			r.beforeFirst();
			while(r.first()) {
				v.add(r.getString(c));
			}
			return v;
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
}
