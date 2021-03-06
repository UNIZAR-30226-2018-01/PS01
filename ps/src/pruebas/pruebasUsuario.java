package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasUsuario {
	private usuarioDAO uDAO = new usuarioDAO();
	private usuarioVO uVO = new usuarioVO("alberto", "albertro");
	private Connection connection = null;
	
	public pruebasUsuario() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	//Pruebas de las clases usuarioVO y usuarioDAO
	
	public void pruebaExisteUsuario() throws SQLException {
		try {
			if (uDAO.existeUsuario(uVO.verNombre(), connection)) {
				System.out.println("El usuario " + uVO.verNombre() + " ya existe.");
			}
			else {
				System.out.println("El usuario " + uVO.verNombre() + " no existe.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
		
	public void pruebaInsertarUsuario() throws UsuarioYaRegistrado, SQLException{
		try {
			new usuarioDAO().insertarUsuario(uVO, connection);
			System.out.println("Usuario insertado correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
			
	public static void main(String args[]) throws Exception {
		pruebasUsuario p = new pruebasUsuario();
		
		p.pruebaExisteUsuario();
		p.pruebaInsertarUsuario();
		p.pruebaExisteUsuario();
	}
}
