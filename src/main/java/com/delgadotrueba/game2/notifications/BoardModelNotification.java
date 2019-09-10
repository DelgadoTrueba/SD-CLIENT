package com.delgadotrueba.game2.notifications;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.Model;

public class BoardModelNotification {
	public ActionsBoardModel action;
	/**/
	public BoardModel model;
	
	/**/
	public int row;
	public int col;
	public String type;

	public BoardModelNotification(ActionsBoardModel action, BoardModel model) {
		this.action = action;
		this.model = model;
	}
	
	public BoardModelNotification(ActionsBoardModel action, int row, int col) {
		this.action = action;
		this.row = row;
		this.col = col;
	}
	
	public BoardModelNotification(ActionsBoardModel action, int row, int col, String type) {
		this.action = action;
		this.row = row;
		this.col = col;
		this.type = type;
	}
}
