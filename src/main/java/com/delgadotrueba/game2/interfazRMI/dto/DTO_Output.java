package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output {
	
	protected byte oid;
	protected byte codop;

	public DTO_Output(byte oid, byte codop) {
		this.oid = oid;
		this.codop = codop;
	}
	
	public void enviarDatosAlServidor(DataOutputStream dataOutput) throws IOException {
		dataOutput.write(oid);
		dataOutput.write(codop);
	}
	
}
