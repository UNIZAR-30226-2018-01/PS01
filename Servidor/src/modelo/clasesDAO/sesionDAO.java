package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.sesionVO;
import modelo.excepcion.UsuarioSinLoguear;
import modelo.excepcion.UsuarioYaLogueado;

public class sesionDAO {
	/*
	 * Pre: ---
	 * Post: Ha creado una nueva sesión insertando la correspondiente tupla en la tabla Sesion de la BD.
	 * 		 Si el usuario ya estuviera logueado y se ejecutase esta función, entonces y solo entonces
	 * 		 saltaría una excepción 'UsuarioYaLogueado'.
	 */
	public void insertarSesion(sesionVO sesion, Connection connection)
			throws UsuarioYaLogueado, SQLException, Exception {
		try {
			if (existeSesion(sesion.verNombreUsuario(), connection)) {
				throw new UsuarioYaLogueado("El usuario " + sesion.verNombreUsuario() + " ya está logueado.");
			}
			else {
				String queryString = "INSERT INTO Sesion " +
		                "(hashSesion, nombreUsuario) " +
		            		"VALUES (?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, sesion.verHashSesion());
	        		preparedStatement.setString(2, sesion.verNombreUsuario());
	        		
	        		preparedStatement.executeUpdate();
			}	        
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha borrado la sesión actual de un determinado usuario eliminando la correspondiente
	 * 		 tupla de la tabla Sesion de la BD.
	 * 		 Si el usuario no estuviera logueado y se ejecutase esta función, entonces y solo entonces
	 * 		 saltaría una excepción 'UsuarioYaLogueado'.
	 */
	public void cerrarSesion(String nombreUsuario, Connection connection)
			throws UsuarioSinLoguear, SQLException, Exception {
		try {
			if (existeSesion(nombreUsuario, connection)) {
				String queryString = "DELETE FROM Sesion "
									+ "WHERE nombreUsuario = " + nombreUsuario + ";";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
		            
		        /* Execute query. */                    
		        preparedStatement.executeUpdate();				
			}
			else {
				throw new UsuarioSinLoguear("El usuario " + nombreUsuario + "no está logueado.");
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si y solo si existe una sesión
	 * 		 registrada para un determinado usuario.
	 */
	public boolean existeSesion(String usuario, Connection connection) throws Exception {
		try {
			String comprobacion = "SELECT nombre "
								+ "FROM Sesion "
								+ "WHERE nombreUsuario = " + usuario + ";";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
	        int busquedaComp = preparedStatement.executeUpdate();
	        return (busquedaComp != 0);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
