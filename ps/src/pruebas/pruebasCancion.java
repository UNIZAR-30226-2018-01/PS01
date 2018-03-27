package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.cancionDAO;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.CancionYaExiste;

public class pruebasCancion {
	private cancionVO cVO = new cancionVO("cancion_1", "artista_1", "album_1", "", "alberto");
	private cancionDAO cDAO = new cancionDAO();
	private Connection connection = null;
	
	public pruebasCancion() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
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
			System.out.println("Canción " + cVO.verTitulo() + " subida por el usuario " + cVO.verUploader() + " correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaQuitarCancion() throws CancionNoExiste, SQLException {
		try {
			cDAO.quitarCancion(cVO, connection);
			System.out.println("Canción " + cVO.verTitulo() + " eliminada por el usuario " + cVO.verUploader() + " correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String args[]) throws Exception {
		pruebasCancion p = new pruebasCancion();
		
		p.pruebaExisteCancion();
		p.pruebaAnyadirCancion();
		p.pruebaAnyadirCancion();
		p.pruebaExisteCancion();
		p.pruebaQuitarCancion();
		p.pruebaExisteCancion();
		p.pruebaAnyadirCancion();		
	}
}
