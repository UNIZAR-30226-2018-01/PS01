package modelo.clasesDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.CancionYaExiste;
import org.json.simple.*;

public class cancionDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado la canción 'cancion' en la tabla Cancion de la BD.
	 * 		 Si ya existía una cancion con el mismo nombre, perteneciente al mismo álbum/artista,
	 * 		 y subida por el mismo 'uploader', entonces lanza una excepción 'CancionYaExiste'
	 */
	public void anyadirCancion(cancionVO cancion, Connection connection)
			throws CancionYaExiste, SQLException {
		System.out.println("Insertando canción en la base de datos 3...");
		try {
			if (existeCancion(cancion, connection)) {
				throw new CancionYaExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " ya existe.");
			}
			else {
				String queryString = "INSERT INTO Cancion(titulo, nombreArtista,"
						+ "nombreAlbum, genero, uploader) "
						+ "VALUES (?,?,?,?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, cancion.verTitulo());
	    			preparedStatement.setString(2, cancion.verNombreArtista());
	    			preparedStatement.setString(3, cancion.verNombreAlbum());
	    			preparedStatement.setString(4, cancion.verGenero());
	    			preparedStatement.setString(5, cancion.verUploader());
	    			preparedStatement.executeUpdate();
	        	}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha eliminado la canción 'cancion' de la tabla Cancion de la BD.
	 * 		 Si no existía ninguna cancion con el mismo nombre, perteneciente al mismo álbum/artista,
	 * 		 y subida por el mismo 'uploader', entonces lanza una excepción 'CancionNoExiste'
	 */
	public void quitarCancion(cancionVO cancion, Connection connection)
			throws CancionNoExiste, SQLException, IOException {
		try {
			if (!existeCancion(cancion, connection)) {
				throw new CancionNoExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " no existe.");
			}
			else {
				// Recuperamos la ruta del fichero y borramos el fichero físico
				String s1 = "SELECT ruta "
						  + "FROM Cancion"
						  + "WHERE titulo = ? AND "
						  + "nombreArtista = ? AND "
						  + "nombreAlbum = ? AND "
						  + "uploader = ?;";
				PreparedStatement preparedStatement =  connection.prepareStatement(s1);
				preparedStatement.setString(1, cancion.verTitulo());
				preparedStatement.setString(2, cancion.verNombreArtista());
				preparedStatement.setString(3, cancion.verNombreAlbum());
				preparedStatement.setString(4, cancion.verUploader());
				ResultSet resultado = preparedStatement.executeQuery();
				Files.delete(new File(resultado.getString(1)).toPath());
				
				// Borramos la entrada en la base de datos
				s1 = "DELETE FROM Cancion "
				   + "WHERE titulo = ? AND "
				   + "nombreArtista = ? AND "
				   + "nombreAlbum = ? AND "
				   + "uploader = ?;";
				
				preparedStatement = connection.prepareStatement(s1);
				preparedStatement.setString(1, cancion.verTitulo());
				preparedStatement.setString(2, cancion.verNombreArtista());
				preparedStatement.setString(3, cancion.verNombreAlbum());
				preparedStatement.setString(4, cancion.verUploader());
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	
	/*
	 * Pre: ---
	 * Post: Devuelve verdad si y solo si existe en la tabla Cancion una canción igual
	 * 		 a 'cancion'.
	 */
	public boolean existeCancion(cancionVO cancion, Connection connection) throws SQLException {
		try {
			String s1 = "SELECT *"
					+ " FROM Cancion"
					+ " WHERE titulo = ?"
					+ " AND nombreArtista = ?"
					+ " AND nombreAlbum = ?"
					+ " AND uploader = ?;";
			
			PreparedStatement preparedStatement = connection.prepareStatement(s1);
			preparedStatement.setString(1, cancion.verTitulo());
			preparedStatement.setString(2, cancion.verNombreArtista());
			preparedStatement.setString(3, cancion.verNombreAlbum());
			preparedStatement.setString(4, cancion.verUploader());
	            
	        /* Execute query. */                    
			ResultSet busquedaComp = preparedStatement.executeQuery();
	        return (busquedaComp.next());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existen canciones en la BD con el título proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un json con una clave canciones, cuyo
	 * 		valor asociado será un array en el que cada componente es una
	 * 		canción
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public JSONObject buscarCancionPorTitulo(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "titulo = ? AND "
					 + "(uploader = ? OR uploader = 'Admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			preparedStatement.setString(1, c.verTitulo());
			preparedStatement.setString(2, nombreUploader);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}

			// Objetos para devolver el resultado
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			busquedaComp.beforeFirst(); // Movemos el cursor antes del 1er elemento
			while (busquedaComp.next()) {
				JSONObject aux = new JSONObject();
				aux.put("tituloCancion", busquedaComp.getString(1));
				aux.put("nombreArtista", busquedaComp.getString(2));
				aux.put("nombreAlbum", busquedaComp.getString(3));
				aux.put("genero", busquedaComp.getString(4));
				aux.put("uploader", busquedaComp.getString(5));
				aux.put("ruta", busquedaComp.getString(6));
				array.add(aux);
			}
			obj.put("canciones", array);
			return obj;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existen canciones en la BD con el artista proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un json con una clave canciones, cuyo
	 * 		valor asociado será un array en el que cada componente es una
	 * 		canción
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public JSONObject buscarCancionPorArtista(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "nombreArtista = ? AND "
					 + "(uploader = ? OR uploader = 'Admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			preparedStatement.setString(1, c.verNombreArtista());
			preparedStatement.setString(2, nombreUploader);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}

			// Objetos para devolver el resultado
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			busquedaComp.beforeFirst(); // Movemos el cursor antes del 1er elemento
			while (busquedaComp.next()) {
				JSONObject aux = new JSONObject();
				aux.put("tituloCancion", busquedaComp.getString(1));
				aux.put("nombreArtista", busquedaComp.getString(2));
				aux.put("nombreAlbum", busquedaComp.getString(3));
				aux.put("genero", busquedaComp.getString(4));
				aux.put("uploader", busquedaComp.getString(5));
				aux.put("ruta", busquedaComp.getString(6));
				array.add(aux);
			}
			obj.put("canciones", array);
			return obj;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existe una cancion en la BD con el album proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un json con una clave canciones, cuyo
	 * 		valor asociado será un array en el que cada componente es una
	 * 		canción
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public JSONObject buscarCancionPorAlbum(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "nombreAlbum = ? AND "
					 + "(uploader = ? OR uploader = 'Admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			preparedStatement.setString(1, c.verNombreAlbum());
			preparedStatement.setString(2, nombreUploader);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}

			// Objetos para devolver el resultado
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			busquedaComp.beforeFirst(); // Movemos el cursor antes del 1er elemento
			while (busquedaComp.next()) {
				JSONObject aux = new JSONObject();
				aux.put("tituloCancion", busquedaComp.getString(1));
				aux.put("nombreArtista", busquedaComp.getString(2));
				aux.put("nombreAlbum", busquedaComp.getString(3));
				aux.put("genero", busquedaComp.getString(4));
				aux.put("uploader", busquedaComp.getString(5));
				aux.put("ruta", busquedaComp.getString(6));
				array.add(aux);
			}
			obj.put("canciones", array);
			return obj;
		}
		catch(Exception e) {
			throw e;
		}
	}
}
