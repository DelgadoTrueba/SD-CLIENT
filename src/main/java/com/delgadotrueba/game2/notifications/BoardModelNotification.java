package com.delgadotrueba.game2.notifications;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.Model;

public class BoardModelNotification {
	public ActionsBoardModel action;
	public BoardModel model;
	
	public BoardModelNotification(ActionsBoardModel action, BoardModel model) {
		this.action = action;
		this.model = model;
	}
}
