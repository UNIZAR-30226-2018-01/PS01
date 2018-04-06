package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorDeConexionesBD {	
	private static final String URL_BD = "jdbc:mysql://localhost:3306/software";
	private static final String user = "mewat";
	private static final String pass = "mewat";
	static {			
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}	
	}

	private GestorDeConexionesBD(){}
	
	public final static Connection getConnection()
		throws SQLException {
<<<<<<< HEAD
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba?" +
                    "user=alberto&password=rass");
=======
				return DriverManager.getConnection(URL_BD, user, pass);
>>>>>>> b5b59b3b6ddc993287c1ed63a2c957ae1392cfb8
	}
}
