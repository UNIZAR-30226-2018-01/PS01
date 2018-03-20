package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasSesion {
	private sesionVO sVO = new sesionVO("albertro","alberto");
	private sesionDAO sDAO = new sesionDAO();
	private Connection connection = null;
	
	public void pruebaExisteSesion() throws SQLException {
		try {
			if (sDAO.existeSesion(sVO.verNombreUsuario(), connection)) {
				System.out.println("La sesion del usuario " + sVO.verNombreUsuario() + " ya existe.");
			}
			else {
				System.out.println("La sesión del usuario " + sVO.verNombreUsuario() + " no existe.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaInsertarSesion() throws UsuarioYaLogueado, SQLException{
		try {
			sDAO.insertarSesion(sVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Sesión creada correctamente.");
	}
	
	public void pruebaCerrarSesion() throws UsuarioYaLogueado, SQLException{
		try {
			sDAO.cerrarSesion(sVO.verNombreUsuario(), connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Sesión cerrada correctamente.");
	}
	
	public static void main(String args[]) {
		
	}
}
