package modelo.clasesDAO;

import modelo.clasesVO.compartirVO;
import modelo.excepcion.NoHayCanciones;
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
			throws SQLException {
		try {
			String s = "INSERT INTO Compartir (usuarioOrigen, titulo, nombreAlbum,"
					+ "nombreArtista, genero, usuarioDestino) "
					+ "VALUES (?,?,?,?,?,?);";
			
			PreparedStatement preparedStatement = c.prepareStatement(s);
			
			preparedStatement.setString(1, cancion.verUsuarioOrigen());
			preparedStatement.setString(2, cancion.verTituloCancion());
			preparedStatement.setString(3, cancion.verNombreAlbum());
			preparedStatement.setString(4, cancion.verNombreArtista());
			preparedStatement.setString(5, cancion.verGenero());
			preparedStatement.setString(6, cancion.verUsuarioDestino());
			
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
			throws SQLException {
		try {
			String s = "DELETE FROM Compartir "
					 + "WHERE usuarioOrigen = ? AND "
					 + "titulo = ? AND "
					 + "nombreAlbum = ? AND "
					 + "nombreArtista = ? AND "
					 + "usuarioDestino = ? AND "
					 + "fecha = ?;";
			
			PreparedStatement p = c.prepareStatement(s);
			
			p.setString(1, cancion.verUsuarioOrigen());
			p.setString(2, cancion.verTituloCancion());
			p.setString(3, cancion.verNombreAlbum());
			p.setString(4, cancion.verNombreArtista());
			p.setString(5, cancion.verUsuarioDestino());
			p.setString(6, cancion.verFecha());
			
			p.executeUpdate();
		}
		catch (Exception e) {
			throw e;
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
			String s = "SELECT Cancion.titulo, Cancion.nombreArtista, "
					   + "Cancion.nombreAlbum, Cancion.genero, "
					   + "Cancion.uploader, Cancion.ruta "
					   + "FROM (SELECT * FROM Compartir where usuarioDestino= ?) c1 "
					   + "JOIN Cancion "
					   + "ON (c1.titulo = Cancion.titulo AND "
					   + "c1.nombreArtista = Cancion.nombreArtista AND "
					   + "c1.nombreAlbum = Cancion.nombreAlbum) "
					   + "ORDER BY c1.fecha DESC;";
			
			PreparedStatement preparedStatement = c.prepareStatement(s);
			preparedStatement.setString(1, usuarioDestino);
			
			ResultSet resultado = preparedStatement.executeQuery();
			
			if (!resultado.first()) {
				throw new SinCompartidas("Nadie ha compartido canciones con el usuario " + usuarioDestino);
			}
			else {
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
					array.add(aux);
				}
				obj.put("canciones", array);
				return obj;
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
}
