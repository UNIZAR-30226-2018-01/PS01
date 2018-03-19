package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.CancionNoExisteEnLista;

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
				throw new CancionExisteEnLista("La canción " + f.verTituloCancion() + "ya existe"
						+ " en la lista" + f.verNombreLista() + ".");
			}
			else {
				String queryString = "INSERT INTO Formar(titulo, nombreArtista, nombreAlbum, nombreLista, nombreUsuario)"
						+ " VALUES(?,?,?,?,?);";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, f.verTituloCancion());
				preparedStatement.setString(2, f.verNombreArtista());
				preparedStatement.setString(3, f.verNombreAlbum());
				preparedStatement.setString(4, f.verNombreLista());
				preparedStatement.setString(5, f.verNombreUsuario());
				
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
				throw new CancionNoExisteEnLista("La canción " + f.verTituloCancion() + "no existe"
						+ " en la lista" + f.verNombreLista() + ".");
			}
			else {
				String queryString = "DELETE FROM Formar"
						+ " WHERE titulo = " + f.verTituloCancion()
						+ " AND nombreArtista = " + f.verNombreArtista()
						+ " AND nombreAlbum = " + f.verNombreAlbum()
						+ " AND nombreLista = " + f.verNombreLista()
						+ " AND nombreUsuario = " + f.verNombreUsuario()
						+ ";";
				
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
	 * Pre:
	 * Post: Devuelve verdad si y solo si una canción ya existe dentro de una cierta lista de un
	 * 		 determinado usuario.
	 */
	public boolean existeCancionEnLista(formarVO f, Connection connection) throws SQLException {
		try {
			String comprobacion = "SELECT *"
					+ " FROM Formar"
					+ " WHERE titulo = " + f.verTituloCancion()
					+ " AND nombreArtista = " + f.verNombreArtista()
					+ " AND nombreAlbum = " + f.verNombreAlbum()
					+ " AND nombreLista = " + f.verNombreLista()
					+ " AND nombreUsuario = " + f.verNombreUsuario()
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
