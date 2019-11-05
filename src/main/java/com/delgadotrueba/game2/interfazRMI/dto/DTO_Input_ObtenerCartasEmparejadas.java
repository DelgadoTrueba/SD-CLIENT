package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_ObtenerCartasEmparejadas extends DTO_Input {
	
	private int parejas;

	public DTO_Input_ObtenerCartasEmparejadas(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}

	public int getParejas() {
		return parejas;
	}

	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}else{
				this.parejas = dataInput.readInt();
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}

}
