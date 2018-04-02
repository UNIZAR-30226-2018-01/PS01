package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.clasesVO.sesionVO;
import modelo.excepcion.SesionExistente;
import modelo.excepcion.SesionInexistente;

public class sesionDAO {
	/*
	 * Pre:  ---
	 * Post: Ha creado una nueva sesión insertando la correspondiente tupla en
	 * 		 la tabla Sesion de la BD.
	 * 		 Si diera la casualidad de que ya existiera una sesión para ese usuario
	 * 		 con el mismo idSesion, salta una excepción 'SesionExistente'
	 */
	public void insertarSesion(sesionVO sesion, Connection connection)
			throws SesionExistente, SQLException {
		try {
			existeSesion(sesion, connection);
			throw new SesionExistente("Existe esa misma sesión");	        
		}
		catch (SesionInexistente e) {
			String queryString = "INSERT INTO Sesion " +
	                "(hashSesion, nombreUsuario) " +
	            		"VALUES (?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
    		
    		preparedStatement.setString(1, sesion.verHashSesion());
    		preparedStatement.setString(2, sesion.verNombreUsuario());
    		
    		preparedStatement.executeUpdate();
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
	 * 		 saltaría una excepción 'SesionInexistente'.
	 */
	public void cerrarSesion(sesionVO s, Connection connection)
			throws SesionInexistente, SQLException {
		try {
			existeSesion(s, connection);
			String queryString = "DELETE FROM Sesion "
							   + "WHERE nombreUsuario = " + s.verNombreUsuario() + " "
							   + "AND hashSesion = " + s.verHashSesion() +";";
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
	            
	        /* Execute query. */                    
	        preparedStatement.executeUpdate();				
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Comprueba si existe una sesión s en la BD. Si no existe, lanza
	 * 		 una excepción "SesionInexistente"
	 */
	public void existeSesion(sesionVO s, Connection connection) throws
			SQLException, SesionInexistente {
		try {
			String comprobacion = "SELECT nombreUsuario "
								+ "FROM Sesion "
								+ "WHERE nombreUsuario = " + s.verNombreUsuario() + " "
								+ "AND hashSesion = " + s.verHashSesion() +";";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
			ResultSet busquedaComp = preparedStatement.executeQuery();
			if(!busquedaComp.next()) {
				throw new SesionInexistente("No existe una sesión con las características proporcionadas");
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
