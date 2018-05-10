package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import modelo.clasesVO.formarVO;
import modelo.clasesVO.listaReproduccionVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.CancionNoExisteEnLista;
import modelo.excepcion.NoHayCanciones;

public class formarDAO {
	
	/*
	 * Pre: ---
	 * Post: Registra en la tabla Formar de la BD que un usuario ha añadido una canción a una lista de reproducción.
	 * 		 Si el usuario ya había insertado antes esa canción en la lista de reproducción entonces salta una
	 * 		 excepción 'CancionExisteEnLista'.
	 */
	public void anyadirCancionALista(formarVO f, Connection connection)
			throws CancionExisteEnLista, SQLException {
		try {
			if (existeCancionEnLista(f, connection)) {
				throw new CancionExisteEnLista("La canción de ruta " + 
						f.getRuta() + " ya existe en la lista " + 
						f.getNombreLista());
			}
			else {
				String queryString = "INSERT INTO Formar(ruta, nombreLista, nombreUsuario)"
						+ " VALUES(?,?,?);";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, f.getRuta());
				preparedStatement.setString(2, f.getNombreLista());
				preparedStatement.setString(3, f.getNombreUsuario());
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Elimina de la tabla Formar de la BD una canción previamente añadida a una lista de reproducción
	 * 		 por parte de un usuario.
	 * 		 Si por algún motivo se intenta eliminar 2 o más veces una canción de la misma lista de reproducción
	 * 		 del mismo usuario, sala una excepción 'CancionNoExisteEnLista'.
	 */
	public void quitarCancionDeLista(formarVO f, Connection connection)
			throws CancionNoExisteEnLista, SQLException {
		try {
			if (!existeCancionEnLista(f, connection)) {
				throw new CancionNoExisteEnLista("La canción de ruta " + 
						f.getRuta() + " no existe en la lista " + 
						f.getNombreLista());
			}
			else {
				String queryString = "DELETE FROM Formar"
						+ " WHERE ruta = ?"
						+ " AND nombreLista = ?"
						+ " AND nombreUsuario = ?;";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				preparedStatement.setString(1, f.getRuta());
				preparedStatement.setString(2, f.getNombreLista());
				preparedStatement.setString(3, f.getNombreUsuario());
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si y solo si una canción ya existe dentro de una cierta lista de un
	 * 		 determinado usuario.
	 */
	public boolean existeCancionEnLista(formarVO f, Connection connection) throws SQLException {
		try {
			String queryString = "SELECT * FROM Formar"
					+ " WHERE ruta = ?"
					+ " AND nombreLista = ?"
					+ " AND nombreUsuario = ?;";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			preparedStatement.setString(1, f.getRuta());
			preparedStatement.setString(2, f.getNombreLista());
			preparedStatement.setString(3, f.getNombreUsuario());
	            
	        /* Execute query. */                    
			ResultSet busquedaComp = preparedStatement.executeQuery();
	        return (busquedaComp.first());			
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Dada una lista de reproducción, devuelve un JSON con las canciones
	 * 		 que forman dicha lista
	 */
	public JSONObject verLista(listaReproduccionVO l, Connection connection)
			throws SQLException, NoHayCanciones {
		try {
			// Hacemos la consulta
			String q = "SELECT titulo, nombreArtista, nombreAlbum, genero, uploader, c.ruta\n" + 
					"FROM\n" + 
					"	(SELECT ruta\n" + 
					"	FROM Formar\n" + 
					"	WHERE nombreLista = ? AND nombreUsuario = ?) s1\n" + 
					"	JOIN\n" + 
					"	Cancion c\n" + 
					"	ON s1.ruta = c.ruta;";
			PreparedStatement p = connection.prepareStatement(q);
			p.setString(1, l.obtenerNombreLista());
			p.setString(2, l.obtenerNombreUsuario());
			ResultSet resultado = p.executeQuery();
			
			if (!resultado.first()) {
				throw new NoHayCanciones("La Lista " + l.obtenerNombreLista() + " del usuario"
						+ l.obtenerNombreUsuario() + " está vacía.");
			}
			else {
				// Objetos para devolver el resultado
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				resultado.beforeFirst(); // Movemos el cursor antes del 1er elemento
				while (resultado.next()) {
					JSONObject aux = new JSONObject();
					aux.put("tituloCancion", resultado.getString(1));
					aux.put("nombreArtista", resultado.getString(2));
					aux.put("nombreAlbum", resultado.getString(3));
					aux.put("genero", resultado.getString(4));
					aux.put("uploader", resultado.getString(5));
					aux.put("ruta", resultado.getString(6));
					array.add(aux);
				}
				obj.put("canciones", array);
				return obj;
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
