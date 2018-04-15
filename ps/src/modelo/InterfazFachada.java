package modelo;

import modelo.clasesVO.*;
import modelo.excepcion.*;
import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.*;

/*
 * Interfaz de la fachada. Incluye las operaciones que se pueden invocar desde
 * los servlets para insertar o comprobar datos de la BD
 */
public interface InterfazFachada {
	/*
	 * Pre:  'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de su contraseña, comprueba
	 * 		 si existe una entrada en la base de datos con ese nombre de usuario
	 * 		 y ese hash de contraseña. En caso contrario, lanza una excepción
	 */
	public void existeUsuario(String nombreUsuario, String hashPass) 
			throws LoginInexistente, SQLException;
	
	/*
	 * Pre: 'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de la contraseña, lo ha
	 * 		 registrado en la base de datos. En caso de que ya existiera un
	 * 		 usuario con ese nombre, lanza una excepción 'UsuarioYaRegistrado'
	 */
	public void registrarUsuario(String nombreUsuario, String hashPass) 
			throws UsuarioYaRegistrado, SQLException;
	
	/*
	 * Pre: 'nombreUsuario' es el nombre de un usuario existente y 'idSesion'
	 * 		es el id de una nueva sesión
	 * Post: Ha registrado una nueva sesión 'id' por parte del usuario
	 * 		 'nombreUsuario'
	 */
	public void nuevaSesion(String nombreUsuario, String idSesion)
			throws SesionExistente, SQLException;
	
	/*
	 * Pre:
	 * Post: Consulta si el usuario 'nombreUsuario' tiene registrada una
	 * 		 sesión con identificador 'idSesion'. En caso de no tenerla,
	 * 		 devuelve una excepción 'SesionInexistente'
	 */
	public void existeSesionUsuario(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException;
	
	
	public void cerrarSesion(String nombreusuario, String idSesion)
			throws SesionInexistente, SQLException;
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción de título "titulo".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	public JSONObject buscarCancionPorTitulo(String titulo,
			String nombreUploader)
			throws SQLException, CancionNoExiste;
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción con artista "artista".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	public JSONObject buscarCancionPorArtista(String artista,
			String nombreUploader)
			throws SQLException, CancionNoExiste;
	
	/*
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción del album "album".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	public JSONObject buscarCancionPorAlbum(String album,
			String nombreUploader)
			throws SQLException, CancionNoExiste;
	
	/*
	 * Pre:  ---
	 * Post: Dado el nombre de un usuario, devuelve un JSON con una clave
	 * 		 llamada "listas", cuyo valor asociado es un array de strings
	 * 		 que contiene el nombre de las diferentes listas del usuario
	 */
	public JSONObject obtenerListasReproducción(String nombreUsuario)
			throws SQLException, NoHayListas;

	public void crearListaDeReproduccion(listaReproduccionVO l)
			throws ListaYaExiste, SQLException;
	
	public void borrarListaDeReproduccion(listaReproduccionVO l)
			throws ListaNoExiste, SQLException;
	
	public void anyadirCancionALista(formarVO f)
			throws CancionExisteEnLista, SQLException;
	
	public void quitarCancionDeLista(formarVO f)
			throws CancionNoExisteEnLista, SQLException;
	
	public void anyadirCancionUsuario(cancionVO c)
			throws CancionYaExiste, SQLException;
	
	public void quitarCancionUsuario(cancionVO c)
			throws CancionNoExiste, SQLException, IOException;
	
	/*
	 * Pre:  ---
	 * Post: Comprueba si 'seguidor' sigue a 'seguido'. Si no lo hace,
	 * 		 lanza una excepción "NoSeguido"
	 */
	public void loSigue(String seguidor, String seguido)
			throws SQLException, NoSeguido;
	
	/*
	 * Pre:  ---
	 * Post: Hace que el usuario 'seguidor' empiece a seguir a 'nombreSeguido'.
	 * 		 Si ya seguía al usuario, devuelve lanza una excepción 'YaSeguido'.
	 */
	public void seguir(String nombreSeguidor, String nombreSeguido)
			throws SQLException, YaSeguido;
	
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido)
			throws ErrorDejarDeSeguir, SQLException;
	
	public JSONObject listaDeSeguidos(String nombreSeguidor)
			throws SinSeguidos, SQLException;
	
	public JSONObject verLista(listaReproduccionVO l)
			throws NoHayCanciones, SQLException;
	
	public JSONObject listaDeSeguidores(String nombreSeguido)
			throws SinSeguidores, SQLException;

	JSONObject mostrarListasUsuario(String nombreUsuario)
			throws SQLException, NoHayListas;
}
