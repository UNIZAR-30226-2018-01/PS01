package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.escucharVO;
import modelo.excepcion.ExcepcionEscuchar;

public class escucharDAO {
	
	/*
	 * Pre: ---
	 * Post: Registra en la tabla Escuchar de la BD que un usuario ha escuchado una lista de reproducción en una
	 * 		 determinada fecha.
	 * 		 Si y solo si por algún motivo se intenta registrar 2 o más veces la 'audición' de una lista de reproducción
	 * 		 en el mismo instante de tiempo, entonces y solo entonces se lanza una excepción ExcepcionEscuchar.
	 */
	public void anyadir(escucharVO a, Connection connection) throws ExcepcionEscuchar, SQLException {
		try {
			if (existeEscuchar(a, connection)) {
				throw new ExcepcionEscuchar("Error al registrar la reproducción por parte"
						+ "del usuario " + a.verNombreListener());
			}
			else {
				String queryString = "INSERT INTO Escuchar(nombreLista, nombreCreador, nombreListener) "
						+ "VALUES (?,?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, a.verNombreLista());
				preparedStatement.setString(2, a.verNombreCreador());
				preparedStatement.setString(3, a.verNombreListener());
				
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si y solo si un usuario ya ha escuchado una lista de reproducción
	 * 		 en una fecha determinada.
	 */
	public boolean existeEscuchar(escucharVO a, Connection connection) throws SQLException {
		try {
			String comprobacion = "SELECT *"
					+ " FROM Escuchar"
					+ " WHERE nombreLista = " + a.verNombreLista()
					+ " AND nombreCreador = " + a.verNombreCreador()
					+ " AND nombreListener = " + a.verNombreListener()
					+ ";";
			
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
