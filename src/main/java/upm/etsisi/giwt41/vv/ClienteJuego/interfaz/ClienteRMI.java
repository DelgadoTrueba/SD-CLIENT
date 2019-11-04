package upm.etsisi.giwt41.vv.ClienteJuego.interfaz;

public interface ClienteRMI {

	public void newGame() throws RMIClientException;
	
	public byte[] obtenerTiposCartas() throws RMIClientException;
	
	public int obtenerCartasEmparejadas() throws RMIClientException;
	
	public byte obtenerPuntosJ1() throws RMIClientException;
	
	public byte obtenerPuntosJ2() throws RMIClientException;
	
	public boolean matchCardsP1(byte r1, byte c1, byte r2, byte c2) throws RMIClientException;
	
	public boolean isSolved() throws RMIClientException;
	
	public int obtenerCartasSeleccionadasJ2() throws RMIClientException;

}
