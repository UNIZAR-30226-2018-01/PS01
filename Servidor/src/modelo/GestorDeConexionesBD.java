package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorDeConexionesBD {
	
	private final static String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
	private final static String DRIVER_URL = "jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious";
	private final static String USER = "a697589";
	private final static String PASSWORD = "Catbir27";
		
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
			return DriverManager.getConnection("jdbc:mysql://localhost/prueba?" +
                    "user=alberto&password=rass");
	}
}
