package modelo.clasesVO;

public class cancionVO {
	private String tituloCancion;
	private String nombreArtista = "Desconocido";
	private String nombreAlbum = "Desconocido";
	private String genero = "Desconocido";
	private String uploader;
	private String ruta;

	public cancionVO(String tituloCancion, String nombreArtista,
			String nombreAlbum, String genero, String uploader, String ruta){
		this.tituloCancion = tituloCancion;
		if (nombreArtista != null){
			this.nombreArtista = nombreArtista;
		}
		if (nombreAlbum != null){
			this.nombreAlbum = nombreAlbum;
		}
		this.genero = genero;
		this.uploader = uploader;
		this.ruta = ruta;
	}
	
	public cancionVO(String tituloCancion, String nombreArtista,
			String nombreAlbum, String genero, String uploader){
		this.tituloCancion = tituloCancion;
		if (nombreArtista != null){
			this.nombreArtista = nombreArtista;
		}
		if (nombreAlbum != null){
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
	
	public String verRuta() {
		return ruta;
	}

	public void modificarTitulo(String nuevoTitulo){
		if (nuevoTitulo != null){
			tituloCancion = nuevoTitulo;
		}
	}

	public void modificarNombreArtista(String nuevoArtista){
		if (nuevoArtista != null){
			nombreArtista = nuevoArtista;
		}
	}

	public void modificarNombreAlbum(String nuevoAlbum){
		if (nuevoAlbum != null){
			nombreAlbum = nuevoAlbum;
		}
	}

	public void modificarUploader(String nuevoUploader){
		if (nuevoUploader != null){
			uploader = nuevoUploader;
		}
	}
	
	public void modificarRuta(String nuevaRuta) {
		if (nuevaRuta != null) {
			ruta = nuevaRuta;
		}
	}
}