package com.delgadotrueba.clienteJuego.juego.mvc.notifications;

import com.delgadotrueba.clienteJuego.juego.mvc.models.BoardModel;

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
