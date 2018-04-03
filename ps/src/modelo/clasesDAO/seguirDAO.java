package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import modelo.clasesVO.seguirVO;
import modelo.excepcion.ErrorDejarDeSeguir;
import modelo.excepcion.SinSeguidos;

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
	
	public Vector<seguirVO> listaDeSeguidos(String nombreSeguidor, Connection connection)
		throws SinSeguidos, SQLException {
		try {
			Vector<seguirVO> seguidos = new Vector<seguirVO>();
			String queryString =  "SELECT * "
								+ "FROM Seguir "
								+ "WHERE nombreSeguidor = '" + nombreSeguidor + "';";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			ResultSet resultado = preparedStatement.executeQuery(queryString);
			
			if (!resultado.first()) {
				throw new SinSeguidos("El usuario " + nombreSeguidor + " no sigue a nadie.");
			}
			else {
				while (resultado.next()) {
					seguidos.add(new seguirVO(resultado.getString(1), resultado.getString(2)));
				}
				
				return seguidos;
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
