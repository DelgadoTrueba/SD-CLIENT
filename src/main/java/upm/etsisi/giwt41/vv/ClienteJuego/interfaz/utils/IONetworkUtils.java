package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.utils;

public class IONetworkUtils {

	public static byte[] tradudirTipoCarta_int(int[][] tipos) {
		byte[] bytes_tipos = new byte[tipos.length*tipos.length];
		int i = 0;
		for (int row = 0; row < tipos.length; row++) {
			   for (int column = 0; column < tipos[0].length; column++) {
				   int tipo = tipos[row][column];
				   byte aux = (byte) tipo;
				   bytes_tipos[i] = aux;
				   i++;
			   }
		}
		return bytes_tipos;
	}
	
	public static int[][] tradudirTipoCarta_bytes(byte[] tipos) {
		int[][] string_tipos = new int[4][4];
		for (int row = 0; row < tipos.length; row++) {
			  if(row >=0 && row<=3) {
				  string_tipos[0][row%4] = ((int)tipos[row]);
			  }
			  if(row >=4 && row<=7) {
				  string_tipos[1][row%4] = ((int)tipos[row]);
			  }
			  if(row >=8 && row<=11) {
				  string_tipos[2][row%4] = ((int)tipos[row]);
			  }
			  if(row >=12 && row<=15) {
				  string_tipos[3][row%4] = ((int)tipos[row]);
			  }
		}
		return string_tipos;
	}
	
	
}
