package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.clasesDAO.cancionDAO;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.CancionYaExiste;

public class pruebasCancion {
	private cancionVO cVO = new cancionVO("cancion_1", "artista_1", "album_1", "", "alberto");
	private cancionDAO cDAO = new cancionDAO();
	private Connection connection = null;
	
	public void pruebaExisteCancion() throws SQLException {
		try {
			if (cDAO.existeCancion(cVO, connection)) {
				System.out.println("La cancion " + cVO.verTitulo() + " subida por el usuario " + cVO.verUploader() + " ya existe.");
			}
			else {
				System.out.println("La cancion " + cVO.verTitulo() + " subida por el usuario " + cVO.verUploader() + " no existe.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaAnyadirCancion() throws CancionYaExiste, SQLException {
		try {
			cDAO.anyadirCancion(cVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Canción " + cVO.verTitulo() + " subida por el usuario " + cVO.verUploader() + " correctamente.");
	}
	
	public void pruebaQuitarCancion() throws CancionNoExiste, SQLException {
		try {
			cDAO.quitarCancion(cVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Canción " + cVO.verTitulo() + " eliminada por el usuario " + cVO.verUploader() + " correctamente.");
	}
}
