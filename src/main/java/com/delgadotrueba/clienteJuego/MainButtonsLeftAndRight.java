package com.delgadotrueba.clienteJuego;
import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.delgadotrueba.game2.interfazRMI.exceptions.RMIClientException;

public class MainButtonsLeftAndRight {

	    private JFrame frame;
	    private JPanel pane;
	    private JButton button1;
	    private JButton button2;

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new MainButtonsLeftAndRight()::createAndShowGui);
	    }

	    public void createAndShowGui() {
	        frame = new JFrame(getClass().getSimpleName());

	        pane = new JPanel();
	        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

	        button1 = new JButton("INICIAR NUEVA PARTIDA");
	        button1.addActionListener(this.eventoButton1());
	        button2 = new JButton("RE-CONECTAR A UNA PARTIDA");
	        button2.addActionListener(this.eventoButton2());

	        pane.add(button1);
	        pane.add(Box.createHorizontalGlue());
	        pane.add(button2);

	        frame.add(pane, BorderLayout.SOUTH);

	        frame.pack();
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	    
	    private ActionListener eventoButton1() {
	    	return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						RunBoardMVC mainRunBoardMVC = new RunBoardMVC();
						frame.setVisible(false);
					} catch (RMIClientException e1) {
						errorNewGame();
					}
					
				}
			};
	    }
	    
	    private ActionListener eventoButton2() {
	    	return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						UnirseBoardMVC unirseRunBoardMVC = new UnirseBoardMVC();
						frame.setVisible(false);
					} catch (RMIClientException e1) {
						errorOldGame();
					}	
				}
			};
	    }
	    
	    private void errorNewGame() {
			JOptionPane.showMessageDialog(null, "ERROR AL INTENTAR CREAR UN NUEVO JUEGO EN EL SERVIDOR", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	    
	    private void errorOldGame() {
			JOptionPane.showMessageDialog(null, "ERROR AL INTENTAR RE-CONECTARSE AL JUEGO EXISTENTE EN EL SERVIDOR", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	   
}
