package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto;

public class RespuetaTiposCartas {
	
	private boolean err;
	private byte[] tipos;
	private Exception exception;
	
	public RespuetaTiposCartas(boolean err, byte[] tipos, Exception exception) {
		this.err = err;
		this.tipos = tipos;
		this.exception = exception;
	}

	public boolean getErr() {
		return err;
	}

	public byte[] getTipos() {
		return tipos;
	}
	
	public Exception getException() {
		return exception;
	}	
		
}
