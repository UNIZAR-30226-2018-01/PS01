package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.clasesVO.reproducirVO;
import modelo.excepcion.ExcepcionReproduccion;

public class reproducirDAO {
	/*
	 * Pre: ---
	 * Post: Ha marcado una determinada canción como reproducida para un usuario.
	 * 		 Si y solo si se produce algún problema al registrar la audición, entonces
	 * 		 lanza una excepción ExcepcionReproduccion.
	 */
	public void anyadirReproduccion (reproducirVO repro, Connection connection)
			throws ExcepcionReproduccion, SQLException {
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
	        		throw new ExcepcionReproduccion("Error al marcar la canción " + repro.verTituloCancion() + " como reproducida "
	        				+ "para el usuario" + repro.verNombreUsuario() + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
}
