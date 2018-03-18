package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.escucharVO;
import modelo.excepcion.ExcepcionEscuchar;

public class escucharDAO {
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
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean existeEscuchar(escucharVO a, Connection connection) throws Exception {
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
