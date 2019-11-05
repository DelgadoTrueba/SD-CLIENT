package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_EmparejarCartas extends DTO_Input {

	private boolean resultado;

	public DTO_Input_EmparejarCartas(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}
	
	public boolean getResultado() {
		return resultado;
	}
	
	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}
			else {
				boolean resul = dataInput.readBoolean();
				this.resultado = resul;
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}
		
}
