package pruebas.servlet;

public class Probar {
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "Prueba1";
	public static String SESION;
	public static String SESION2;
	
	public static void main(String[] args) {
		try {
			RegistrarUsuario.execute(USER, PASS);
			SESION = IniciarSesion.execute(USER, PASS);
			RegistrarUsuario.execute("albertro", "Albertro1");
			SESION2 = IniciarSesion.execute("albertro", "Albertro1");
			
			BuscarCancionTitulo.execute(USER, SESION, "Deltoya");
			BuscarCancionArtista.execute(USER, SESION, "Extremoduro");
			BuscarCancionAlbum.execute(USER, SESION, "Iros todos a tomar por culo");
			CrearListaDeReproduccion.execute(USER, SESION, "Fiesta");
			SeguirUsuario.execute("albertro", SESION2, USER);
			SeguirUsuario.execute(USER, SESION, "albertro");
			//DejarDeSeguirUsuario.execute(USER, SESION, "david");
			//BorrarListaDeReproduccion.execute(USER, SESION, "Fiesta");
			MostrarListasReproduccion.execute(USER, SESION);
			VerLista.execute(USER, SESION, "Fiesta", USER);
			VerSeguidores.execute(USER, SESION);
			VerSeguidos.execute(USER, SESION);
			AnyadirCancionALista.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo", "Fiesta");
			QuitarCancionDeLista.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo", "Fiesta");
			//BorrarCancion.execute(USER, SESION, "Malibu (Basslouder Remix)", "VA", "Future Trance 81 - CD 2");
			
			BuscarUsuarios.execute(USER, SESION, "alb");
			//ReproducirCancion.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo");
			CambiarContrasenya.execute(USER, SESION, "Prueba1", "Prueba2", "Prueba2");
			//EliminarCuenta.execute(USER, SESION);
			EliminarCuenta.execute("albertro", SESION2);
			//CerrarSesion.execute("albertro", SESION2);
			CerrarSesion.execute(USER, SESION);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
