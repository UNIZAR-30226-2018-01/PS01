package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.FuncionesAuxiliares;
import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasReproduccion {
	private static reproduccionVO rVO = new reproduccionVO("alberto", "cancion_1", "album_1", "artista_1", "fecha");
	private static reproduccionDAO rDAO = new reproduccionDAO();
	private static Connection connection = null;
	
	public static void pruebaAnyadirReproduccion() throws ExcepcionReproduccion, SQLException {
		try {
			rDAO.anyadirReproduccion(rVO, connection);
			System.out.println("Sesión creada correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String args[]) throws Exception {
		connection = FuncionesAuxiliares.obtenerConexion();
		pruebaAnyadirReproduccion();
		pruebaAnyadirReproduccion();
	}
}
