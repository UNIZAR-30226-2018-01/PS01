package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import modelo.clasesVO.*;
import modelo.excepcion.*;
import java.sql.SQLException;
import modelo.clasesVO.artistaAlbumVO;

public class artistaAlbumDAO {
	public void anyadirArtistaAlbum(artistaAlbumVO aa, Connection connection)
			throws ArtistaAlbumExiste, SQLException {
		try {
			if (existeArtistaAlbum(aa, connection)) {
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
	
	public void quitarArtistaAlbum(artistaAlbumVO aa, Connection connection)
			throws ArtistaAlbumNoExiste, SQLException {
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
