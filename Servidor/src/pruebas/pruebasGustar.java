package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.gustarDAO;
import modelo.clasesVO.gustarVO;
import modelo.excepcion.ErrorAnyadirMegusta;
import modelo.excepcion.ErrorQuitarMegusta;
import modelo.excepcion.ExcepcionReproduccion;

public class pruebasGustar {
	private gustarVO gVO = new gustarVO("alberto", "cancion_1", "album_1", "artista_1");
	private gustarDAO gDAO = new gustarDAO();
	private Connection connection = null;
	
	public pruebasGustar() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	public void pruebaDarMegusta() throws ErrorAnyadirMegusta, SQLException {
		try {
			gDAO.megusta(gVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Me gusta dado correctamente.");
	}
	
	public void pruebaQuitarMegusta() throws ErrorQuitarMegusta, SQLException {
		try {
			gDAO.yanomegusta(gVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Me gusta dado correctamente.");
	}
}
