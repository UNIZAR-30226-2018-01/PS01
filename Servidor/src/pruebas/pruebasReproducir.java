package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasReproducir {
	private reproducirVO rVO = new reproducirVO("alberto", "cancion_1", "album_1", "artista_1");
	private reproducirDAO rDAO = new reproducirDAO();
	private Connection connection = null;
	
	public pruebasReproducir() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	public void pruebaAnyadirReproduccion() throws ExcepcionReproduccion, SQLException {
		try {
			rDAO.anyadirReproduccion(rVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Sesión creada correctamente.");
	}
	
	public static void main(String args[]) {
		
	}
}
