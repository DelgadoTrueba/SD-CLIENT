package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_ObtenerPuntosJugador extends DTO_Input{
	
	private byte puntos;
	
	public DTO_Input_ObtenerPuntosJugador(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}


	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}else {
				this.puntos = dataInput.readByte();
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}
	
	public byte getPuntos() {
		return puntos;
	}


}
