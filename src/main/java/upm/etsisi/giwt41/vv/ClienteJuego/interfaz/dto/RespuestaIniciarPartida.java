package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuestaIniciarPartida {
	private boolean err;
	private Exception exception;
	
	public RespuestaIniciarPartida(boolean err, Exception exception) {
		this.err = err;
		this.exception = exception;
	}

	public boolean getErr() {
		return err;
	}

	public Exception getException() {
		return exception;
	}	
	
}
