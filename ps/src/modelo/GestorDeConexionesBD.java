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
				return DriverManager.getConnection(URL_BD, user, pass);
	}
}
