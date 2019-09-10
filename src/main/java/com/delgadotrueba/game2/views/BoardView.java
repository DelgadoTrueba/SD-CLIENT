package com.delgadotrueba.game2.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;

public class BoardView implements java.util.Observer{
	
	 private static final int NUMBER_OF_ROWS = 4;
	 private static final int NUMBER_OF_COLUMNS = 6;
	   
	 private CellView[][] btnBoard;
	 private JButton btnNumOfMatchedPairs;
	 private JButton btnNumOfFailedAttempts;
	 private JButton btnSelectedCards;
	 
	 public BoardView() {
		JFrame jframe = new JFrame("Memory Game. Sistemas Distribuidos");
		
		/**/
		JPanel jpanel2 = new JPanel();
		jpanel2.setLayout(new GridLayout(1, 3));
				
	    btnNumOfMatchedPairs = new JButton("numOfMatchedPairs: X");
	    btnNumOfFailedAttempts = new JButton("numOfFailedAttempts: X");
	    btnSelectedCards = new JButton("selectedCards: X");

		jpanel2.add(btnNumOfMatchedPairs);
		jpanel2.add(btnNumOfFailedAttempts);
		jpanel2.add(btnSelectedCards);

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
			   jpanel.add(btnBoard[row][column]);
		   }
		  }
		jframe.add(jpanel, BorderLayout.CENTER);
		/**/

		JSplitPane mSplitPane = new JSplitPane();
		jframe.add(mSplitPane, BorderLayout.SOUTH);
	
		JButton mRetryButton = new JButton("Retry");
		mRetryButton.setFocusPainted(false);
		mRetryButton.addActionListener(new RetryBoard());
		mSplitPane.setLeftComponent(mRetryButton);
	
		JButton mNewButton = new JButton("New Game");
		mNewButton.setFocusPainted(false);
		mNewButton.addActionListener(new NewBoard());
		mSplitPane.setRightComponent(mNewButton);
		
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
		
		if(obs instanceof BoardModel && ActionsBoardModel.setNumOfMatchedPairs.equals(notification.action)) {
			int num = notification.model.getNumOfMatchedPairs();
			this.btnNumOfMatchedPairs.setText("numOfMatchedPairs: " + num);
		}
		if(obs instanceof BoardModel && ActionsBoardModel.setNumOfFailedAttempts.equals(notification.action)) {
			int num = notification.model.getNumOfFailedAttempts();
			this.btnNumOfFailedAttempts.setText("numOfFailedAttempts: " + num);
		}
		if(obs instanceof BoardModel && ActionsBoardModel.setSelectedCards.equals(notification.action)) {
			int num = notification.model.getSelectedCards();
			this.btnSelectedCards.setText("selectedCards: " + num);
		}
	} 
	
	//PUBLIC API
	public void setImage(int row, int col, String num) {
		this.btnBoard[row][col].setImage(num);
	}
	
	public void setEmptyImage(int row, int col) {
		this.btnBoard[row][col].setEmptyImage();
	}
	
	public void setHiddenImage(int row, int col) {
		this.btnBoard[row][col].setHiddenImage();
	}
	
	//EVENTOS
	private class RetryBoard implements ActionListener {
		 public void actionPerformed(ActionEvent e) {
			 JOptionPane.showMessageDialog(null, "Init", "Info", JOptionPane.INFORMATION_MESSAGE);
		 }
	 }
	 
	private class NewBoard implements ActionListener {
		 public void actionPerformed(ActionEvent e) {
			 JOptionPane.showMessageDialog(null, "Init", "Info", JOptionPane.INFORMATION_MESSAGE);
		 }
	}
	 
	public void finalMessage(int numOfFailedAttempts, int MAX_NUM_OF_CARDS ) {
		 Float numeralScore = (((float) numOfFailedAttempts) / ((float) MAX_NUM_OF_CARDS)) * 100;
		 String textualScore = numeralScore.toString();
	
		 JOptionPane.showMessageDialog(null, "Solved!! Your results:\n" + " Failed Attempts: " + numOfFailedAttempts + "\n Error percentage : " + textualScore + " %", "RESULTS", JOptionPane.INFORMATION_MESSAGE);
	}
}
