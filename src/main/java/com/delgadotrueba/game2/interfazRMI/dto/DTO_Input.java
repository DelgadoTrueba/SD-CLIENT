package com.delgadotrueba.game2.interfazRMI.dto;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input {

	protected boolean err;
	protected Exception excep;
	
	public DTO_Input() throws InvalidDataInterfaceException {

	}
	
	public boolean getErr() {
		return err;
	}
	
	public Exception getException() {
		return excep;
	}
	
}
