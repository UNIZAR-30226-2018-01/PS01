public class gustarVO {
	private String nombreUsuario;
	private String tituloCancion;
	private String nombreAlbum;
	private String nombreArtista;

	public gustarVO(String nombreUsuario, String tituloCancion, String nombreAlbum, String nombreArtista){
		this.nombreUsuario = nombreUsuario;
		this.tituloCancion = tituloCancion;
		this.nombreAlbum = nombreAlbum;
		this.nombreArtista = nombreArtista;
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

	public void modificarNombreUsuario(String nuevoUsuario){
		if (nuevoUsuario != NULL){
			nombreUsuario = nuevoUsuario;
		}
	}

	public void modificarTituloCancion(String nuevaCancion){
		if (nuevaCancion != NULL){
			tituloCancion = nuevaCancion;
		}
	}

	public void modificarNombreAlbum(String nuevoAlbum){
		if (nuevoAlbum != NULL){
			nombreAlbum = nuevoAlbum;
		}
	}

	public void modificarNombreArtista(String nuevoArtista){
		if (nuevoArtista != NULL){
			nombreArtista = nuevoArtista;
		}
	}
}