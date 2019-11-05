package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.game2.interfazRMI.exceptions.InvalidDataInterfaceException;

public class DTO_Input_TiposCartas extends DTO_Input {
	
	private byte[] tipos;

	public DTO_Input_TiposCartas(DataInputStream dataInput) throws InvalidDataInterfaceException {
		super();
		this.inicializarDatosApartirDeMensaje(dataInput);
	}

	public byte[][] getTipos() {
		return tradudirTipoCarta_bytes(tipos);
	}
	
	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException{
		try {
			this.err =  dataInput.readBoolean();
			if(err) {
				/*LEER ERROR*/
			}else {
				byte tipo;
				byte[] tipos = new byte [16];
				for(int i = 0; i<16; i++) {
					tipo = dataInput.readByte();
					tipos[i]=tipo;
				}
				this.tipos = tipos;
			}
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
	}
	
	///////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	//////////////////////////////////////////////////////////////
	private static byte[][] tradudirTipoCarta_bytes(byte[] tipos) {
		byte[][] string_tipos = new byte[4][4];
		for (int row = 0; row < tipos.length; row++) {
			  if(row >=0 && row<=3) {
				  string_tipos[0][row%4] = (tipos[row]);
			  }
			  if(row >=4 && row<=7) {
				  string_tipos[1][row%4] = (tipos[row]);
			  }
			  if(row >=8 && row<=11) {
				  string_tipos[2][row%4] = (tipos[row]);
			  }
			  if(row >=12 && row<=15) {
				  string_tipos[3][row%4] = (tipos[row]);
			  }
		}
		return string_tipos;
	}
}
