package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuestaEmparejarCartas {

	private boolean err;
	private boolean resultado;
	private Exception exception;
	
	public RespuestaEmparejarCartas(boolean err, boolean resultado, Exception exception) {
		this.err = err;
		this.resultado = resultado;
		this.exception = exception;
	}
	
	public boolean getResultado() {
		return resultado;
	}

	public boolean getErr() {
		return err;
	}

	public Exception getException() {
		return exception;
	}
		
}
