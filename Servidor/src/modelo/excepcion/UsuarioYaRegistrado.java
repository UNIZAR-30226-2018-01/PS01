package modelo.excepcion;

public class UsuarioYaRegistrado extends ExcepcionBase {
	private static final long serialVersionUID = 1L;

	// Constructor de la clase
	public UsuarioYaRegistrado(String info){
		super(info);
	}
}
