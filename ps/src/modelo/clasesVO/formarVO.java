package modelo.clasesVO;

public class formarVO {
	// Parámetros
	private String ruta;
	private String nombreLista;
	private String nombreUsuario;
	
	// Contructor
	public formarVO(String ruta, String nombreLista, String nombreUsuario) {
		super();
		this.ruta = ruta;
		this.nombreLista = nombreLista;
		this.nombreUsuario = nombreUsuario;
	}

	// Getters y setters
	
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNombreLista() {
		return nombreLista;
	}

	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}