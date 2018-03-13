public class cancionVO {
	private String tituloCancion;
	private String nombreArtista = "Desconocido";
	private String nombreAlbum = "Desconocido";
	private String genero = "Desconocido";
	private String uploader;

	public cancionVO(String tituloCancion, String nombreArtista, String nombreAlbum, String genero, String uploader){
		this.tituloCancion = tituloCancion;
		if (nombreArtista != NULL){
			this.nombreArtista = nombreArtista;
		}
		if (nombreAlbum != NULL){∫
			this.nombreAlbum = nombreAlbum;
		}
		this.genero = genero;
		this.uploader = uploader;
	}

	public String verTitulo(){
		return tituloCancion;
	}

	public String verNombreArtista(){
		return nombreArtista;
	}

	public String verNombreAlbum(){
		return nombreAlbum;
	}

	public String verGenero(){
		return genero;
	}

	public String verUploader(){
		return uploader;
	}

	public void modificarTitulo(String nuevoTitulo){
		if (nuevoTitulo != NULL){
			tituloCancion = nuevoTitulo;
		}
	}

	public void modificarNombreArtista(String nuevoArtista){
		if (nuevoArtista != NULL){
			nombreArtista = nuevoArtista;
		}
	}

	public void modificarNombreAlbum(String nuevoAlbum){
		if (nuevoAlbum != NULL){
			nombreAlbum = nuevoAlbum;
		}
	}

	public void modificarUploader(String nuevoUploader){
		if (nuevoUploader != NULL){
			uploader = nuevoUploader;
		}
	}
}