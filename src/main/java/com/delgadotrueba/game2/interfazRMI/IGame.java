package com.delgadotrueba.game2.interfazRMI;

import com.delgadotrueba.game2.interfazRMI.exceptions.RMIClientException;

public interface IGame {

	public byte newGame() throws RMIClientException;
	
	public byte[][] obtenerTiposCartas() throws RMIClientException;
	
	public int obtenerCartasEmparejadas() throws RMIClientException;
	
	public byte obtenerPuntosJ1() throws RMIClientException;
	
	public byte obtenerPuntosJ2() throws RMIClientException;
	
	public boolean matchCardsP1(byte r1, byte c1, byte r2, byte c2) throws RMIClientException;
	
	public boolean isSolved() throws RMIClientException;
	
	public int obtenerCartasSeleccionadasJ2() throws RMIClientException;

}
