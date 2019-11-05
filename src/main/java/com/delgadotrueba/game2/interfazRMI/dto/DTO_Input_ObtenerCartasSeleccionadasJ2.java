package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_ObtenerCartasSeleccionadasJ2 extends DTO_Input {
	
	private int selecciondas;

	public DTO_Input_ObtenerCartasSeleccionadasJ2(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}

	public int getCartasSelecciondasJ2() {
		return selecciondas;
	}

	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}else {
				this.selecciondas = dataInput.readInt();
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}

}
