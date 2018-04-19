package pruebas.servlet;

public class Probar {
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "prueba";
	public static String SESION;
	
	public static void main(String[] args) {
		try {
			//int i = 0;
			//while (i<10) {
				RegistrarUsuario.execute(USER, PASS);
				SESION = IniciarSesion.execute(USER, PASS);
				BuscarCancionTitulo.execute(USER, SESION, "Deltoya");
				BuscarCancionArtista.execute(USER, SESION, "Extremoduro");
				BuscarCancionAlbum.execute(USER, SESION, "Iros todos a tomar por culo");
				CrearListaDeReproduccion.execute(USER, SESION, "Fiesta");
				RegistrarUsuario.execute("david", "feo");
				SeguirUsuario.execute(USER, SESION, "david");
				DejarDeSeguirUsuario.execute(USER, SESION, "david");
				BorrarListaDeReproduccion.execute(USER, SESION, "Fiesta");
				MostrarListasReproduccion.execute(USER, SESION);
				CerrarSesion.execute(USER, SESION);
			//}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
