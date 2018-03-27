package modelo.clasesVO;

public class gustarVO {
	private String nombreUsuario;
	private String tituloCancion;
	private String nombreAlbum;
	private String nombreArtista;
	private String nombreUploader;

	public gustarVO(String nombreUsuario, String tituloCancion, String nombreAlbum, String nombreArtista, String nombreUploader){
		this.nombreUsuario = nombreUsuario;
		this.tituloCancion = tituloCancion;
		this.nombreAlbum = nombreAlbum;
		this.nombreArtista = nombreArtista;
		this.nombreUploader = nombreUploader;
	}

	public String verNombreUsuario(){
		return nombreUsuario;
	}

	public String verTitulo(){
		return tituloCancion;
	}

	public String verNombreAlbum(){
		return nombreAlbum;
	}

	public String verNombreArtista(){
		return nombreArtista;
	}
	
	public String verNombreUploader() {
		return nombreUploader;
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
	
	public void modificarNombreUploader(String nuevoUploader) {
		if (nuevoUploader != null) {
			nombreUploader = nuevoUploader;
		}
	}
}