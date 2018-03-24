package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.gustarVO;
import modelo.excepcion.ErrorAnyadirMegusta;
import modelo.excepcion.ErrorQuitarMegusta;

public class gustarDAO {
	/*
	 * Pre: ---
	 * Post: Ha registrado que un usuario ha marcado como favorita una canción.
	 * 		 Si se produce algún error entonces lanza una excepción 'ErrorAnyadirMegusta'.
	 */
	public void megusta(gustarVO gustar, Connection connection)
			throws ErrorAnyadirMegusta, SQLException {
		try {
			String queryString = "INSERT INTO Gustar(nombreUsuario, titulo, nombreAlbum, nombreArtista, uploader) "
					+ "VALUES (?,?,?,?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			preparedStatement.setString(1, gustar.verNombreUsuario());
			preparedStatement.setString(2, gustar.verTitulo());
			preparedStatement.setString(3, gustar.verNombreAlbum());
			preparedStatement.setString(4, gustar.verNombreArtista());
			preparedStatement.setString(5, gustar.verNombreUploader());
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp == 0) {
	        		throw new ErrorAnyadirMegusta("Error al marcar la canción " + gustar.verTitulo() + " como favorita"
	        				+ " para el usuario " + gustar.verNombreUsuario() + ".");
	        }
		}
		catch (SQLException e) {
			throw new ErrorAnyadirMegusta("Error al marcar la canción " + gustar.verTitulo() + " como favorita"
    				+ " para el usuario " + gustar.verNombreUsuario() + ".");
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha registrado que una canción ya no es favorita de un usuario.
	 * 		 Si se produce algún error entonces lanza una excepción 'ErrorQuitarMegusta'.
	 */
	public void yanomegusta(gustarVO gustar, Connection connection)
			throws ErrorQuitarMegusta, SQLException {
		try {
			String queryString = "DELETE FROM Gustar"
					+ " WHERE Gustar.nombreUsuario = '" + gustar.verNombreUsuario()
					+ "' AND Gustar.titulo = '" + gustar.verTitulo()
					+ "' AND Gustar.nombreAlbum = '" + gustar.verNombreAlbum()
					+ "' AND Gustar.nombreArtista = '" + gustar.verNombreArtista()
					+ "' AND Gustar.uploader = '" + gustar.verNombreUploader()
					+ "';";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp == 0) {
	        		throw new ErrorQuitarMegusta("Error al quitar la canción " + gustar.verTitulo() + " como favorita"
	        				+ " para el usuario " + gustar.verNombreUsuario() + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
}
