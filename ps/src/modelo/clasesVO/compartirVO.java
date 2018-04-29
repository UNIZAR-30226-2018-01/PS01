package modelo.clasesVO;

public class compartirVO {
	private String usuarioOrigen;
	private String tituloCancion;
	private String nombreAlbum;
	private String nombreArtista;
	private String genero;
	private String usuarioDestino;
	private String fecha;
	
	public compartirVO(String usuarioOrigen, String tituloCancion, String nombreAlbum,
					   String nombreArtista, String genero, String usuarioDestino, String fecha) {
		this.usuarioOrigen = usuarioOrigen;
		this.tituloCancion = tituloCancion;
		this.nombreAlbum = nombreAlbum;
		this.nombreArtista = nombreArtista;
		this.genero = genero;
		this.usuarioDestino = usuarioDestino;
		this.fecha = fecha;
	}
	
	public String verUsuarioOrigen () {
		return usuarioOrigen;
	}
	
	public String verTituloCancion () {
		return tituloCancion;
	}
	
	public String verNombreAlbum () {
		return nombreAlbum;
	}
	
	public String verNombreArtista () {
		return nombreArtista;
	}
	
	public String verGenero () {
		return genero;
	}
	
	public String verUsuarioDestino () {
		return usuarioDestino;
	}
	
	public String verFecha() {
		return fecha;
	}
}
