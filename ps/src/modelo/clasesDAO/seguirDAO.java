package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.excepcion.ErrorDejarDeSeguir;

public class seguirDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado el usuario 'usuario' en la tabla Usuario de la BD.
	 * 		 Si ya existía un usuario con ese nombre, lanza una excepción
	 * 		 'UsuarioYaRegistrado'
	 */
	public void seguir(String nombreSeguidor, String nombreSeguido, Connection connection)
			throws SQLException {
		try {
			String queryString = "INSERT INTO Seguir " +
	                "(nombreSeguidor, nombreSeguido) " +
	            		"VALUES (?,?)";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
	        	preparedStatement = connection.prepareStatement(queryString);
	        	preparedStatement.setString(1, nombreSeguidor);
	        	preparedStatement.setString(2, nombreSeguido);
	        	preparedStatement.executeUpdate();
    
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha registrado que una canción ya no es favorita de un usuario.
	 * 		 Si se produce algún error entonces lanza una excepción 'ErrorQuitarMegusta'.
	 */
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido, Connection connection)
			throws ErrorDejarDeSeguir, SQLException {
		try {
			String queryString = "DELETE FROM Seguir"
					+ " WHERE nombreSeguidor = '" + nombreSeguidor
					+ "' AND nombreSeguido = '" + nombreSeguido
					+ "';";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp == 0) {
	        		throw new ErrorDejarDeSeguir("Error al dejar de seguir al usuario "
	        				+ nombreSeguido + " por parte del usuario " + nombreSeguidor + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
}
