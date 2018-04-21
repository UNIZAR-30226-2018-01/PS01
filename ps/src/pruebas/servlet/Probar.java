package pruebas.servlet;

public class Probar {
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "prueba";
	public static String SESION;
	public static String SESION2;
	
	public static void main(String[] args) {
		try {
			//int i = 0;
			//while (i<10) {
				RegistrarUsuario.execute(USER, PASS);
				SESION = IniciarSesion.execute(USER, PASS);
				RegistrarUsuario.execute("david", "feo");
				SESION2 = IniciarSesion.execute("david", "feo");
				
				BuscarCancionTitulo.execute(USER, SESION, "Deltoya");
				BuscarCancionArtista.execute(USER, SESION, "Extremoduro");
				BuscarCancionAlbum.execute(USER, SESION, "Iros todos a tomar por culo");
				CrearListaDeReproduccion.execute(USER, SESION, "Fiesta");
				SeguirUsuario.execute("david", SESION2, USER);
				SeguirUsuario.execute(USER, SESION, "david");
				//DejarDeSeguirUsuario.execute(USER, SESION, "david");
				//BorrarListaDeReproduccion.execute(USER, SESION, "Fiesta");
				MostrarListasReproduccion.execute(USER, SESION);
				VerLista.execute(USER, SESION, "Fiesta", USER);
				VerSeguidores.execute(USER, SESION);
				VerSeguidos.execute(USER, SESION);
				AnyadirCancionALista.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo", "Fiesta");
				QuitarCancionDeLista.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo", "Fiesta");
				//BorrarCancion.execute(USER, SESION, "Malibu (Basslouder Remix)", "VA", "Future Trance 81 - CD 2");
				
				BuscarUsuarios.execute(USER, SESION, "da");
				ReproducirCancion.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo");
				CerrarSesion.execute("david", SESION2);
				CerrarSesion.execute(USER, SESION);
			//}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
