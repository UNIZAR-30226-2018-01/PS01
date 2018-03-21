package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.clasesDAO.artistaAlbumDAO;
import modelo.clasesVO.artistaAlbumVO;
import modelo.excepcion.ArtistaAlbumExiste;
import modelo.excepcion.ArtistaAlbumNoExiste;

public class pruebasArtistaAlbum {
	private artistaAlbumVO aVO = new artistaAlbumVO("artista_1", "album_1", "2018");
	private artistaAlbumDAO aDAO = new artistaAlbumDAO();
	private Connection connection = null;
	
	public void pruebaExisteAA() throws SQLException {
		try {
			if (aDAO.existeArtistaAlbum(aVO, connection)) {
				System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " ya existe.");
			}
			else {
				System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " no existe.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaInsertarAA() throws ArtistaAlbumExiste, SQLException {
		try {
			aDAO.anyadirArtistaAlbum(aVO, connection);
		}
		catch (Exception e) {
			System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " ha sido añadido correctamente.");
		}
	}
	
	public void pruebaQuitarAA() throws ArtistaAlbumNoExiste, SQLException {
		try {
			aDAO.quitarArtistaAlbum(aVO, connection);
		}
		catch (Exception e) {
			System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " ha sido eliminado correctamente.");
		}
	}
	
	public static void main(String args[]) {
		
	}
}
