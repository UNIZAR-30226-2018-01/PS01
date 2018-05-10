package pruebas.servlet;

public class Probar {
	// INSERT INTO Cancion(titulo,nombreArtista,nombreAlbum,genero,uploader,ruta) VALUES('Deltoya','Extremoduro','Iros','Rock','Admin','Sin');
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "Prueba1";
	public static final String USER2 = "albertro";
	public static final String PASS2 = "Albertro1";
	public static final String LISTA = "Fiesta";
	public static final String CANCION = "Deltoya";
	public static final String ARTISTA = "Extremoduro";
	public static final String ALBUM = "Iros";
	public static final String GENERO = "Rock";
	public static final String RUTA = "Sin";
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
			AnyadirCancionALista.execute(USER, SESION, RUTA, LISTA);
			MostrarListasReproduccion.execute(USER, SESION);
			VerLista.execute(USER, SESION, LISTA, USER);
			QuitarCancionDeLista.execute(USER, SESION, RUTA, LISTA);
			BorrarListaDeReproduccion.execute(USER, SESION, LISTA);
			ObtenerGeneros.execute(USER, SESION);
			ObtenerArtistas.execute(USER, SESION);
			ObtenerAlbums.execute(USER, SESION);
			ObtenerAlbumsArtista.execute(USER, SESION, ARTISTA);
			BuscarArtista.execute(USER, SESION, ARTISTA);
			BuscarAlbum.execute(USER, SESION, ALBUM);
			TopSemanal.execute(USER, SESION);
			EscuchadasRecientemente.execute(USER, SESION);
			BuscarUsuarios.execute(USER, SESION, "alb");
			SeguirUsuario.execute(USER2, SESION2, USER);
			SeguirUsuario.execute(USER, SESION, USER2);
			VerSeguidores.execute(USER, SESION);
			VerSeguidos.execute(USER, SESION);
			DejarDeSeguirUsuario.execute(USER, SESION, USER2);
			CompartirCancion.execute(USER, SESION, RUTA, USER2);
			DevolverCompartidas.execute(USER2, SESION2);
			CerrarSesion.execute(USER, SESION);
			CambiarContrasenya.execute(USER2, SESION2, PASS2, "Albertro2", "Albertro2");
			CambiarNombre.execute(USER2, SESION2, "Paquito");
			EliminarCuenta.execute("Paquito", SESION2, "Albertro2");
			ReproducirCancion.execute(USER, SESION, CANCION, ARTISTA, ALBUM);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
