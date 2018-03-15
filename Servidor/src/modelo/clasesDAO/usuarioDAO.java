package modelo.clasesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.clasesVO.usuarioVO;

public class usuarioDAO {
	public void insertarUsuario(usuarioVO usuario, Connection connection) throws Exception {
		try {
			if (existeUsuario(usuario.verNombre(), connection)) {
				throw new Exception("Usuario "+usuario.verNombre()+" ya existente.");
			}
			else {
				String queryString = "INSERT INTO Usuario " +
		                "(login, contrasenya, fechaRegistro, correo) " +
		            		"VALUES (?,?,?,?)";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
	        		preparedStatement = connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, usuario.verNombre());
	        		preparedStatement.setString(2, usuario.verHashPass());
	        		
	        		preparedStatement.executeUpdate();
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean existeUsuario(String usuario, Connection connection) throws Exception{
		try {
			String comprobacion = "SELECT nombre "
					+ "FROM Usuario "
					+ "WHERE nombre = " + usuario + ";";
			
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
