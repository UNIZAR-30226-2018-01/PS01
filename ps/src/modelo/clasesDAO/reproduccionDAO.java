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
	public void anyadirReproduccion (reproduccionVO repro, Connection connection)
			throws ExcepcionReproduccion, SQLException {
		try {
			String queryString = "INSERT INTO Reproduccion(nombreUsuario, titulo, nombreAlbum, nombreArtista) "
					+ "VALUES (?,?,?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			preparedStatement.setString(1, repro.verNombreUsuario());
			preparedStatement.setString(2, repro.verTituloCancion());
			preparedStatement.setString(3, repro.verNombreAlbum());
			preparedStatement.setString(4, repro.verNombreArtista());
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp != 0) {
	        		throw new ExcepcionReproduccion("Error al marcar la canción " + repro.verTituloCancion() + " como reproducida "
	        				+ "para el usuario" + repro.verNombreUsuario() + ".");
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
