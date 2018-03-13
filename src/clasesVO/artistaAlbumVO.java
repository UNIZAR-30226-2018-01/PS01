public class artistaAlbumVO {
	private String nombreArtista = "Desconocido";
	private String nombreAlbum = "Desconocido";
	private String anyooAlbum = "XXXX";

	public artistaAlbumVO(String nombreArtista, String nombreAlbum, String anyooAlbum){
		if (nombreArtista != NULL){
			this.nombreArtista = nombreArtista;
		}
		if (nombreAlbum != NULL){∫
			this.nombreAlbum = nombreAlbum;
		}
		if (anyooAlbum != NULL){
			this.anyooAlbum = anyooAlbum;
		}
	}

	public String verNombreArtista(){
		return nombreArtista;
	}

	public String verNombreAlbum(){
		return nombreAlbum;
	}

	public String verAnyooAlbum(){
		return anyooAlbum;
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

	public void modificarAnyooAlbum(String nuevoAnyoo){
		if (anyooAlbum != NULL){
			anyooAlbum = nuevoAnyoo;
		}
	}
}