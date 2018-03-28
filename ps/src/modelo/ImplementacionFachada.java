package modelo;

import modelo.excepcion.*;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.mysql.jdbc.Connection;

import modelo.clasesDAO.*;
import modelo.clasesVO.*;

public class ImplementacionFachada implements InterfazFachada {

	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	private static Connection obtenerConexion() throws SQLException {
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/ConexionMySQL");
			return (Connection) ds.getConnection();
		}
		catch(NamingException e) {
			System.out.println("Error al obtener conexion del pool");
		}
		return null;
	}
	
	@Override
	public void iniciarSesion(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			new usuarioDAO().hayUsuario(new usuarioVO(nombreUsuario, hashPass),
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
	public void nuevaSesion(String nombreUsuario, String idSesion)
			throws SesionExistente, SQLException {
		try {
			new sesionDAO().insertarSesion(new sesionVO(nombreUsuario, idSesion),
					obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void cerrarSesion(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException {
		try {
			new sesionDAO().cerrarSesion(new sesionVO(nombreUsuario, idSesion),
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public void crearListaDeReproduccion(listaReproduccionVO l)
			throws ListaYaExiste, SQLException {
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
			throws ErrorAnyadirMegusta, SQLException {
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
			throws ErrorQuitarMegusta, SQLException {
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
			throws ExcepcionReproduccion, SQLException {
		try {
			new reproducirDAO().anyadirReproduccion(r,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void anyadirCancionALista(formarVO f)
			throws CancionExisteEnLista, SQLException {
		try {
			new formarDAO().anyadirCancionALista(f,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void quitarCancionDeLista(formarVO f)
			throws CancionNoExisteEnLista, SQLException {
		try {
			new formarDAO().quitarCancionDeLista(f,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void anyadirAudicionLista(escucharVO e)
			throws ExcepcionEscuchar, SQLException {
		try {
			new escucharDAO().anyadir(e,
					obtenerConexion());
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void anyadirCancionUsuario(cancionVO c)
			throws CancionYaExiste, SQLException {
		try {
			new cancionDAO().anyadirCancion(c,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void quitarCancionUsuario(cancionVO c)
			throws CancionNoExiste, SQLException {
		try {
			new cancionDAO().quitarCancion(c,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void anyadirArtistaAlbum(artistaAlbumVO a)
			throws ArtistaAlbumExiste, SQLException {
		try {
			new artistaAlbumDAO().anyadirArtistaAlbum(a,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void quitarArtistaAlbum(artistaAlbumVO a)
			throws ArtistaAlbumNoExiste, SQLException {
		try {
			new artistaAlbumDAO().quitarArtistaAlbum(a,
					obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
}