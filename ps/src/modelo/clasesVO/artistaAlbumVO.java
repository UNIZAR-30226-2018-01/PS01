package modelo.clasesVO;

public class artistaAlbumVO {
	private String nombreArtista = "Desconocido";
	private String nombreAlbum = "Desconocido";
	private String anyooAlbum = "XXXX";

	public artistaAlbumVO(String nombreArtista, String nombreAlbum, String anyooAlbum){
		if (nombreArtista != null){
			this.nombreArtista = nombreArtista;
		}
		if (nombreAlbum != null){
			this.nombreAlbum = nombreAlbum;
		}
		if (anyooAlbum != null){
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
		if (nuevoArtista != null){
			nombreArtista = nuevoArtista;
		}
	}

	public void modificarNombreAlbum(String nuevoAlbum){
		if (nuevoAlbum != null){
			nombreAlbum = nuevoAlbum;
		}
	}

	public void modificarAnyooAlbum(String nuevoAnyoo){
		if (anyooAlbum != null){
			anyooAlbum = nuevoAnyoo;
		}
	}
}