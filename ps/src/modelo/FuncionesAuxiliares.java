package modelo;

import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import com.mysql.jdbc.Connection;

public class FuncionesAuxiliares {

	private FuncionesAuxiliares() {}
	
	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	public static Connection obtenerConexion() throws SQLException {
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/ConexionMySQL");
			return (Connection) ds.getConnection();
		}
		catch(NamingException e) {
			System.out.println("Error al obtener conexion del pool");
		}
		return null;
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

}
