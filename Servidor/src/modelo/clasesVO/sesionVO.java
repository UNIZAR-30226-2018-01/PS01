package modelo.clasesVO;

public class sesionVO {
	private String hashSesion;
	private String nombreUsuario;

	public sesionVO(String hashSesion, String nombreUsuario){
		this.hashSesion = hashSesion;
		this.nombreUsuario = nombreUsuario;
	}

	public String verHashSesion(){
		return hashSesion;
	}

	public String verNombreUsuario(){
		return nombreUsuario;
	}

	public void modificarNombreUsuario(String nuevoUsuario){
		if (nuevoUsuario != null){
			nombreUsuario = nuevoUsuario;
		}
	}
}