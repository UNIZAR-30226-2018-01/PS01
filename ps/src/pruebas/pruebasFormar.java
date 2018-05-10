package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.formarDAO;
import modelo.clasesVO.formarVO;
import modelo.excepcion.CancionExisteEnLista;
import modelo.excepcion.CancionNoExisteEnLista;

public class pruebasFormar {
	private formarVO fVO = new formarVO("ruta", "lista_1", "alberto");
	private formarDAO fDAO = new formarDAO();
	private Connection connection = null;
	
	public pruebasFormar() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	public void pruebaExisteCancionEnLista() throws SQLException {
		try {
			if (fDAO.existeCancionEnLista(fVO, connection)) {
				System.out.println("La canción ya está en la lista");
			}
			else {
				System.out.println("La canción no está en la lista");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaAnyadirCancionALista() throws CancionExisteEnLista, SQLException {
		try {
			fDAO.anyadirCancionALista(fVO, connection);
			System.out.println("Canción añadida a la lista");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaQuitarCancionDeLista() throws CancionNoExisteEnLista, SQLException {
		try {
			fDAO.quitarCancionDeLista(fVO, connection);
			System.out.println("Canción eliminada de la lista");
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
