package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import modelo.clasesVO.reproduccionVO;
import modelo.excepcion.ExcepcionReproduccion;

public class reproduccionDAO {
	/*
	 * Pre: ---
	 * Post: Ha marcado una determinada canción como reproducida para un usuario.
	 * 		 Si y solo si se produce algún problema al registrar la audición, entonces
	 * 		 lanza una excepción ExcepcionReproduccion.
	 */
	public void anyadirReproduccion (String ruta, String nombreUsuario,
			Connection connection) throws Exception {
		try {
			// Obtenemos los valores asociados a la ruta
			String q = "SELECT titulo, nombreArtista, nombreAlbum "
					 + "FROM Cancion "
					 + "WHERE ruta=?;";
			PreparedStatement p = connection.prepareStatement(q);
			p.setString(1, ruta);
			ResultSet r = p.executeQuery();
			if(!r.first()) {
				throw new Exception("La ruta proporcionada no es válida");
			}
			
			// Guardamos la reproducción
			q = "INSERT INTO Reproduccion(nombreUsuario, titulo, nombreAlbum, nombreArtista) "
			  + "VALUES (?,?,?,?);";
			p = connection.prepareStatement(q);
			p.setString(1, nombreUsuario);
			p.setString(2, r.getString(1));
			p.setString(3, r.getString(3));
			p.setString(4, r.getString(2));
			int b = p.executeUpdate();
			if(b != 0) {
				throw new Exception("No se pudo añadir la reproducción");
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave canciones, cuyo valor asociado es
	 * 		 un array de canciones (claves tituloCancion, nombreArtista y
	 * 		 nombreAlbum), que se corresponden con las 10 canciones más
	 * 		 escuchadas la última semana.
	 * 		 Si algo va mal, lanza una excepción
	 */
	public JSONObject topSemanal(Connection c) throws SQLException {
		// Hacemos la consulta
		// Cogemos las reproducciones de canciones del servidor de los
		// últimos 7 días
		String subQuery1 = "(Select * from Reproduccion where "
						 + "TIMESTAMPDIFF(DAY,fecha,CURRENT_TIMESTAMP)<=7 AND "
						 + "uploader = 'Admin') q1 ";
		// Contamos el número de reproducciones de esas canciones
		String subQuery2 = "(SELECT *, count(*) AS num "
						 + "FROM " + subQuery1
						 + "GROUP BY titulo,nombreAlbum,nombreArtista) q2 ";
		// Nos quedamos con las 10 canciones más escuchadas
		String q = "SELECT q2.titulo, q2.nombreAlbum, q2.nombreArtista "
				 + "FROM " + subQuery2 
				 + " ORDER BY (num) DESC limit 10 ";
		PreparedStatement p = c.prepareStatement(q);
		ResultSet r = p.executeQuery();
		
		// Objetos para devolver el resultado
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		r.beforeFirst(); // Movemos el cursor antes del 1er elemento
		while (r.next()) {
			JSONObject aux = new JSONObject();
			aux.put("tituloCancion", r.getString(1));
			aux.put("nombreArtista", r.getString(2));
			aux.put("nombreAlbum", r.getString(3));
			array.add(aux);
		}
		obj.put("canciones", array);
		return obj;
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve un JSON con la clave canciones, cuyo valor asociado es
	 * 		 un array de canciones (claves tituloCancion, nombreArtista y
	 * 		 nombreAlbum), que se corresponden con las últimas 10 canciones
	 * 		 que el usuario 'usuario' ha escuchado
	 * 		 Si algo va mal, lanza una excepción
	 */
	public JSONObject escuchadasRecientemente(String usuario, Connection c) 
			throws SQLException {
		// Hacmos la consulta
		String q = "Select titulo, nombreArtista, nombreAlbum "
				 + "from Reproduccion "
				 + "where nombreUsuario = ? "
				 + "ORDER BY fecha DESC "
				 + "limit 10;";
		PreparedStatement p = c.prepareStatement(q);
		p.setString(1, usuario);
		ResultSet r = p.executeQuery();
		
		// Objetos para devolver el resultado
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		r.beforeFirst(); // Movemos el cursor antes del 1er elemento
		while (r.next()) {
			JSONObject aux = new JSONObject();
			aux.put("tituloCancion", r.getString(1));
			aux.put("nombreArtista", r.getString(2));
			aux.put("nombreAlbum", r.getString(3));
			array.add(aux);
		}
		obj.put("canciones", array);
		return obj;
	}
}
