package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorDeConexionesBD {		
	static {			
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}	
	}

	private GestorDeConexionesBD(){
	}
	
	public final static Connection getConnection()
		throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba?" +
                    "user=alberto&password=rass");
	}
}
