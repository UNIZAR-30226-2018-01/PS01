package modelo;

import modelo.excepcion.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.FuncionesAuxiliares;
import org.json.simple.*;

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
			new sesionDAO().insertarSesion(new sesionVO(idSesion, nombreUsuario),
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
		System.out.println("Insertando canción en la base de datos 2...");
		try {
			System.out.println("Insertando canción en la base de datos 2.1...");
			new cancionDAO().anyadirCancion(c,
					FuncionesAuxiliares.obtenerConexion());
			System.out.println("Canción insertada con éxito");
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void quitarCancionUsuario(cancionVO c)
			throws CancionNoExiste, SQLException, IOException {
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
	
	@Override
	public JSONObject buscarCancionPorTitulo(String titulo,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			JSONObject v = new cancionDAO().
					buscarCancionPorTitulo(new cancionVO(titulo, "", "", "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
			return v;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Vector<cancionVO> buscarCancionPorArtista(String artista,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			Vector<cancionVO> v = new cancionDAO().
					buscarCancionPorArtista(new cancionVO("", artista, "", "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
			return v;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Vector<cancionVO> buscarCancionPorAlbum(String album,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			Vector<cancionVO> v = new cancionDAO().
					buscarCancionPorAlbum(new cancionVO("", "", album, "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
			return v;
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void seguir(String nombreSeguidor, String nombreSeguido) throws SQLException {
		try {
			new seguirDAO().seguir(nombreSeguidor, nombreSeguido, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido) throws SQLException, ErrorDejarDeSeguir {
		try {
			new seguirDAO().dejarDeSeguir(nombreSeguidor, nombreSeguido, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Vector<seguirVO> listaDeSeguidos(String nombreSeguidor) throws SinSeguidos, SQLException {
		try {
			return new seguirDAO().listaDeSeguidos(nombreSeguidor, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Vector<cancionVO> verLista(listaReproduccionVO l) throws NoHayCanciones, SQLException {
		try {
			return new formarDAO().verLista(l, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Vector<seguirVO> listaDeSeguidores(String nombreSeguido) throws SinSeguidores, SQLException {
		try {
			return new seguirDAO().listaDeSeguidores(nombreSeguido, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
}
