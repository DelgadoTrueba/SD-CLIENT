package com.delgadotrueba.game2.notifications;

import com.delgadotrueba.game2.models.Model;

public class ModelNotification {

	public ActionsModel action;
	public Model model;
	
	public ModelNotification(ActionsModel action, Model model) {
		this.action = action;
		this.model = model;
	}
	
}
