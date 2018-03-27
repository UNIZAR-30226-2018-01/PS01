package modelo.excepcion;

public class SesionExistente extends ExcepcionBase{
	private static final long serialVersionUID = 1L;
	
	public SesionExistente(String info) {
		super(info);
	}
}
