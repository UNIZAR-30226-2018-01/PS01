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
import modelo.FuncionesAuxiliares;

public class ImplementacionFachada implements InterfazFachada {

	
	@Override
	public void iniciarSesion(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			new usuarioDAO().hayUsuario(new usuarioVO(nombreUsuario, hashPass),
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public void existeSesionUsuario(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException{
		try {
			new sesionDAO().existeSesion(new sesionVO(nombreUsuario, idSesion),
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
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
					FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
}
