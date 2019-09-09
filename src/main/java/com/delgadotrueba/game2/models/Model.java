package com.delgadotrueba.game2.models;

import com.delgadotrueba.game2.notifications.ActionsModel;
import com.delgadotrueba.game2.notifications.ModelNotification;

public class Model extends java.util.Observable {	
	
	private int counter;	//primitive, automatically initialised to 0

	public Model(){
		// Must be empty
	} 

	public void setValue(int value) {
		this.counter = value;
		setChanged();
		//model Push - send counter as part of the message
		notifyObservers(
				new ModelNotification(ActionsModel.incrementValue, this)
				);
	}
	
	public void incrementValue() {
		++counter;
		setChanged();
		//model Push - send counter as part of the message
		notifyObservers(
				new ModelNotification(ActionsModel.incrementValue, this)
				);
	}	
	
	public int getValue() {return this.counter;}
}
