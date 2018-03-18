package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.gustarVO;
import modelo.excepcion.ErrorAnyadirMegusta;
import modelo.excepcion.ErrorQuitarMegusta;

public class gustarDAO {
	public void megusta(gustarVO gustar, Connection connection)
			throws ErrorAnyadirMegusta, SQLException {
		try {
			String queryString = "INSERT INTO Gustar(nombreUsuario, titulo, nombreAlbum, nombreArtista) "
					+ "VALUES (?,?,?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			preparedStatement.setString(1, gustar.verNombreUsuario());
			preparedStatement.setString(2, gustar.verTitulo());
			preparedStatement.setString(3, gustar.verNombreAlbum());
			preparedStatement.setString(4, gustar.verNombreArtista());
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp != 0) {
	        		throw new ErrorAnyadirMegusta("Error al marcar la canción " + gustar.verTitulo() + " como favorita"
	        				+ "para el usuario" + gustar.verNombreUsuario() + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void yanomegusta(gustarVO gustar, Connection connection)
			throws Exception, SQLException {
		try {
			String queryString = "DELETE FROM TABLE Gustar"
					+ " WHERE Gustar.nombreUsuario = " + gustar.verNombreUsuario()
					+ " AND Gustar.titulo = " + gustar.verTitulo()
					+ " AND Gustar.nombreAlbum = " + gustar.verNombreAlbum()
					+ " AND Gustar.nombreArtista = " + gustar.verNombreArtista();
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp != 0) {
	        		throw new ErrorQuitarMegusta("Error al quitar la canción " + gustar.verTitulo() + " como favorita"
	        				+ "para el usuario" + gustar.verNombreUsuario() + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
}
