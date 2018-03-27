package modelo.excepcion;

public abstract class ExcepcionBase extends Exception {
	private static final long serialVersionUID = 1L;
	private String info;
	
	// Constructor de la clase
	public ExcepcionBase(String _info) {
		info = _info;
	}
	
	// Método toString()
	public String toString() {
		return info;
	}
}
