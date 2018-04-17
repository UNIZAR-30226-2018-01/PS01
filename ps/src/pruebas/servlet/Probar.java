package pruebas.servlet;

public class Probar {
	public static final String URL_SERVER = "http://mewat1718.ddns.net:8080/ps/";
	public static final String USER = "mewat";
	public static final String PASS = "prueba";
	public static String SESION;
	
	public static void main(String[] args) {
		try {
			RegistrarUsuario.execute(USER, PASS);
			SESION = IniciarSesion.execute(USER, PASS);
			BuscarCancionTitulo.execute(USER, SESION, "Deltoya");
			BuscarCancionArtista.execute(USER, SESION, "Extremoduro");
			BuscarCancionAlbum.execute(USER, SESION, "Iros todos a tomar por culo");
			CrearListaDeReproduccion.execute(USER, SESION, "Fiesta");
			CerrarSesion.execute(USER, SESION);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
