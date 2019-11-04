package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuestaObtenerPuntosJugador {
	
	private boolean err;
	private byte puntos;
	private Exception exception;
	
	public RespuestaObtenerPuntosJugador(boolean err, byte puntos, Exception exception) {
		this.err = err;
		this.puntos = puntos;
		this.exception = exception;
	}

	public boolean getErr() {
		return err;
	}

	public byte getPuntos() {
		return puntos;
	}

	public Exception getException() {
		return exception;
	}

}
