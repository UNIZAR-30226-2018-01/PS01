package modelo;

import modelo.excepcion.*;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;

public class ImplementacionFachada implements InterfazFachada {

	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	private static Connection obtenerConexion() {
		Connection c = null;
		return c;
	}
	
	@Override
	public void iniciarSesion(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			new usuarioDAO().iniciarSesion(new usuarioVO(nombreUsuario, hashPass),
					obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public void registrarUsuario(String nombreUsuario, String hashPass)
			throws UsuarioYaRegistrado, SQLException {
		try {
			new usuarioDAO().insertarUsuario(new usuarioVO(nombreUsuario, hashPass),
					obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}

}
