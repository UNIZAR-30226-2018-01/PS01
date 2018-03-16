package modelo.excepcion;

/*
 * Excepción que será lanzada cuando se haya intentado autenticar a un usuario
 * que no existe
 */
public class LoginInexistente extends Exception{
	private String info;
	private static final long serialVersionUID = 1L;
	
	// Constructor de la clase
	public LoginInexistente(String _info) {
		info = _info;
	}
	
	public String toString() {
		return info;
	}
}
