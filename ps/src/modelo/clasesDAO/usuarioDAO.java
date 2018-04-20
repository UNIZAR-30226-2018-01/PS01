package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.clasesVO.*;
import modelo.excepcion.*;
import java.sql.SQLException;
import org.json.simple.*;

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
					+ "WHERE nombre = ?;";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
			preparedStatement.setString(1,usuario);
	            
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
	 * 		 'LoginInexistente'. En caso contrario, ha comprobado si existe
	 * 		 una entrada en la tabla usuario con un login y hash de contraseña
	 * 		 determinados.
	 */
	public void hayUsuario(usuarioVO u, Connection c)
			throws LoginInexistente, SQLException{
		try {
			// Preparamos la consulta
			String q = "SELECT nombre "
					+ "FROM Usuario "
					+ "WHERE nombre = ? AND "
					+ "hashPass = ?;";
			PreparedStatement preparedStatement = c.prepareStatement(q);
			preparedStatement.setString(1, u.verNombre());
			preparedStatement.setString(2, u.verHashPass());
			
			// Hacemos la consulta
			ResultSet r = preparedStatement.executeQuery();
			
			// Comprobamos si ha devuelto algo
			if(!r.first()) {
				// Si no ha devuelto significa que no el usuario no existe
				throw new LoginInexistente("Datos de login incorrectos");
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Dada la cadena de caracteres 'nombre' busca usuarios cuyo nombre
	 * 		 sea igual o empiece por nombre, devolviéndolos en un JSON
	 * 		 cuya clave es usuarios, cuyo valor asociado es un array de strings
	 *  	 con los nombres encontrados
	 */
	public JSONObject buscarUsuarios(String nombre, Connection c)
			throws SQLException, UsuarioInexistente {
		try {
			// Preparamos la consulta
			String q = "SELECT nombre FROM Usuario WHERE nombre LIKE ? "
					 + "ORDER BY(nombre);";
			PreparedStatement p = c.prepareStatement(q);
			p.setString(1, nombre+"%");
			
			// Hacemos la consulta
			ResultSet r = p.executeQuery();
			
			// No ha habido resultados
			if(!r.first()) {
				throw new UsuarioInexistente("No hay ningún usuario cuyo nombre "
						+ "sea o empiece por " + nombre);
			}
			
			// Generamos el JSON
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			r.beforeFirst(); // Movemos el cursor antes del 1er elemento
			while (r.next()) {
				array.add(r.getString(1));
			}
			obj.put("usuarios", array);
			return obj;
		}
		catch(Exception e) {
			throw e;
		}
	}
}
