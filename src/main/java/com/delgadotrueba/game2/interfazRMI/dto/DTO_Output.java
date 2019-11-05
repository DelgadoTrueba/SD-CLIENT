package com.delgadotrueba.game2.interfazRMI.dto;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output {
	
	protected byte codop;

	public DTO_Output(byte codop) {
		this.codop = codop;
	}
	
	public void enviarDatosAlServidor(DataOutputStream dataOutput) throws IOException {
		dataOutput.write(codop);
	}
	
}
