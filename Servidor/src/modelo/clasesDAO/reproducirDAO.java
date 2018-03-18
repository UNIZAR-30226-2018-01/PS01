package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.clasesVO.reproducirVO;
import modelo.excepcion.ErrorFavorita;

public class reproducirDAO {
	public void anyadirReproduccion (reproducirVO repro, Connection connection)
			throws ErrorFavorita, SQLException {
		try {
			String queryString = "INSERT INTO Reproducir(nombreUsuario, titulo, nombreAlbum, nombreArtista) "
					+ "VALUES (?,?,?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
			preparedStatement.setString(1, repro.verNombreUsuario());
			preparedStatement.setString(2, repro.verTituloCancion());
			preparedStatement.setString(3, repro.verNombreAlbum());
			preparedStatement.setString(4, repro.verNombreArtista());
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp != 0) {
	        		throw new ErrorFavorita("Error al marcar la canción " + repro.verTituloCancion() + " como favorita"
	        				+ "para el usuario" + repro.verNombreUsuario() + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
}
