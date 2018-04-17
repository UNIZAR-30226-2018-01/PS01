package modelo;

import modelo.excepcion.*;
import java.io.IOException;
import java.sql.SQLException;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.FuncionesAuxiliares;
import org.json.simple.*;

public class ImplementacionFachada implements InterfazFachada {
	/*
	 * Pre:  'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de su contraseña, comprueba
	 * 		 si existe una entrada en la base de datos con ese nombre de usuario
	 * 		 y ese hash de contraseña. En caso contrario, lanza una excepción
	 */
	@Override
	public void existeUsuario(String nombreUsuario, String hashPass)
			throws LoginInexistente, SQLException {
		try {
			new usuarioDAO().hayUsuario(new usuarioVO(nombreUsuario, hashPass),
					FuncionesAuxiliares.obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: 'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de la contraseña, lo ha
	 * 		 registrado en la base de datos. En caso de que ya existiera un
	 * 		 usuario con ese nombre, lanza una excepción 'UsuarioYaRegistrado'
	 */
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
	
	/*
	 * Pre: 'nombreUsuario' es el nombre de un usuario existente y 'idSesion'
	 * 		es el id de una nueva sesión
	 * Post: Ha registrado una nueva sesión 'id' por parte del usuario
	 * 		 'nombreUsuario'
	 */
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
	
	/*
	 * Pre: El usuario 'nombreUsuario' ya existe
	 * Post: Consulta si el usuario 'nombreUsuario' tiene registrada una
	 * 		 sesión con identificador 'idSesion'. En caso de no tenerla,
	 * 		 devuelve una excepción 'SesionInexistente'
	 */
	@Override
	public void existeSesionUsuario(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException{
		try {
			new sesionDAO().existeSesion(new sesionVO(idSesion, nombreUsuario),
					FuncionesAuxiliares.obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}

	/*
	 * Pre: El usuario 'nombreUsuario' ya existe
	 * Post: Elimina la sesión identificada por el usuario 'nombreusuario'
	 * 		 y el id de sesión 'idSesion'
	 * 		 Si la sesión no existe entonces lanza una excepción SesionInexistente
	 */
	@Override
	public void cerrarSesion(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException {
		try {
			new sesionDAO().cerrarSesion(new sesionVO(idSesion, nombreUsuario),
					FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
		
	}
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si una lista de reproducción existe para un
	 * 		 usuario dado
	 */
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

	/*
	 * 	Pre: La lista de reproducción seleccionada ya existe
	 * 	Post: Dada una lista de reproducción de un usuario, borra la misma
	 * 		  de la BD
	 * 		  Si la lista no existe, entonces lanza una excepción
	 * 		  'ListaNoExiste'
	 */
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

	/*
	 * Pre: La canción a añadir ya existe en la biblioteca del usuario
	 * Post: Añade una nueva canción a una lista de reproducción.
	 * 		 Si la canción a añadir ya estaba en la lista seleccionada,
	 * 		 entonces lanza una excepción CancionExisteEnLista
	 */
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

	/*
	 * Pre: La canción a eliminar existe dentro de la lista d reproducción
	 * 		seleccionada
	 * Post: Elimina una canción de una lista de reproducción
	 * 		 Si no existe, entonces lanza una excepción 'CancionNoExisteEnLista'
	 */
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

	/*
	 * Pre: La canción c no existe en la biblioteca del usuario
	 * Post: Añade una canción a la biblioteca del usuario (lo que no implica
	 * 		 necesariamente que esté en una lista de reproducción).
	 * 		 Si la canción ya existe, entonces lanza una excepción
	 * 		 CancionYaExiste
	 */
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

	/*
	 * Pre: La canción c ya existe en la biblioteca del usuario.
	 * Post: Elimina una canción de la biblioteca del usuario.
	 * 		 Si la canción no existía, entonces lanza una excepción
	 * 		 CancionNoExiste.
	 */
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
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción de título "titulo".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	@Override
	public JSONObject buscarCancionPorTitulo(String titulo,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			return new cancionDAO().
					buscarCancionPorTitulo(new cancionVO(titulo, "", "", "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción con artista "artista".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	@Override
	public JSONObject buscarCancionPorArtista(String artista,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			return new cancionDAO().
					buscarCancionPorArtista(new cancionVO("", artista, "", "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción del album "album".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	@Override
	public JSONObject buscarCancionPorAlbum(String album,
			String nombreUploader)
			throws SQLException, CancionNoExiste {
		try {
			return new cancionDAO().
					buscarCancionPorAlbum(new cancionVO("", "", album, "", ""),
							nombreUploader,
							FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Pre:  El usuario 'nombreSeguidor' ya existe
	 * Post: Hace que el usuario 'seguidor' empiece a seguir a 'nombreSeguido'.
	 * 		 Si ya seguía al usuario, lanza una excepción 'YaSeguido'.
	 */
	@Override
	public void seguir(String nombreSeguidor, String nombreSeguido)
			throws SQLException, YaSeguido {
		try {
			new seguirDAO().seguir(nombreSeguidor, nombreSeguido,
					FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: El usuario 'nombreSeguidor' ya existe
	 * Post: Hace que el usuario 'nombreSeguidor' empiece a seguir a 'nombreSeguido'.
	 * 		 Si no seguía al usuario, lanza una excepción 'ErrorDejarDeSeguir'.
	 */
	@Override
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido) throws SQLException, ErrorDejarDeSeguir {
		try {
			new seguirDAO().dejarDeSeguir(nombreSeguidor, nombreSeguido, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Pre: El usuario 'nombreSeguidor' ya existe
	 * Post: Devuelve un json con la clave "listaDeSeguidos" y una lista de los
	 * 		 usuarios a los que 'nombreSeguidor' sigue.
	 * 		 Si 'nombreSeguidor' no sigue a nadie, entonces lanza una
	 * 		 excepción 'SinSeguidos'
	 */
	@Override
	public JSONObject listaDeSeguidos(String nombreSeguidor) throws SinSeguidos, SQLException {
		try {
			return new seguirDAO().listaDeSeguidos(nombreSeguidor, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Pre: ---
	 * Post: Devuelve un json con la clave 'canciones' y una lista de las
	 * 		 canciones que contiene una lista dada de un usuario dado.
	 * 		 Si la lista de reproducción seleccionada está vacía, entonces
	 * 		 devuelve una excepción 'NoHayCanciones'.
	 */
	@Override
	public JSONObject verLista(listaReproduccionVO l) throws NoHayCanciones, SQLException {
		try {
			return new formarDAO().verLista(l, FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: El usuario 'nombreSeguido' ya existe
	 * Post: Devuelve un json con la clave "listaDeSeguidores" y una lista de los
	 * 		 usuarios que siguen a 'nombreSeguido'.
	 * 		 Si 'nombreSeguido' no tiene seguidores, entonces lanza una
	 * 		 excepción 'SinSeguidores'
	 */
	@Override
	public JSONObject listaDeSeguidores(String nombreSeguido) throws SinSeguidores, SQLException {
		try {
			return new seguirDAO().listaDeSeguidores(nombreSeguido,
					FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:  Los usuarios 'seguidor' y 'seguido' ya existen
	 * Post: Comprueba si 'seguidor' sigue a 'seguido'.
	 * 		 Si no lo hace, lanza una excepción "NoSeguido"
	 */
	@Override
	public void loSigue(String seguidor, String seguido)
			throws SQLException, NoSeguido {
		try {
			new seguirDAO().loSigue(seguidor, seguido,
					FuncionesAuxiliares.obtenerConexion());
		}
		catch(Exception e) {
			throw e;
		}
	}

	/*
	 * Pre:  ---
	 * Post: Dado el nombre de un usuario, devuelve un JSON con una clave
	 * 		 llamada "listas", cuyo valor asociado es un array de strings
	 * 		 que contiene el nombre de las diferentes listas del usuario
	 */
	public JSONObject mostrarListasUsuario(String nombreUsuario) throws SQLException, NoHayListas {
		try {
			return new listaReproduccionDAO().devolverListas(nombreUsuario,
					FuncionesAuxiliares.obtenerConexion());
		}
		catch (Exception e) {
			throw e;
		}
	}
}