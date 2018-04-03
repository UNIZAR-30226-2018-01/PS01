package modelo.clasesVO;

public class seguirVO {
	private String nombreSeguidor;
	private String nombreSeguido;
	
	public seguirVO(String nombreSeguidor, String nombreSeguido) {
		this.nombreSeguidor = nombreSeguidor;
		this.nombreSeguido = nombreSeguido;
	}
	
	public String verNombreSeguidor() {
		return nombreSeguidor;
	}
	
	public String verNombreSeguido() {
		return nombreSeguido;
	}
	
	public void modificarNombreSeguidor(String nuevoSeguidor) {
		nombreSeguidor = nuevoSeguidor;
	}
	
	public void modificarNombreSeguido(String nuevoSeguido) {
		nombreSeguido = nuevoSeguido;
	}
}
