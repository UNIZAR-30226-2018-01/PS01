package modelo;

import modelo.excepcion.*;
import java.sql.SQLException;

/*
 * Interfaz de la fachada. Incluye las operaciones que se pueden invocar desde
 * los servlets para insertar o comprobar datos de la BD
 */
public interface InterfazFachada {
	/*
	 * Pre:  'nombreUsuario' es el nombre del usuario y 'hashPass' el nombre de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de su contraseña, comprueba
	 * 		 si existe una entrada en la base de datos con ese nombre de usuario
	 * 		 y ese hash de contraseña. En caso contrario, lanza una excepción
	 */
	public void iniciarSesion(String nombreUsuario, String hashPass) 
			throws LoginInexistente, SQLException;
}
