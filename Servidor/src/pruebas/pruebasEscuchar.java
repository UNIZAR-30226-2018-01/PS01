package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.clasesDAO.escucharDAO;
import modelo.clasesVO.escucharVO;
import modelo.excepcion.ExcepcionEscuchar;

public class pruebasEscuchar {
	private escucharVO eVO = new escucharVO("lista_1","alberto","alberto");
	private escucharDAO eDAO = new escucharDAO();
	private Connection connection = null;
	
	public void pruebaExisteEscuchar() throws SQLException {
		try {
			if (eDAO.existeEscuchar(eVO, connection)) {
				System.out.println("El usuario ya había escuchado la lista " + eVO.verNombreListener() + " antes en la misma fecha.");
			}
			else {
				System.out.println("El usuario nunca escuchado la lista " + eVO.verNombreListener() + " antes en la misma fecha.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaAnyadirEscuchar() throws  ExcepcionEscuchar, SQLException {
		try {
			eDAO.anyadir(eVO, connection);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("La audición de la lista " + eVO.verNombreLista() + " se ha registrado correctamente.");
	}
	
	public static void main(String args[]) {
		
	}
}
