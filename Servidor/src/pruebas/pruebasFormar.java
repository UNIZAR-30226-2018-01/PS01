package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.clasesDAO.formarDAO;
import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.CancionNoExisteEnLista;

public class pruebasFormar {
	private formarVO fVO = new formarVO("cancion_1", "artista_1", "album_1", "lista_1", "alberto");
	private formarDAO fDAO = new formarDAO();
	private Connection connection = null;
	
	public void pruebaExisteCancionEnLista() throws SQLException {
		try {
			if (fDAO.existeCancionEnLista(fVO, connection)) {
				System.out.println("La canción " + fVO.verTituloCancion() + " ya existe en la lista " + fVO.verNombreLista() + ".");
			}
			else {
				System.out.println("La canción " + fVO.verTituloCancion() + " no existe en la lista " + fVO.verNombreLista() + ".");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaAnyadirCancionALista() throws CancionExisteEnLista, SQLException {
		try {
			fDAO.anyadirCancionALista(fVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Canción " + fVO.verTituloCancion() + " añadida a la lista " + fVO.verNombreLista() + " correctamente.");
	}
	
	public void pruebaQuitarCancionDeLista() throws CancionNoExisteEnLista, SQLException {
		try {
			fDAO.quitarCancionDeLista(fVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Canción " + fVO.verTituloCancion() + " eliminada de la lista " + fVO.verNombreLista() + " correctamente.");
	}
	
	public static void main(String args[]) {
		
	}
}
