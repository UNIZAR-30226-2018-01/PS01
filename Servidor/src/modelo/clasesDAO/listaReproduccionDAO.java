package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.clasesVO.listaReproduccionVO;

public class listaReproduccionDAO {
	public void anyadirLista(listaReproduccionVO lista, Connection connection) throws Exception {
		try {
			String comprobacion = "SELECT * FROM ListaReproduccion"
					+ " WHERE ListaReproduccion.nombre = " + lista.obtenerNombreLista()
					+ " AND ListaReproduccion.nombreUsuario = " + lista.obtenerNombreUsuario() + ";";
			String queryString = "INSERT INTO ListaReproduccion(nombre, nombreUsuario) "
					+ "VALUES (?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
			
			int busquedaComp = preparedStatement.executeUpdate();
	        	if (busquedaComp != 0) {
	        		throw new Exception("La lista de reproducción " + lista.obtenerNombreLista() + " ya existe.");
	        	}
	        	else {
	        		preparedStatement = connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, lista.obtenerNombreLista());
	    			preparedStatement.setString(2, lista.obtenerNombreUsuario());
	    			
	    			preparedStatement.executeUpdate();
	        	}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void quitarLista(listaReproduccionVO lista, Connection connection) throws Exception {
		try {
			if (!existeLista(lista, connection)) {
				throw new Exception("La lista " + lista.obtenerNombreLista() + "no existe.");
			}
			else {
				String queryString = "DELETE FROM TABLE ListaReproduccion"
						+ "WHERE nombre = " + lista.obtenerNombreLista()
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
			String comprobacion = "SELECT nombre "
					+ "FROM Usuario"
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
