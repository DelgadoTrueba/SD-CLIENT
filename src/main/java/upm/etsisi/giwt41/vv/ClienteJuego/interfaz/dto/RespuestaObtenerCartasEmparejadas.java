package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuestaObtenerCartasEmparejadas {
	
	private boolean err;
	private int parejas;
	private Exception exception;

	public RespuestaObtenerCartasEmparejadas(boolean err, int parejas, Exception exception) {
		this.err = err;
		this.parejas = parejas;
		this.exception = exception;
	}

	public boolean getErr() {
		return err;
	}

	public int getParejas() {
		return parejas;
	}

	public Exception getException() {
		return exception;
	}

}
