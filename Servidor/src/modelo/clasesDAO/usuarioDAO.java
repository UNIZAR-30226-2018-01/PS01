package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.clasesVO.*;
import modelo.excepcion.*;
import java.sql.SQLException;

public class usuarioDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado el usuario 'usuario' en la tabla Usuario de la BD.
	 * 		 Si ya existía un usuario con ese nombre, lanza una excepción
	 * 		 'UsuarioYaRegistrado'
	 */
	public void insertarUsuario(usuarioVO usuario, Connection connection)
			throws UsuarioYaRegistrado, SQLException {
		try {
			if (existeUsuario(usuario.verNombre(), connection)) {
				throw new UsuarioYaRegistrado("El usuario \"" +
						usuario.verNombre() + "\" ya existe");
			}
			else {
				String queryString = "INSERT INTO Usuario " +
		                "(nombre, hashPass) " +
		            		"VALUES (?,?)";
				
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
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si existe un usuario con el nombre 'usuario' en
	 * 		 la tabla 'Usuario' de la BD.
	 */
	public boolean existeUsuario(String usuario, Connection connection)
			throws SQLException{
		try {
			String comprobacion = "SELECT nombre "
					+ "FROM Usuario "
					+ "WHERE nombre = '" + usuario + "';";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
	        ResultSet busquedaComp = preparedStatement.executeQuery();
	        return (busquedaComp.next());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Si el usuario u no existe en la base de datos, lanza una excepción
	 * 		 'LoginInexistente'. En caso contrario, no hace nada.
	 */
	public void iniciarSesion(usuarioVO u, Connection c)
			throws LoginInexistente, SQLException{
		try {
			// Preparamos la consulta
			String q = new String();
			q = q + "SELECT * FROM USUARIO ";
			q = q + "WHERE nombre = ? AND hashPass = ?";
			PreparedStatement preparedStatement = c.prepareStatement(q);
			preparedStatement.setString(1, u.verNombre());
			preparedStatement.setString(1, u.verHashPass());
			
			// Hacemos la consulta
			ResultSet r = preparedStatement.executeQuery(q);
			
			// Comprobamos si ha devuelto algo
			if(!r.next()) {
				// Si no ha devuelto significa que no el usuario no existe
				throw new LoginInexistente("El usuario \"" + u.verNombre() +
						"\" no existe");
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
