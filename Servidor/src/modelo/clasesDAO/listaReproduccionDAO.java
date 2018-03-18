package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.listaReproduccionVO;
import modelo.excepcion.ListaNoExiste;
import modelo.excepcion.ListaYaExiste;

public class listaReproduccionDAO {
	public void anyadirLista(listaReproduccionVO lista, Connection connection)
			throws ListaYaExiste, SQLException {
		try {
			if (existeLista(lista, connection)) {
				throw new ListaYaExiste("La lista de reproducción " + lista.obtenerNombreLista() + "del"
						+ " usuario " + lista.obtenerNombreUsuario() + " ya existe.");
			}
			else {
				String queryString = "INSERT INTO ListaReproduccion(nombre, nombreUsuario) "
						+ "VALUES (?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, lista.obtenerNombreLista());
	    			preparedStatement.setString(2, lista.obtenerNombreUsuario());
	    			
	    			preparedStatement.executeUpdate();
	        	}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void quitarLista(listaReproduccionVO lista, Connection connection)
			throws ListaNoExiste, SQLException {
		try {
			if (!existeLista(lista, connection)) {
				throw new ListaNoExiste("La lista " + lista.obtenerNombreLista() + "no existe.");
			}
			else {
				String queryString = "DELETE FROM ListaReproduccion"
						+ " WHERE nombre = " + lista.obtenerNombreLista()
						+ " AND nombreUsuario = " + lista.obtenerNombreUsuario()
						+ "; ";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean existeLista(listaReproduccionVO lista, Connection connection) throws Exception{
		try {
			String comprobacion = "SELECT *"
					+ " FROM ListaReproduccion"
					+ " WHERE nombre = " + lista.obtenerNombreLista()
					+ " AND nombreUsuario = " + lista.obtenerNombreUsuario() 
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
