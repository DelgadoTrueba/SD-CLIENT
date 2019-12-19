package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_IniciarPartida extends DTO_Input {
	
	private byte OID;
	
	public DTO_Input_IniciarPartida(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		inicializarDatosApartirDeMensaje(dataInput);
	}
	
	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}
			else {
				this.OID = dataInput.readByte();
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}
	
	public byte getOID() {
		return this.OID;
	}
	
}
