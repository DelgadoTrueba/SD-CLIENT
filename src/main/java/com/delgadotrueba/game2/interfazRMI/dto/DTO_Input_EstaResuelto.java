package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_EstaResuelto extends DTO_Input {

	private boolean resuelto;
	
	public DTO_Input_EstaResuelto(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}

	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}
			else  {
				this.resuelto = dataInput.readBoolean();
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}
	
	public boolean getResuelto() {
		return resuelto;
	}

}
