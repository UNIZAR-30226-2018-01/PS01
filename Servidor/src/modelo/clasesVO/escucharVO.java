package modelo.clasesVO;

public class escucharVO {
	private String nombreLista;
	private String nombreCreador;
	private String nombreListener;

	public escucharVO(String nombreLista, String nombreCreador, String nombreListener){
		this.nombreLista = nombreLista;
		this.nombreCreador = nombreCreador;
		this.nombreListener = nombreListener;
	}

	public String verNombreLista(){
		return nombreLista;
	}

	public String verNombreCreador(){
		return nombreCreador;
	}

	public String verNombreListener(){
		return nombreListener;
	}

	public void modificarNombreLista(String nuevaLista){
		if (nuevaLista != null){
			nombreLista = nuevaLista;
		}
	}

	public void modificarNombreCreador(String nuevoCreador){
		if (nuevoCreador != null){
			nombreCreador = nuevoCreador;
		}
	}

	public void modificarNombreListener(String _nombreListener){
		if (nombreListener != null){
			nombreListener = _nombreListener;
		}
	}
}