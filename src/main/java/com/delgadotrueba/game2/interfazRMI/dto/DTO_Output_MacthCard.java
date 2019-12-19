package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class DTO_Output_MacthCard extends DTO_Output {

	private byte r1;
	private byte c1;
	private byte r2;
	private byte c2;
	
	public DTO_Output_MacthCard(byte oid, byte codop, byte r1, byte c1, byte r2, byte c2) throws IOException {
		super(oid, codop);
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
	}
	
	public void enviarDatosAlServidor(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlServidor(dataOutput);
		dataOutput.write(r1);
		dataOutput.write(c1);
		dataOutput.write(r2);
		dataOutput.write(c2);
	}

}
