package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.clasesVO.listaReproduccionVO;
import modelo.excepcion.ListaNoExiste;
import modelo.excepcion.ListaYaExiste;
import modelo.excepcion.NoHayListas;

public class listaReproduccionDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado la lista 'lista' en la tabla ListaReproduccion de la BD.
	 * 		 Si ya existía una lista con el mismo nombre y además creada por el
	 * 		 mismo usuario, entonces y solo entonces lanza una excepción 'ListaYaExiste'.
	 */
	public void anyadirLista(listaReproduccionVO lista, Connection connection)
			throws ListaYaExiste, SQLException {
		try {
			if (existeLista(lista, connection)) {
				throw new ListaYaExiste("La lista de reproducción " + lista.obtenerNombreLista()
						+ " del usuario " + lista.obtenerNombreUsuario() + " ya existe.");
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
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha eliminado la lista 'lista' de la tabla ListaReproduccion de la BD.
	 * 		 Si no existía ninguna lista con el mismo nombre y además creada por el
	 * 		 mismo usuario, entonces y solo entonces lanza una excepción 'ListaNoExiste'.
	 */
	public void quitarLista(listaReproduccionVO lista, Connection connection)
			throws ListaNoExiste, SQLException {
		try {
			if (!existeLista(lista, connection)) {
				throw new ListaNoExiste("La lista " + lista.obtenerNombreLista() + " no existe.");
			}
			else {
				String queryString = "DELETE FROM ListaReproduccion"
						+ " WHERE nombre = ?"
						+ " AND nombreUsuario = ?;";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				preparedStatement.setString(1, lista.obtenerNombreLista());
				preparedStatement.setString(2, lista.obtenerNombreUsuario());
				
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si un usuario nunca antes ha creado una lista de reproducción
	 * 		 con el mismo nombre.
	 */
	public boolean existeLista(listaReproduccionVO lista, Connection connection) throws SQLException {
		try {
			String comprobacion = "SELECT *"
					+ " FROM ListaReproduccion"
					+ " WHERE nombre = ?"
					+ " AND nombreUsuario = ?;";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
			preparedStatement.setString(1, lista.obtenerNombreLista());
			preparedStatement.setString(2, lista.obtenerNombreUsuario());
	            
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
	 * Post: Ha devuelto un Vector con todas las listas que un usuario ha creado
	 * 		 y las de los usuarios a los que sigue
	 */
	public JSONObject devolverListas(String nombreUsuario, Connection c)
			throws SQLException, NoHayListas {
		try {
			String s = "SELECT * "
					 + "FROM ListaReproduccion "
					 + "WHERE nombreUsuario = ?;";
			PreparedStatement p = c.prepareStatement(s);
			p.setString(1, nombreUsuario);
			ResultSet r = p.executeQuery();
			
			// Comprobamos que exista la canción
			if(!r.first()) {
				throw new NoHayListas("El usuario no tiene ninguna lista asociada");
			}
			JSONObject obj = new JSONObject();
			// Obtenemos y devolvemos el nombre de las listas
			return FuncionesAuxiliares.
							obtenerValorColumna(r, "nombre");
		}
		catch (Exception e) {
			throw e;
		}
	}
}
