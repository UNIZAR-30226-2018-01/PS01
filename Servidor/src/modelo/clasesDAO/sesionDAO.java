package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.sesionVO;
import modelo.excepcion.UsuarioSinLoguear;
import modelo.excepcion.UsuarioYaLogueado;

public class sesionDAO {
	public void insertarSesion(sesionVO sesion, Connection connection) throws UsuarioYaLogueado, SQLException {
		try {
			if (existeSesion(sesion.verNombreUsuario(), connection)) {
				throw new UsuarioYaLogueado("El usuario " + sesion.verNombreUsuario() + " ya está logueado.");
			}
			else {
				String queryString = "INSERT INTO Sesion " +
		                "(hashSesion, nombreUsuario) " +
		            		"VALUES (?,?)";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
		        
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
	
	public void cerrarSesion(sesionVO sesion, Connection connection)
			throws UsuarioSinLoguear, SQLException {
		try {
			if (existeSesion(sesion.verNombreUsuario(), connection)) {
				String queryString = "DELETE FROM Sesion "
									+ "WHERE hashSesion = " + sesion.verHashSesion() + " "
									+ "AND nombreUsuario = " + sesion.verNombreUsuario() + ";";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
		            
		        /* Execute query. */                    
		        preparedStatement.executeUpdate();				
			}
			else {
				throw new UsuarioSinLoguear("El usuario " + sesion.verNombreUsuario() + "no está logueado.");
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
}
