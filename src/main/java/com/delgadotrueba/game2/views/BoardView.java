package com.delgadotrueba.game2.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.CellModel;
import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;
import com.delgadotrueba.game2.utils.Turn;

public class BoardView implements java.util.Observer{
	
	 private static final int NUMBER_OF_ROWS = 3;
	 private static final int NUMBER_OF_COLUMNS = 2;
	   
	 private CellView[][] btnBoard;
	 private JButton btnNumOfMatchedPairs_P1;
	 private JButton btnNumOfMatchedPairs_P2;
	 private JButton textInfo;
	 
	 private Turn turn;
	 
	 public BoardView(Turn turn) {
		 
		 this.turn = turn;
		 
		JFrame jframe = new JFrame("Memory Game. Sistemas Distribuidos");
		
		/**/
		JPanel jpanel2 = new JPanel();
		jpanel2.setLayout(new GridLayout(1, 4));
			
		IconPlayer player1 = new IconPlayer("boy");
	    btnNumOfMatchedPairs_P1 = new JButton("0");
	    btnNumOfMatchedPairs_P1.setBackground(Color.CYAN);
	    
	    IconPlayer player2 = new IconPlayer("girl");
	    btnNumOfMatchedPairs_P2 = new JButton("0");
	    btnNumOfMatchedPairs_P2.setBackground(Color.YELLOW);
	    

		jpanel2.add(player1);
		jpanel2.add(btnNumOfMatchedPairs_P1);
		jpanel2.add(btnNumOfMatchedPairs_P2);
		jpanel2.add(player2);

		
		jframe.add(jpanel2, BorderLayout.BEFORE_FIRST_LINE);
		/**/
		
		/**/
		JPanel jpanel = new JPanel();
		jpanel.setBackground(Color.WHITE);

		int BOARD_BORDER_WIDTH = 20;
		jpanel.setBorder(BorderFactory.createEmptyBorder(BOARD_BORDER_WIDTH, BOARD_BORDER_WIDTH, BOARD_BORDER_WIDTH, BOARD_BORDER_WIDTH));
		jpanel.setLayout(new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS));

		btnBoard = new CellView[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

		  for (int row = 0; row < NUMBER_OF_ROWS; row++) {
		   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
			   btnBoard[row][column] = new CellView(row, column);
			   btnBoard[row][column].setHiddenImage();
			   jpanel.add(btnBoard[row][column]);
		   }
		  }
		jframe.add(jpanel, BorderLayout.CENTER);
		/**/
		
		/**/
		JPanel jpanel3 = new JPanel();
		jpanel3.setLayout(new GridLayout(1, 2));
		textInfo = new JButton();
		
		IconPlayer playerChoosen = new IconPlayer("boy");
		playerChoosen.setText("You´re player 1");
		jpanel3.add(textInfo);
		jpanel3.add(playerChoosen);
	
		
		jframe.add(jpanel3, BorderLayout.SOUTH);
		/**/

		jframe.pack();
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jframe.setResizable(true);
		jframe.setVisible(true);		
	}
	 
	public void addController(ActionListener controller){
		 for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			 for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				 btnBoard[row][column].addActionListener(controller);	//need instance of controller before can add it as a listener 
			 }
		 }
	}

	public void setValue(int v){
		//myTextField.setText("" + v);
	}
	
	// OBSERBER.SUBSCRIBE()
	public void update(Observable obs, Object obj) {
	
		BoardModelNotification notification = ((BoardModelNotification)obj);
	
		if(obs instanceof BoardModel && ActionsBoardModel.setMatchedCard.equals(notification.action)) {
			int PEEK_DELAY = (int) 1 * 1000;
			Timer timer = new Timer(PEEK_DELAY, e -> setMatchedImage( notification.row, notification.col));
			timer.setRepeats(false);
			timer.start();
		}
	
		if(obs instanceof BoardModel && ActionsBoardModel.setHiddenCard.equals(notification.action)) {
			int PEEK_DELAY = (int) 1 * 1000;
			Timer timer = new Timer(PEEK_DELAY, e -> setHiddenImage( notification.row, notification.col));
			timer.setRepeats(false);
			timer.start();
		}
	
		if(obs instanceof BoardModel && ActionsBoardModel.setSelectedCard.equals(notification.action)) {
			String type = notification.model.mBoard[notification.row][notification.col].getType();
			setImage( notification.row, notification.col, type);
		}
		
		if(obs instanceof BoardModel && ActionsBoardModel.solved.equals(notification.action)) {
			solvedMessage();
		}
	} 

	//PUBLIC API
	public void displayNumOfMatchedPairs_P1(int num) {
		this.btnNumOfMatchedPairs_P1.setText(""+num);
	}
	
	public void displayNumOfMatchedPairs_P2(int num) {
		this.btnNumOfMatchedPairs_P2.setText(""+num);
	}
	
	public void hiddenImages() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   btnBoard[row][column].setHiddenImage();
			   }
		}
	}

	//PRIVATE API
	private void setImage(int row, int col, String num) {
		this.btnBoard[row][col].setImage(num);
	}
	
	private void setMatchedImage(int row, int col) {
		this.btnBoard[row][col].setEmptyImage();
	}
	
	private void setHiddenImage(int row, int col) {
		this.btnBoard[row][col].setHiddenImage();
	}
		 
	private void solvedMessage() {
		 if(turn.isPlayerOne()) JOptionPane.showMessageDialog(null, "You WIN, CONGRATULATIONS !!!", "RESULT", JOptionPane.INFORMATION_MESSAGE);
		 else JOptionPane.showMessageDialog(null, "You LOSE :( ", "RESULT", JOptionPane.INFORMATION_MESSAGE);
		 System.exit(0);
	}
	
	public void notIsYourTurn() {
		JOptionPane.showMessageDialog(null, "You must wait for your turn", "WAIT", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showInfoText() {
		if(turn.isPlayerOne()) {
			this.textInfo.setText("Tu turno elige un par de cartas !!!");
		}
		if(turn.isPlayerTwo()) {
			this.textInfo.setText("Turno de tu rival. ESPERANDO SU RESPUESTA.");
		}
	}
	
		
}
