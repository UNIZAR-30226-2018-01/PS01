package modelo.clasesVO;

public class reproducirVO {
	private String nombreUsuario;
	private String tituloCancion;
	private String nombreAlbum;
	private String nombreArtista;

	public reproducirVO(String nombreUsuario, String tituloCancion, String nombreAlbum, String nombreArtista){
		this.nombreUsuario = nombreUsuario;
		this.tituloCancion = tituloCancion;
		this.nombreAlbum = nombreAlbum;
		this.nombreArtista = nombreArtista;
	}

	public String verNombreUsuario(){
		return nombreUsuario;
	}

	public String verTituloCancion(){
		return tituloCancion;
	}

	public String verNombreAlbum(){
		return nombreAlbum;
	}

	public String verNombreArtista(){
		return nombreArtista;
	}

	public void modificarNombreUsuario(String nuevoUsuario){
		if (nuevoUsuario != null){
			nombreUsuario = nuevoUsuario;
		}
	}

	public void modificarTituloCancion(String nuevaCancion){
		if (nuevaCancion != null){
			tituloCancion = nuevaCancion;
		}
	}

	public void modificarNombreAlbum(String nuevoAlbum){
		if (nuevoAlbum != null){	
			nombreAlbum = nuevoAlbum;
		}
	}

	public void modificarNombreArtista(String nuevoArtista){
		if (nuevoArtista != null){
			nombreArtista = nuevoArtista;
		}
	}
}