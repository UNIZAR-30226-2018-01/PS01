package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.artistaAlbumDAO;
import modelo.clasesVO.artistaAlbumVO;
import modelo.excepcion.ArtistaAlbumExiste;
import modelo.excepcion.ArtistaAlbumNoExiste;

public class pruebasArtistaAlbum {
	private artistaAlbumVO aVO = new artistaAlbumVO("artista_1", "album_1", "2018");
	private artistaAlbumDAO aDAO = new artistaAlbumDAO();
	private Connection connection = null;
	
	public pruebasArtistaAlbum () throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
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
			System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " ha sido añadido correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaQuitarAA() throws ArtistaAlbumNoExiste, SQLException {
		try {
			aDAO.quitarArtistaAlbum(aVO, connection);
			System.out.println("El álbum " + aVO.verNombreAlbum() + " del artista " + aVO.verNombreArtista() + " ha sido eliminado correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String args[]) throws Exception {
		pruebasArtistaAlbum p = new pruebasArtistaAlbum();
		
		p.pruebaExisteAA();
		p.pruebaInsertarAA();
		p.pruebaInsertarAA();
		p.pruebaInsertarAA();
		p.pruebaInsertarAA();
		p.pruebaInsertarAA();
		p.pruebaQuitarAA();
		p.pruebaQuitarAA();
		p.pruebaQuitarAA();
		p.pruebaQuitarAA();
		p.pruebaExisteAA();
	}
}
