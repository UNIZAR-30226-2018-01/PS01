package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.clasesVO.formarVO;

public class formarDAO {
	public void anyadirCancionALista(formarVO f, Connection connection) throws Exception {
		try {
			if (existeCancionEnLista(f, connection)) {
				throw new Exception("La canción " + f.verTituloCancion() + "ya existe"
						+ " en la lista" + f.verNombreLista() + ".");
			}
			else {
				String queryString = "INSERT INTO Formar(titulo, nombreArtista, nombreAlbum, nombreLista, nombreUsuario)"
						+ " VALUES(?,?,?,?,?);";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.setString(1, f.verTituloCancion());
				preparedStatement.setString(2, f.verNombreArtista());
				preparedStatement.setString(3, f.verNombreAlbum());
				preparedStatement.setString(4, f.verNombreLista());
				preparedStatement.setString(5, f.verNombreUsuario());
				
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void quitarCancionDeLista(formarVO f, Connection connection) throws Exception {
		try {
			if (!existeCancionEnLista(f, connection)) {
				throw new Exception("La canción " + f.verTituloCancion() + "no existe"
						+ " en la lista" + f.verNombreLista() + ".");
			}
			else {
				String queryString = "DELETE FROM Formar"
						+ " WHERE titulo = " + f.verTituloCancion()
						+ " AND nombreArtista = " + f.verNombreArtista()
						+ " AND nombreAlbum = " + f.verNombreAlbum()
						+ " AND nombreLista = " + f.verNombreLista()
						+ " AND nombreUsuario = " + f.verNombreUsuario()
						+ ";";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean existeCancionEnLista(formarVO f, Connection connection) throws Exception {
		try {
			String comprobacion = "SELECT *"
					+ " FROM Formar"
					+ " WHERE titulo = " + f.verTituloCancion()
					+ " AND nombreArtista = " + f.verNombreArtista()
					+ " AND nombreAlbum = " + f.verNombreAlbum()
					+ " AND nombreLista = " + f.verNombreLista()
					+ " AND nombreUsuario = " + f.verNombreUsuario()
					+ ";";
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
	        int busquedaComp = preparedStatement.executeUpdate();
	        return (busquedaComp != 0);			
		}
		catch (Exception e) {
			throw e;
		}
	}
}
