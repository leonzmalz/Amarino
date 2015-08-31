package com.exempla.bluetooth;

import com.example.appbluetooth.MainActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import at.abraxas.amarino.Amarino;

public class Bluetooth {

	private BluetoothAdapter meuBluetooth;
	private String mac;
	private static boolean conectou;
	private Context contexto;

	public Bluetooth(Context ctx) {
		this.conectou = false;
		this.meuBluetooth = BluetoothAdapter.getDefaultAdapter();
		this.contexto = ctx;
	}

	public boolean isConected() {
		return this.conectou;
	}

	public boolean isNull() {
		return this.meuBluetooth == null;
	}

	public boolean isEnabled() {
		return this.meuBluetooth.isEnabled();
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void conectar() {
		if (!this.mac.equals("")) {
			Amarino.connect(this.contexto, this.mac);
			this.conectou = true;
		}
	}

	public void desconectar() {
		if (this.conectou) {
			Amarino.disconnect(this.contexto, this.mac);
			this.conectou = false;
		}
	}

	public void enviarIntAmarino(char parameter, int value) {
		if (!this.mac.equals("")) {
			Amarino.sendDataToArduino(contexto, this.mac, parameter, value);
		}
	}
}
