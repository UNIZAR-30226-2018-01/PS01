package modelo;

import modelo.excepcion.*;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;

public class ImplementacionFachada implements InterfazFachada {

	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	private static Connection obtenerConexion() {
		Connection c = null;
		return c;
	}
	
	@Override
	public void iniciarSesion(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			new usuarioDAO().iniciarSesion(new usuarioVO(nombreUsuario, hashPass),
					obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public void registrarUsuario(String nombreUsuario, String hashPass)
			throws UsuarioYaRegistrado, SQLException {
		try {
			new usuarioDAO().insertarUsuario(new usuarioVO(nombreUsuario, hashPass),
					obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void cerrarSesion(String nombreUsuario)
			throws UsuarioSinLoguear, SQLException, Exception {
		try {
			new sesionDAO().cerrarSesion(nombreUsuario,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public void crearListaDeReproduccion(listaReproduccionVO l)
			throws ListaYaExiste, SQLException, Exception {
		try {
			new listaReproduccionDAO().anyadirLista(l,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void borrarListaDeReproduccion(listaReproduccionVO l)
			throws ListaNoExiste, SQLException {
		try {
			new listaReproduccionDAO().quitarLista(l,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void megusta(gustarVO g)
			throws ErrorAnyadirMegusta, SQLException, Exception {
		try {
			new gustarDAO().megusta(g,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void yanomegusta(gustarVO g)
			throws ErrorQuitarMegusta, SQLException, Exception {
		try {
			new gustarDAO().yanomegusta(g,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void anyadirReproduccion(reproducirVO r)
			throws ExcepcionReproduccion, SQLException, Exception {
		try {
			new reproducirDAO().anyadirReproduccion(r,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
}
