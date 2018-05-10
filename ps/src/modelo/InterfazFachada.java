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
	 * Pre:  ---
	 * Post: Comprueba si existe un usuario con 'nombreUsuario' en la base de
	 * 		 de datos. Si existe, lanza una excepción 'UsuarioExistente'
	 */
	public void existeNombreUsuario(String nombreUsuario)
			throws SQLException, UsuarioExistente;
	
	/*
	 * Pre:  ---
	 * Post: Ha cambiado el nombre del usuario 'antiguoNombre' por 'nuevoNombre
	 * 		 Si ha habido algún error, lanza una excepción
	 */
	public void cambiarNombreUsuario(String antiguoNombre, String nuevoNombre)
			throws Exception;
	
	/*
	 * Pre:  ---
	 * Post: Ha cambiado la contraseña del usuario 'usuario'.
	 * 		 Si ha habido algún error, lanza una excepción
	 */
	public void cambiarContrasenyaUsuario(String usuario, String viejaPass, String nuevaPass)
			throws SQLException, ErrorCambiarPass;
	
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
	 * Pre: El usuario 'nombreUsuario' ya existe
	 * Post: Consulta si el usuario 'nombreUsuario' tiene registrada una
	 * 		 sesión con identificador 'idSesion'. En caso de no tenerla,
	 * 		 devuelve una excepción 'SesionInexistente'
	 */
	public void existeSesionUsuario(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException;
	
	
	/*
	 * Pre: El usuario 'nombreUsuario' ya existe
	 * Post: Elimina la sesión identificada por el usuario 'nombreusuario'
	 * 		 y el id de sesión 'idSesion'
	 * 		 Si la sesión no existe entonces lanza una excepción SesionInexistente
	 */
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
	 * Pre:
	 * Post: Comprueba en la BD si existe una canción del album "album".
	 * 		 Si existe alguna, devuelve un json con una clave canciones, cuyo
	 * 		 valor asociado será un array en el que cada componente es una
	 * 		 canción
	 */
	public JSONObject buscarCancionPorGenero(String genero,
			String nombreUploader)
			throws SQLException, CancionNoExiste;

	/*
	 * Pre:
	 * Post: Comprueba en la BD si una lista de reproducción existe para un
	 * 		 usuario dado
	 */
	public void crearListaDeReproduccion(listaReproduccionVO l)
			throws ListaYaExiste, SQLException;
	
	/*
	 * 	Pre: La lista de reproducción seleccionada ya existe
	 * 	Post: Dada una lista de reproducción de un usuario, borra la misma
	 * 		  de la BD
	 * 		  Si la lista no existe, entonces lanza una excepción
	 * 		  'ListaNoExiste'
	 */
	public void borrarListaDeReproduccion(listaReproduccionVO l)
			throws ListaNoExiste, SQLException;
	
	/*
	 * Pre: La canción a añadir ya existe en la biblioteca del usuario
	 * Post: Añade una nueva canción a una lista de reproducción.
	 * 		 Si la canción a añadir ya estaba en la lista seleccionada,
	 * 		 entonces lanza una excepción CancionExisteEnLista
	 */
	public void anyadirCancionALista(formarVO f)
			throws CancionExisteEnLista, SQLException;
	
	/*
	 * Pre: La canción a eliminar existe dentro de la lista d reproducción
	 * 		seleccionada
	 * Post: Elimina una canción de una lista de reproducción
	 * 		 Si no existe, entonces lanza una excepción 'CancionNoExisteEnLista'
	 */
	public void quitarCancionDeLista(formarVO f)
			throws CancionNoExisteEnLista, SQLException;
	
	/*
	 * Pre: La canción c no existe en la biblioteca del usuario
	 * Post: Añade una canción a la biblioteca del usuario (lo que no implica
	 * 		 necesariamente que esté en una lista de reproducción).
	 * 		 Si la canción ya existe, entonces lanza una excepción
	 * 		 CancionYaExiste
	 */
	public void anyadirCancionUsuario(cancionVO c, String lista)
			throws CancionYaExiste, CancionExisteEnLista, SQLException;
	
	/*
	 * Pre: La canción c ya existe en la biblioteca del usuario.
	 * Post: Elimina una canción de la biblioteca del usuario.
	 * 		 Si la canción no existía, entonces lanza una excepción
	 * 		 CancionNoExiste.
	 */
	public void quitarCancionUsuario(cancionVO c)
			throws CancionNoExiste, SQLException, IOException;
	
	/*
	 * Pre:  Los usuarios 'seguidor' y 'seguido' ya existen
	 * Post: Comprueba si 'seguidor' sigue a 'seguido'.
	 * 		 Si no lo hace, lanza una excepción "NoSeguido"
	 */
	public void loSigue(String seguidor, String seguido)
			throws SQLException, NoSeguido;
	
	/*
	 * Pre:  El usuario 'nombreSeguidor' ya existe
	 * Post: Hace que el usuario 'seguidor' empiece a seguir a 'nombreSeguido'.
	 * 		 Si ya seguía al usuario, lanza una excepción 'YaSeguido'.
	 */
	public void seguir(String nombreSeguidor, String nombreSeguido)
			throws SQLException, YaSeguido;
	
	/*
	 * Pre: El usuario 'nombreSeguidor' ya existe
	 * Post: Hace que el usuario 'nombreSeguidor' empiece a seguir a 'nombreSeguido'.
	 * 		 Si no seguía al usuario, lanza una excepción 'ErrorDejarDeSeguir'.
	 */
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido)
			throws ErrorDejarDeSeguir, SQLException;
	
	/*
	 * Pre: El usuario 'nombreSeguidor' ya existe
	 * Post: Devuelve un json con la clave "listaDeSeguidos" y una lista de los
	 * 		 usuarios a los que 'nombreSeguidor' sigue.
	 * 		 Si 'nombreSeguidor' no sigue a nadie, entonces lanza una
	 * 		 excepción 'SinSeguidos'
	 */
	public JSONObject listaDeSeguidos(String nombreSeguidor)
			throws SinSeguidos, SQLException;
	
	/*
	 * Pre: ---
	 * Post: Devuelve un json con la clave 'canciones' y una lista de las
	 * 		 canciones que contiene una lista dada de un usuario dado.
	 * 		 Si la lista de reproducción seleccionada está vacía, entonces
	 * 		 devuelve una excepción 'NoHayCanciones'.
	 */
	public JSONObject verLista(listaReproduccionVO l)
			throws NoHayCanciones, SQLException;
	
	/*
	 * Pre: El usuario 'nombreSeguido' ya existe
	 * Post: Devuelve un json con la clave "listaDeSeguidores" y una lista de los
	 * 		 usuarios que siguen a 'nombreSeguido'.
	 * 		 Si 'nombreSeguido' no tiene seguidores, entonces lanza una
	 * 		 excepción 'SinSeguidores'
	 */
	public JSONObject listaDeSeguidores(String nombreSeguido)
			throws SinSeguidores, SQLException;

	/*
	 * Pre:  ---
	 * Post: Dado el nombre de un usuario, devuelve un JSON con una clave
	 * 		 llamada "listas", cuyo valor asociado es un array de strings
	 * 		 que contiene el nombre de las diferentes listas del usuario
	 */
	JSONObject mostrarListasUsuario(String nombreUsuario)
			throws SQLException, NoHayListas;
	
	/*
	 * Pre:  ---
	 * Post: Dada la cadena de caracteres 'nombre' busca usuarios cuyo nombre
	 * 		 sea igual o empiece por nombre, devolviéndolos en un JSON
	 * 		 cuya clave es usuarios, cuyo valor asociado es un array de strings
	 *  	 con los nombres encontrados
	 */
	public JSONObject buscarUsuarios(String nombreBuscado, String nombreBuscador)
			throws SQLException,UsuarioInexistente;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve la ruta de la canción especificada. En caso de no existir
	 * 		 dicha canción, lanza una excepción
	 */
	public String obtenerRuta(String titulo, String artista, String album,
			String nombreUsuario) throws Exception;
	
	/*
	 * Pre:  ---
	 * Post: Ha actualizado la ruta de la imagen del usuario a 'ruta'.
	 * 		 Si algo ha ido mal, lanza una excepción
	 */
	public void actualizarImagen(String usuario, String ruta) throws Exception;
	
	/*
	 * Pre:  ---
	 * Post: Borra, si existe, la cuenta asociada al nombre 'nombreUsuario'
	 */
	public void eliminarCuenta(String nombreUsuario)
			throws SQLException;
	
	/*
	 * Pre:
	 * Post: Devuelve un JSON con la clave generos, cuyo valor asociado es un
	 * 		 array de strings que contiene en cada componente un género de los
	 * 		 pertenecientes al servidor o al usuario.
	 */
	public JSONObject getGeneros(String user) throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave canciones, cuyo valor asociado es
	 * 		 un array de canciones (claves tituloCancion, nombreArtista y
	 * 		 nombreAlbum), que se corresponden con las 10 canciones más escuchadas la
	 * 		 última semana.
	 * 		 Si algo va mal, lanza una excepción
	 */
	public JSONObject topSemanal() throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave "artistas", cuyo valor asociado
	 * 		 es un array de strings con todos los artistas que hay en el
	 * 		 servidor
	 */
	public JSONObject getArtistas(String user) throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave "albums", cuyo valor asociado
	 * 		 es un array de albums. Cada uno de estos albums contiene las
	 * 		 claves nombre y artista
	 */
	public JSONObject getAlbums(String user) throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave "albums", cuyo valor asociado
	 * 		 es un array de strings, en el que cada componente se corresponde
	 * 		 con el nombre de un album.
	 */
	public JSONObject getAlbumsArtista(String artista, String user)
			throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave "artistas", cuyo valor asociado
	 * 		 es un array de strings con todos los artistas obtenidos en la
	 * 		 búsqueda
	 */
	public JSONObject buscarArtista(String artista, String user)
			throws SQLException;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave "albums", cuyo valor asociado
	 * 		 es un array de albums. Cada uno de estos albums contiene las
	 * 		 claves nombre y artista. El nombre de estos albums será o empezará
	 * 		 por 'album'.
	 */
	public JSONObject buscarAlbum(String album, String user)
			throws SQLException;
	
	/*
	 * Pre: ---
	 * Post: Registra en la BD que el usuario 'usuarioOrigen' ha compartido una determinada
	 * 		 canción con el usuario 'usuarioDestino'.
	 */
	public void compartirCancion(compartirVO cancion)
			throws Exception;
	
	/*
	 * Pre: ---
	 * Post: Registra en la BD que el usuario 'usuarioDestino' ha borrado una canción
	 * 		 compartida por el usuario 'usuarioOrigen'.
	 */
	public void eliminarComparticion(compartirVO cancion)
			throws Exception;
	
	/*
	 * Pre: ---
	 * Post: Devuelve un objeto JSON que contiene la lista de todas las canciones compartidas
	 * 		 con el usuario 'usuarioDestino'.
	 * 		 Si nadie ha compartido canciones con el usuario 'usuarioDestino', entonces lanza
	 * 		 una excepción 'SinCompartidas'.
	 */
	public JSONObject devolverCompartidas(String usuarioDestino)
			throws SQLException, SinCompartidas;
	/*
	 * Pre:	 ---
	 * Post: Devuelve un JSON con la clave "busquedaListas", cuyo valor asociado
	 * 		 es un array de listas de reproducción que contienen el nombre de la
	 * 		 lista y de su creador.
	 */
	public JSONObject buscarLista(String lista, String yo)
			throws SQLException, SinCoincidenciasListas;
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave canciones, cuyo valor asociado es
	 * 		 un array de canciones (claves tituloCancion, nombreArtista y
	 * 		 nombreAlbum), que se corresponden con las últimas 10 canciones
	 * 		 que el usuario 'usuario' ha escuchado
	 * 		 Si algo va mal, lanza una excepción
	 */
	public JSONObject recientes(String usuario) throws SQLException;
	
	/*
	 * Pre: ---
	 * Post: Cambia el nombre actual de una lista de reproducción por 'nombreNuevo' si y solo si
	 * 		 la lista ya existe (lo cuál incluye que el usuario exista), de lo contrario, lanza
	 * 		 una excepción 'ListaNoExiste'.
	 */
	public void cambiarNombreLista(listaReproduccionVO listaVieja, String nombreNuevo) 
			throws SQLException, ListaNoExiste;

	public int solicitarId() throws SQLException;

	/*
	 * Pre:  ---
	 * Post: Ha añadido una reproducción de la canción identidicada por titulo,
	 * 		 artista y album a la base de datos
	 */
	public void anyadirReproduccion(String ruta, String nombreUsuario)
			throws Exception;
}
