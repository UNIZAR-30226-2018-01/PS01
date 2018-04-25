package modelo.clasesVO;

public class reproduccionVO {
	private String nombreUsuario;
	private String tituloCancion;
	private String nombreAlbum;
	private String nombreArtista;
	private String uploader;
	private String fecha;

	public reproduccionVO(String nombreUsuario, String tituloCancion,
			String nombreAlbum, String nombreArtista, String uploader, 
			String fecha){
		this.nombreUsuario = nombreUsuario;
		this.tituloCancion = tituloCancion;
		this.nombreAlbum = nombreAlbum;
		this.nombreArtista = nombreArtista;
		this.uploader = uploader;
		this.fecha = fecha;
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
	
	public String verUploader() {
		return uploader;
	}
	
	public String verFecha() {
		return fecha;
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
	
	public void modificarUploader(String uploader) {
		this.uploader = uploader;
	}
}