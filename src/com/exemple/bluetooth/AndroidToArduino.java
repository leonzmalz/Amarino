package com.exemple.bluetooth;

import android.content.Context;
import at.abraxas.amarino.Amarino;

public class AndroidToArduino {

	private boolean conectou;
	private Context contexto;
	private String mac;

	public AndroidToArduino(Context ctx, String mac) {
		this.contexto = ctx;
		this.conectou = false;
		this.mac = mac;
		if(!mac.equals(""))
			this.conectar();
	}

	public void setConectou(boolean conectou) {
		this.conectou = conectou;
	}
	
	public void conectar() {
		if (!conectou) {
			Amarino.connect(this.contexto, this.mac);
		}

	}

	public void desconectar() {
		if (this.conectou) {
			Amarino.disconnect(this.contexto, this.mac);
			this.conectou = false;
		}
	}

	public void enviarIntAmarino(char parameter, int value) {
		if (this.conectou)
			Amarino.sendDataToArduino(this.contexto, this.mac, parameter, value);

	}

}
