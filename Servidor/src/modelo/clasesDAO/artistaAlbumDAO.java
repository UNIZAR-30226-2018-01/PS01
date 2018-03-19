package modelo.clasesDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import modelo.clasesVO.artistaAlbumVO;
import modelo.excepcion.*;
import java.sql.SQLException;

import javax.servlet.ServletException;

public class artistaAlbumDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado el álbum correspondiente a un artista en la tabla ArtistaAlbum de la BD.
	 * 		 Si ya existía un álbum con el mismo nombre y del mismo artista, lanza una excepción
	 * 		 'ArtistaAlbumExiste'
	 */
	public void anyadirArtistaAlbum(artistaAlbumVO aa, Connection connection)
			throws ArtistaAlbumExiste, SQLException, Exception {
		try {
			if (!existeArtistaAlbum(aa, connection)) {
				throw new ArtistaAlbumExiste("El álbum " + aa.verNombreAlbum()
				+ " del artista " + aa.verNombreArtista() + "ya existe.");
			}
			else {
				String queryString = "INSERT INTO ArtistaAlbum(nombreArtista, nombreAlbum, anyooAlbum) "
						+ "VALUES (?,?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, aa.verNombreArtista());
	    			preparedStatement.setString(2, aa.verNombreAlbum());
	    			preparedStatement.setString(3, aa.verAnyooAlbum());
	    			
	    			preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha eliminado el álbum correspondiente a un artista de la tabla ArtistaAlbum de la BD.
	 * 		 Si no existía ningún álbum con el mismo nombre y del mismo artista, lanza una excepción
	 * 		 'ArtistaAlbumNoExiste'
	 */
	public void quitarArtistaAlbum(artistaAlbumVO aa, Connection connection)
			throws ArtistaAlbumNoExiste, SQLException, Exception {
		try {
			if (!existeArtistaAlbum(aa, connection)) {
				throw new ArtistaAlbumNoExiste("El álbum " + aa.verNombreAlbum()
						+ " del artista " + aa.verNombreArtista() + "no existe.");
			}
			else {
				String queryString = "DELETE FROM ArtistaAlbum"
						+ " WHERE nombreArtista = " + aa.verNombreArtista()
						+ " AND nombreAlbum = " + aa.verNombreAlbum()
						+ "; ";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si y solo si existe un álbum con el mismo nombre
	 * 		 y del mismo artista que 'aa' en la tabla 'ArtistaAlbum' de la BD.
	 */
	public boolean existeArtistaAlbum(artistaAlbumVO aa, Connection connection) throws Exception {
		try {
			String comprobacion = "SELECT *"
					+ " FROM ArtistaAlbum"
					+ " WHERE nombreArtista = " + aa.verNombreArtista()
					+ " AND nombreAlbum = " + aa.verNombreAlbum()
					+ "; ";
			
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
