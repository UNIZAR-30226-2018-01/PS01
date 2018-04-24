package pruebas.servlet;

public class Probar {
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "Prueba1";
	public static final String USER2 = "albertro";
	public static final String PASS2 = "Albertro1";
	public static final String LISTA = "Fiesta";
	public static final String CANCION = "Deltoya";
	public static final String ARTISTA = "Extremoduro";
	public static final String ALBUM = "Iros todos a tomar por culo";
	public static final String GENERO = "Rock";
	public static String SESION;
	public static String SESION2;
	
	public static void main(String[] args) {
		try {
			RegistrarUsuario.execute(USER, PASS);
			SESION = IniciarSesion.execute(USER, PASS);
			RegistrarUsuario.execute(USER2, PASS2);
			SESION2 = IniciarSesion.execute(USER2, PASS2);
			BuscarCancionTitulo.execute(USER, SESION, CANCION);
			BuscarCancionArtista.execute(USER, SESION, ARTISTA);
			BuscarCancionAlbum.execute(USER, SESION, ALBUM);
			BuscarCancionGenero.execute(USER, SESION, GENERO);
			CrearListaDeReproduccion.execute(USER, SESION, LISTA);
			AnyadirCancionALista.execute(USER, SESION, CANCION, ARTISTA, ALBUM, LISTA);
			MostrarListasReproduccion.execute(USER, SESION);
			VerLista.execute(USER, SESION, LISTA, USER);
			QuitarCancionDeLista.execute(USER, SESION, CANCION, ARTISTA, ALBUM, LISTA);
			BorrarListaDeReproduccion.execute(USER, SESION, "Fiesta");
			BuscarUsuarios.execute(USER, SESION, "alb");
			SeguirUsuario.execute(USER2, SESION2, USER);
			SeguirUsuario.execute(USER, SESION, USER2);
			VerSeguidores.execute(USER, SESION);
			VerSeguidos.execute(USER, SESION);
			DejarDeSeguirUsuario.execute(USER, SESION, USER2);
			//BorrarCancion.execute(USER, SESION, "Malibu (Basslouder Remix)", "VA", "Future Trance 81 - CD 2");
			//ReproducirCancion.execute(USER, SESION, "Deltoya", "Extremoduro", "Iros todos a tomar por culo");
			CerrarSesion.execute(USER, SESION);
			CambiarContrasenya.execute(USER2, SESION2, PASS2, "Albertro2", "Albertro2");
			EliminarCuenta.execute(USER2, SESION2, "Albertro2");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
