package com.delgadotrueba.game2.controllers;

import com.delgadotrueba.game2.models.BoardModel;

public interface BoardGame {

	/**
	 * Crea una partida (inicializa todas la cartas con el estado HIDDEN)
	 *  y asigna el turno al jugador uno, es decir al que crea la partida.
	 *
	 * @return int: Indica el nº de la partida creada
	 * @throws Exception: No se ha podido crear una nueva partida
	 */
	int initializeNewBoard() throws Exception;
	
	/**
	 * Se une a una partida que no haya comenzado, y te asigan el turno2
	 * 
	 * @param id: Id de la partida
	 * @throws Exception: No se ha podido unirse a la partida
	 */
	void joinBoard(int id) throws Exception;
	
	/**
	 * Devuelve el turno correspondiente al jugador al que le toca seleccionar cartas
	 * @return 0 == turno jugador1, 1== turno jugador2
	 * @throws Exception: No se puede obtener el turno, si no se ha inicializado la partida
	 */
	boolean getTurn() throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	BoardModel getBoard() throws Exception;
	
	/** (*) Mejora tambian cambia el turno
	 * Cambia el estado de esa carta a SELECTED
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	void setCardSelected(int row, int col) throws Exception;
		
	
	/**
	 * Valida si ha resuelto el tablero. Es decir, si todas las cartas poseen el
	 * estado MATCHED
	 * @return
	 * @throws Exception
	 */
	boolean isSolved() throws Exception;
	
}
