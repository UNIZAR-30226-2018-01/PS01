package modelo.clasesVO;

public class usuarioVO {
	private String nombre;
	private String hashPass;

	public usuarioVO(String nombre, String hashPass){
		this.nombre = nombre;
		this.hashPass = hashPass;
	}

	public String verNombre(){
		return nombre;
	}

	public String verHashPass(){
		return hashPass;
	}

	public void modificarNombre(String nuevoNombre){
		if (nuevoNombre != null){
			nombre = nuevoNombre;
		}
	}
}