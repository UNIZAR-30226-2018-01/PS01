package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.formarDAO;
import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.CancionNoExisteEnLista;

public class pruebasFormar {
	private formarVO fVO = new formarVO("cancion_1", "artista_1", "album_1", "lista_1", "alberto");
	private formarDAO fDAO = new formarDAO();
	private Connection connection = null;
	
	public pruebasFormar() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
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
			System.out.println("Canción " + fVO.verTituloCancion() + " añadida a la lista " + fVO.verNombreLista() + " correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaQuitarCancionDeLista() throws CancionNoExisteEnLista, SQLException {
		try {
			fDAO.quitarCancionDeLista(fVO, connection);
			System.out.println("Canción " + fVO.verTituloCancion() + " eliminada de la lista " + fVO.verNombreLista() + " correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String args[]) throws Exception {
		pruebasFormar p = new pruebasFormar();
		
		p.pruebaExisteCancionEnLista();
		p.pruebaAnyadirCancionALista();
		p.pruebaAnyadirCancionALista();
		p.pruebaExisteCancionEnLista();
		p.pruebaQuitarCancionDeLista();
		p.pruebaQuitarCancionDeLista();
		p.pruebaExisteCancionEnLista();
		p.pruebaAnyadirCancionALista();
	}
}
