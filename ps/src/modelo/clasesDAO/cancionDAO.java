package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.CancionYaExiste;

public class cancionDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado la canción 'cancion' en la tabla Cancion de la BD.
	 * 		 Si ya existía una cancion con el mismo nombre, perteneciente al mismo álbum/artista,
	 * 		 y subida por el mismo 'uploader', entonces lanza una excepción 'CancionYaExiste'
	 */
	public void anyadirCancion(cancionVO cancion, Connection connection)
			throws CancionYaExiste, SQLException {
		try {
			if (existeCancion(cancion, connection)) {
				throw new CancionYaExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " ya existe.");
			}
			else {
				String queryString = "INSERT INTO Cancion(titulo, nombreArtista, nombreAlbum, genero, uploader) "
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
			throws CancionNoExiste, SQLException {
		try {
			if (!existeCancion(cancion, connection)) {
				throw new CancionNoExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " no existe.");
			}
			else {
				String queryString = "DELETE FROM Cancion"
						+ " WHERE titulo = '" + cancion.verTitulo()
						+ "' AND nombreArtista = '" + cancion.verNombreArtista()
						+ "' AND nombreAlbum = '" + cancion.verNombreAlbum()
						+ "' AND uploader = '" + cancion.verUploader()
						+ "';";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
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
			String comprobacion = "SELECT *"
					+ " FROM Cancion"
					+ " WHERE titulo = '" + cancion.verTitulo()
					+ "' AND nombreArtista = '" + cancion.verNombreArtista()
					+ "' AND nombreAlbum = '" + cancion.verNombreAlbum()
					+ "' AND uploader = '" + cancion.verUploader()
					+ "';";
			
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
}
