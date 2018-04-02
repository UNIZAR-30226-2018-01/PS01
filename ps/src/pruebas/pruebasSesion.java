package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasSesion {
	private sesionVO sVO = new sesionVO("albertro","alberto");
	private sesionDAO sDAO = new sesionDAO();
	private Connection connection = null;
	
	public pruebasSesion() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	public void pruebaExisteSesion() throws SQLException {
		try {
			sDAO.existeSesion(sVO, connection);
			System.out.println("La sesion del usuario " + sVO.verNombreUsuario() + " ya existe.");
		}
		catch(SesionInexistente e) {
			System.out.println("La sesión del usuario " + sVO.verNombreUsuario() + " no existe.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaInsertarSesion() throws SesionExistente, SQLException{
		try {
			sDAO.insertarSesion(sVO, connection);
			System.out.println("Sesión creada correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaCerrarSesion() throws SesionExistente, SQLException{
		try {
			sDAO.cerrarSesion(sVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Sesión cerrada correctamente.");
	}
	
	public static void main(String args[]) throws Exception {
		pruebasSesion p = new pruebasSesion();
		p.pruebaExisteSesion();
		p.pruebaInsertarSesion();
		p.pruebaExisteSesion();
		p.pruebaCerrarSesion();
		p.pruebaExisteSesion();
	}
}
