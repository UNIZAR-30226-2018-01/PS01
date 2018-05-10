package modelo.clasesVO;

import java.util.Date;

public class compartirVO {
	private String ruta;
	private String usuarioOrigen;
	private String usuarioDestino;
	private Date fecha;
	
	// Constructores
	public compartirVO(String ruta, String usuarioOrigen,
			String usuarioDestino) {
		super();
		this.ruta = ruta;
		this.usuarioOrigen = usuarioOrigen;
		this.usuarioDestino = usuarioDestino;
	}
	
	public compartirVO(String ruta, String usuarioOrigen, String usuarioDestino,
			Date fecha) {
		super();
		this.ruta = ruta;
		this.usuarioOrigen = usuarioOrigen;
		this.usuarioDestino = usuarioDestino;
		this.fecha = fecha;
	}

	
	// Getters y setters
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
