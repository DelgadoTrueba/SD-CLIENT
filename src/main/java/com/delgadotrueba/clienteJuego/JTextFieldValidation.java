package com.delgadotrueba.clienteJuego;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JTextFieldValidation extends JTextField {

	 private static final long serialVersionUID = 1L;

	    @Override
	    public void processKeyEvent(KeyEvent ev) {
	        if (Character.isDigit(ev.getKeyChar())) {
	            super.processKeyEvent(ev);
	        }
	        ev.consume();
	        return;
	    }

	    /**
	     * As the user is not even able to enter a dot ("."), only integers (whole numbers) may be entered.
	     */
	    public Long getNumber() {
	        Long result = null;
	        String text = getText();
	        if (text != null && !"".equals(text)) {
	            result = Long.valueOf(text);
	        }
	        return result;
	    }
}
