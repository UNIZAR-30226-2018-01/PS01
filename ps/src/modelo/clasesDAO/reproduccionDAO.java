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
			// Guardamos la reproducción
			String q = "INSERT INTO Reproduccion(nombreUsuario, ruta) VALUES (?,?);";
			PreparedStatement p = connection.prepareStatement(q);
			p.setString(1, nombreUsuario);
			p.setString(2, ruta);
			p.executeUpdate();
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
		String q =  "Select t1.titulo, t1.nombreArtista, t1.nombreAlbum, t1.genero, t1.ruta\n" + 
					"FROM\n" + 
					"(Select c.titulo, c.nombreArtista, c.nombreAlbum, c.genero, c.ruta, count(*) as numReproducciones\n" + 
					"from Reproduccion r JOIN Cancion c ON r.ruta=c.ruta\n" + 
					"where TIMESTAMPDIFF(DAY,fecha,CURRENT_TIMESTAMP)<7 and nombreUsuario='Admin'\n" + 
					"GROUP BY c.titulo, c.nombreArtista, c.nombreAlbum, c.genero, c.ruta) t1\n" + 
					"ORDER BY (numReproducciones) DESC\n" + 
					"LIMIT 10;";
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
			aux.put("genero", r.getString(4));
			aux.put("ruta", r.getString(5));
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
		String q = "SELECT titulo, nombreArtista, nombreAlbum, genero, ruta "
				 + "FROM "
				   + "(SELECT titulo, nombreArtista, nombreAlbum, genero, ruta, max(fecha) as fecha "
				   + "FROM "
				     + "(SELECT c.titulo, c.nombreArtista, c.nombreAlbum, c.genero, c.ruta, r.fecha "
				     + "from Reproduccion r JOIN Cancion c ON r.ruta=c.ruta "
				     + "where r.nombreUsuario = ?) t1 "
				     + "GROUP BY titulo, nombreArtista, nombreAlbum, genero, ruta "
				   + ") t2 "
				   + "ORDER BY (fecha) DESC "
				   + "LIMIT 10;";
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
			aux.put("genero", r.getString(4));
			aux.put("ruta", r.getString(5));
			array.add(aux);
		}
		obj.put("canciones", array);
		return obj;
	}
}
