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
import modelo.GestorDeConexionesBD;

public class FuncionesAuxiliares {
<<<<<<< HEAD
=======
	public static final String URL_SERVER = "http://127.0.0.1:8080/ps/";
>>>>>>> b5b59b3b6ddc993287c1ed63a2c957ae1392cfb8

	private FuncionesAuxiliares() {}
	
	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	public static Connection obtenerConexion() throws SQLException {

		//try {
		//	Class.forName("com.mysql.jdbc.Driver");
		/*try {
>>>>>>> b5b59b3b6ddc993287c1ed63a2c957ae1392cfb8
			Context initContext = new InitialContext();
			Context c = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) c.lookup("jdbc/prueba"); 
			System.out.println("Creando conexión...");
			return ds.getConnection();
		}
		catch(SQLException s) {
			System.out.println(s.toString());
			return null;
		}
		catch(NamingException e) {
			System.out.println("Error al obtener conexion del pool");
			System.out.println(e.toString());
			return null;
<<<<<<< HEAD
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
=======
		}*/
		return GestorDeConexionesBD.getConnection();
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
}
