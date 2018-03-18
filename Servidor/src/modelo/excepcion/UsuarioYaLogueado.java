package modelo.excepcion;

public class UsuarioYaLogueado extends ExcepcionBase{
	private static final long serialVersionUID = 1L;
	
	public UsuarioYaLogueado(String info) {
		super(info);
	}
}
