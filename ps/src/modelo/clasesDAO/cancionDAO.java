package modelo.clasesDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.clasesVO.cancionVO;
import modelo.excepcion.CancionNoExiste;
import modelo.excepcion.CancionYaExiste;
import org.json.simple.*;

public class cancionDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado la canción 'cancion' en la tabla Cancion de la BD.
	 * 		 Si ya existía una cancion con el mismo nombre, perteneciente al mismo álbum/artista,
	 * 		 y subida por el mismo 'uploader', entonces lanza una excepción 'CancionYaExiste'
	 */
	public void anyadirCancion(cancionVO cancion, Connection connection)
			throws CancionYaExiste, SQLException {
		System.out.println("Insertando canción en la base de datos 3...");
		try {
			if (existeCancion(cancion, connection)) {
				throw new CancionYaExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " ya existe.");
			}
			else {
				String queryString = "INSERT INTO Cancion(titulo, nombreArtista, nombreAlbum, genero, uploader) "
						+ "VALUES (?,?,?,?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, cancion.verTitulo());
	    			preparedStatement.setString(2, cancion.verNombreArtista());
	    			preparedStatement.setString(3, cancion.verNombreAlbum());
	    			preparedStatement.setString(4, cancion.verGenero());
	    			preparedStatement.setString(5, cancion.verUploader());
	    			preparedStatement.executeUpdate();
	        	}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha eliminado la canción 'cancion' de la tabla Cancion de la BD.
	 * 		 Si no existía ninguna cancion con el mismo nombre, perteneciente al mismo álbum/artista,
	 * 		 y subida por el mismo 'uploader', entonces lanza una excepción 'CancionNoExiste'
	 */
	public void quitarCancion(cancionVO cancion, Connection connection)
			throws CancionNoExiste, SQLException, IOException {
		try {
			if (!existeCancion(cancion, connection)) {
				throw new CancionNoExiste("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " no existe.");
			}
			else {
				String queryString1 = "SELECT ruta "
								   + " WHERE titulo = '" + cancion.verTitulo()
								   + "' AND nombreArtista = '" + cancion.verNombreArtista()
								   + "' AND nombreAlbum = '" + cancion.verNombreAlbum()
								   + "' AND uploader = '" + cancion.verUploader()
								   + "';";
				
				String queryString2 = "DELETE FROM Cancion"
						+ " WHERE titulo = '" + cancion.verTitulo()
						+ "' AND nombreArtista = '" + cancion.verNombreArtista()
						+ "' AND nombreAlbum = '" + cancion.verNombreAlbum()
						+ "' AND uploader = '" + cancion.verUploader()
						+ "';";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString1);
				ResultSet resultado = preparedStatement.executeQuery();
				
				String ruta = resultado.getString(1);
				
				preparedStatement = 
		                connection.prepareStatement(queryString2);
				preparedStatement.executeUpdate();
				
				Files.delete(new File(ruta).toPath());
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	
	/*
	 * Pre: ---
	 * Post: Devuelve verdad si y solo si existe en la tabla Cancion una canción igual
	 * 		 a 'cancion'.
	 */
	public boolean existeCancion(cancionVO cancion, Connection connection) throws SQLException {
		try {
			String comprobacion = "SELECT *"
					+ " FROM Cancion"
					+ " WHERE titulo = '" + cancion.verTitulo()
					+ "' AND nombreArtista = '" + cancion.verNombreArtista()
					+ "' AND nombreAlbum = '" + cancion.verNombreAlbum()
					+ "' AND uploader = '" + cancion.verUploader()
					+ "';";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
			ResultSet busquedaComp = preparedStatement.executeQuery();
	        return (busquedaComp.next());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existe una cancion en la BD con el título proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un json con una clave canciones, cuyo
	 * 		valor asociado será un array en el que cada componente es una
	 * 		canción
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public JSONObject buscarCancionPorTitulo(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "titulo = '" + c.verNombreArtista() + "' AND "
					 + "(uploader = '" + nombreUploader + "' OR "
					 + "uploader = 'Admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}
			
			// Generamos los objetos VO
			/*Vector<String> titulos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "titulo"));
			Vector<String> nombres =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreArtista"));
			Vector<String> albums =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreAlbum"));
			Vector<String> generos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "genero"));
			Vector<String> uploaders =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "uploader"));
			Vector<String> rutas =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "ruta"));*/
			Vector<cancionVO> canciones =new Vector<cancionVO>();
			
			/*while (busquedaComp.next()) {
				canciones.add(new cancionVO(titulos.elementAt(i),
						nombres.elementAt(i), albums.elementAt(i),
						generos.elementAt(i), uploaders.elementAt(i),
						rutas.elementAt(i)));
			}*/
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			while (busquedaComp.next()) {
				JSONObject aux = new JSONObject();
				aux.put("tituloCancion", busquedaComp.getString(1));
				aux.put("nombreArtista", busquedaComp.getString(2));
				aux.put("nombreAlbum", busquedaComp.getString(3));
				aux.put("genero", busquedaComp.getString(4));
				aux.put("uploader", busquedaComp.getString(5));
				aux.put("ruta", busquedaComp.getString(6));
				array.add(aux);
			}
			obj.put("canciones", array);
			return obj;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existe una cancion en la BD con el artista proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un vector de cancionesVO que contiene todas las canciones
	 * 		obtenidas en la consulta.
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public Vector<cancionVO> buscarCancionPorArtista(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "nombreArtista = '" + c.verNombreArtista() + "' AND "
					 + "(uploader = '" + nombreUploader + "' OR"
					 + "uploader = 'admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}
			
			// Generamos los objetos VO
			/*Vector<String> titulos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "titulo"));
			Vector<String> nombres =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreArtista"));
			Vector<String> albums =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreAlbum"));
			Vector<String> generos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "genero"));
			Vector<String> uploaders =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "uploader"));
			Vector<String> rutas =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "ruta"));*/
			Vector<cancionVO> canciones =new Vector<cancionVO>(); 
			
			while (busquedaComp.next()) {
				canciones.add(new cancionVO(busquedaComp.getString(1), busquedaComp.getString(2),
											busquedaComp.getString(3), busquedaComp.getString(4),
											busquedaComp.getString(5), busquedaComp.getString(6)));
			}
			return canciones;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post Busca en la BD si existe una cancion en la BD con el album proporcionado,
	 * 		bien sea subida por el administrador o por el usuario.
	 * 		Además, devuelve un vector de cancionesVO que contiene todas las canciones
	 * 		obtenidas en la consulta.
	 * 		De no existir, lanza una excepción CancionNoExiste
	 */
	public Vector<cancionVO> buscarCancionPorAlbum(cancionVO c,
			String nombreUploader, Connection cc)
			throws SQLException, CancionNoExiste {
		try {
			String s = "SELECT * FROM Cancion WHERE "
					 + "nombreAlbum = '" + c.verNombreAlbum() + "' AND "
					 + "(uploader = '" + nombreUploader + "' OR"
					 + "uploader = 'admin');";
			PreparedStatement preparedStatement = cc.prepareStatement(s);
			ResultSet busquedaComp = preparedStatement.executeQuery();
			
			// Comprobamos que exista la canción
			if(!busquedaComp.first()) {
				throw new CancionNoExiste("La cancion buscada no existe en la BD");
			}
			
			// Generamos los objetos VO
			/*Vector<String> titulos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "titulo"));
			Vector<String> nombres =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreArtista"));
			Vector<String> albums =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "nombreAlbum"));
			Vector<String> generos =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "genero"));
			Vector<String> uploaders =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "uploader"));
			Vector<String> rutas =
					new Vector<String>(FuncionesAuxiliares.
							obtenerValorColumna(busquedaComp, "ruta"));*/
			Vector<cancionVO> canciones =new Vector<cancionVO>(); 
			
			while (busquedaComp.next()) {
				canciones.add(new cancionVO(busquedaComp.getString(1), busquedaComp.getString(2),
											busquedaComp.getString(3), busquedaComp.getString(4),
											busquedaComp.getString(5), busquedaComp.getString(6)));
			}
			return canciones;
		}
		catch(Exception e) {
			throw e;
		}
	}
}
