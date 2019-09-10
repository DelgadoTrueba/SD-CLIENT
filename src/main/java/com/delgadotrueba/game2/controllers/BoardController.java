package com.delgadotrueba.game2.controllers;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.Model;
import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.View;

public class BoardController implements java.awt.event.ActionListener {
	
	BoardModel model;
	BoardView view;

	public BoardController() {	
		// Must be empty
	} 

	//invoked when a button is pressed
	public void actionPerformed(java.awt.event.ActionEvent e){
		//model.incrementValue();
	}

	public void addModel(BoardModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}

	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards){
		
		model.initModel(numOfMatchedPairs, numOfFailedAttempts, selectedCards);
	}
}
