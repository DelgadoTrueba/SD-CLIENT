package com.delgadotrueba.clienteJuego.juego.notifications;

import com.delgadotrueba.clienteJuego.juego.models.BoardModel;

public class BoardModelNotification {
	
	public ActionsBoardModel action;
	
	public BoardModel model;
	public int row;
	public int col;
	
	public BoardModelNotification(ActionsBoardModel action, BoardModel model, int row, int col) {
		this.action = action;
		this.model = model;
		this.row = row;
		this.col = col;
	}
	
}
