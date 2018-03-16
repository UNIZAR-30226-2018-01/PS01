package modelo;

import modelo.excepcion.*;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;

public class ImplementacionFachada implements InterfazFachada {

	private static Connection obtenerConexion() {
		Connection c = null;
		return c;
	}
	
	@Override
	public void iniciarSesion(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			usuarioVO u = new usuarioVO(nombreUsuario, hashPass);
			usuarioDAO d = new usuarioDAO();
			Connection c = obtenerConexion();
			d.iniciarSesion(u, c);
		}
		catch(Exception e) {
			throw e;
		}
	}

}
