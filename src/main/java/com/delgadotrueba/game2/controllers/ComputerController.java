package com.delgadotrueba.game2.controllers;

import java.util.Random;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.utils.Turn;
import com.delgadotrueba.game2.views.BoardView;

public class ComputerController extends Thread {

	private BoardModel model;
	private BoardView view;
	
	private Turn turn;
	private int numOfMatched_P2 = 0;
	
	public ComputerController(Turn turn) {	
		this.turn = turn;
	} 
	
	public void addModel(BoardModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}
	
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards){
		
	}
	
	public void actionPerformed() {
		Random random = new Random();
		int row = (0 + random.nextInt(3));
		int col = (0 + random.nextInt(2));
		
		if (!(turn.isPlayerTwo())) {
			return;
		}
		
  		if (!this.model.isCardValid(row, col)) {
  			return;
  		}
  		
  		this.model.setCardSelected(row, col);


  		if ( this.model.isPlayable() ) {
  			
  			if (this.model.areSelectedCardsSameType()){
  				
  				this.model.setSelectedCardsMatched();
  				
  				this.incrementNumOfMatchedPairs();
  		  		
  				this.model.isSolved();
  			} else {
  				  				
  				this.model.setSelectedCardsHidden();
  				
  				this.turn.changeTurn();
  				this.view.showInfoText();
  			}
  			
  		}
	}
	
	public void incrementNumOfMatchedPairs() {
		if(turn.isPlayerTwo()) {
			this.numOfMatched_P2 = this.numOfMatched_P2 +1 ;
			this.view.displayNumOfMatchedPairs_P2(numOfMatched_P2);
		}	
	}
	
	public void run(){  
	  while(true){  
	    try{
	    	Thread.sleep(1500);
	    	actionPerformed();
	    }catch(InterruptedException e){
	    	System.out.println(e);
	    }  
	  }  
	}
}
