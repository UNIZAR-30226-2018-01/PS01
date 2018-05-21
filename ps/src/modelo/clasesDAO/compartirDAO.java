package modelo.clasesDAO;

import modelo.clasesVO.compartirVO;
import modelo.excepcion.SinCompartidas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class compartirDAO {
	/*
	 * Pre: ---
	 * Post: Registra en la BD que el usuario 'usuarioOrigen' ha compartido una determinada
	 * 		 canción con el usuario 'usuarioDestino'.
	 */
	public void compartirCancion(compartirVO cancion, Connection c)
			throws Exception {
		try {
			String s = "INSERT INTO Compartir(ruta,usuarioOrigen,usuarioDestino) "
					+ "VALUES (?,?,?);";
			
			PreparedStatement preparedStatement = c.prepareStatement(s);
			
			preparedStatement.setString(1, cancion.getRuta());
			preparedStatement.setString(2, cancion.getUsuarioOrigen());
			preparedStatement.setString(3, cancion.getUsuarioDestino());
			
			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Registra en la BD que el usuario 'usuarioDestino' ha borrado una canción
	 * 		 compartida por el usuario 'usuarioOrigen'.
	 */
	public void eliminarComparticion(compartirVO cancion, Connection c)
			throws Exception {
		try {
			String s = "DELETE FROM Compartir "
					 + "usuarioDestino = ? AND "
					 + "ruta = ?;";
			
			PreparedStatement p = c.prepareStatement(s);
			
			p.setString(1, cancion.getUsuarioDestino());
			p.setString(2, cancion.getRuta());
			p.executeUpdate();
		}
		catch (Exception e) {
			throw new Exception("La canción no estaba compartida");
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Devuelve un objeto JSON que contiene la lista de todas las canciones compartidas
	 * 		 con el usuario 'usuarioDestino'.
	 * 		 Si nadie ha compartido canciones con el usuario 'usuarioDestino', entonces lanza
	 * 		 una excepción 'SinCompartidas'.
	 */
	public JSONObject devolverCompartidas(String usuarioDestino, Connection c)
			throws SQLException, SinCompartidas {
		try {
			String s =  "SELECT c2.titulo, c2.nombreArtista, c2.nombreAlbum,\n" + 
						"       c2.genero, c2.uploader, c2.ruta, c2.ruta_imagen\n" + 
						" FROM \n" + 
						"	(SELECT ruta, max(fecha) as fecha\n" + 
						"	FROM Compartir\n" + 
						"	WHERE usuarioDestino = ?\n" + 
						"	GROUP BY ruta) c1 \n" + 
						"	JOIN Cancion c2 ON c1.ruta=c2.ruta\n" + 
						"ORDER BY(c1.fecha) DESC " +
						"LIMIT 10;";
				
			PreparedStatement preparedStatement = c.prepareStatement(s);
			preparedStatement.setString(1, usuarioDestino);
			
			ResultSet resultado = preparedStatement.executeQuery();
			
//			if (!resultado.first()) {
//				throw new SinCompartidas("Nadie ha compartido canciones con el usuario " + usuarioDestino);
//			}
//			else {
				// Objetos para devolver el resultado
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				resultado.beforeFirst(); // Movemos el cursor antes del 1er elemento
				while (resultado.next()) {
					JSONObject aux = new JSONObject();
					aux.put("tituloCancion", resultado.getString(1));
					aux.put("nombreArtista", resultado.getString(2));
					aux.put("nombreAlbum", resultado.getString(3));
					aux.put("genero", resultado.getString(4));
					aux.put("uploader", resultado.getString(5));
					aux.put("ruta", resultado.getString(6));
					aux.put("ruta_imagen", resultado.getString(7));
					array.add(aux);
				}
				obj.put("canciones", array);
				return obj;
//			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
