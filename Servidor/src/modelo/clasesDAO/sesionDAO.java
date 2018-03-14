package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.clasesVO.sesionVO;

public class sesionDAO {
	public void insertarSesion(sesionVO sesion, Connection connection) throws Exception {
		try {
			String comprobacion = "SELECT nombre "
								+ "FROM Sesion "
								+ "WHERE nombreUsuario = " + sesion.verNombreUsuario() + ";";
			String queryString = "INSERT INTO Sesion " +
	                "(hashSesion, nombreUsuario) " +
	            		"VALUES (?,?,?,?)";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            preparedStatement.setString(1, sesion.verNombreUsuario());
	            
	        /* Execute query. */                    
	        int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp != 0) {
	        		throw new Exception("Usuario "+sesion.verNombreUsuario()+" ya logueado.");
	        }
	        else {
	        		preparedStatement = connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, sesion.verHashSesion());
	        		preparedStatement.setString(2, sesion.verNombreUsuario());
	        		
	        		preparedStatement.executeUpdate();
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
	
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
	
	public void cerrarSesion(sesionVO sesion, Connection connection) throws Exception {
		try {
			if (existeSesion(sesion.verNombreUsuario(), connection)) {
				String queryString = "DELETE FROM TABLE Sesion "
									+ "WHERE Sesion.hashSesion = " + sesion.verHashSesion() + " "
									+ "AND Sesion.nombreUsuario = " + sesion.verNombreUsuario() + ";";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
		            
		        /* Execute query. */                    
		        preparedStatement.executeUpdate();				
			}
			else {
				throw new Exception("El usuario " + sesion.verNombreUsuario() + "no está logueado.");
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
