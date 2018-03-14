public class formarVO {
	private String tituloCancion;
	private String nombreArtista;
	private String nombreAlbum;
	private String nombreLista;
	private String nombreUsuario;

	public formarVO(String tituloCancion, String nombreArtista, String nombreAlbum, String nombreLista, String nombreUsuario){
		this.tituloCancion = tituloCancion;
		this.nombreArtista = nombreArtista;
		this.nombreAlbum = nombreAlbum;
		this.nombreLista = nombreLista;
		this.nombreUsuario = nombreUsuario;
	}

	public String verTituloCancion(){
		return tituloCancion;
	}

	public String verNombreArtista(){
		return nombreArtista;
	}

	public String verNombreAlbum(){
		return nombreAlbum;
	}

	public String verNombreLista(){
		return nombreLista;
	}

	public String verNombreUsuario(){
		return nombreUsuario;
	}

	public void modificarTituloCancion(String nuevoTitulo){
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

	public void modificarNombreLista(String nuevaLista){
		if (nuevaLista != NULL){
			nombreLista = nuevaLista;
		}
	}

	public void modificarNombreUsuario(String nuevoUsuario){
		if (nuevoUsuario != NULL){
			nombreUsuario = nuevoUsuario;
		}
	}
}