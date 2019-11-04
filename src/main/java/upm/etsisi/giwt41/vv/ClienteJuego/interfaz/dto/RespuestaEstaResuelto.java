package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuestaEstaResuelto {

	private boolean err;
	private boolean resuelto;
	private Exception exception;
	
	public RespuestaEstaResuelto(boolean err, boolean resuelto, Exception exception) {
		super();
		this.err = err;
		this.resuelto = resuelto;
		this.exception = exception;
	}

	public boolean getErr() {
		return err;
	}

	public boolean getResuelto() {
		return resuelto;
	}

	public Exception getException() {
		return exception;
	}
		
}
