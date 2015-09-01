package com.exemple.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import at.abraxas.amarino.AmarinoIntent;

public class StatusAmarino extends BroadcastReceiver {
	
	private AndroidToArduino comunicaArduino;
	
	public StatusAmarino(AndroidToArduino arduino) {
		this.comunicaArduino = arduino;
	}
	@Override
	public void onReceive(Context context, Intent intent) {

		final String action = intent.getAction();

		if (AmarinoIntent.ACTION_CONNECTED.equals(action)) {
			Toast.makeText(context, "Bluetooth do arduino está conectado",
					Toast.LENGTH_LONG).show();
			this.comunicaArduino.setConectou(true);
			
		} else if (AmarinoIntent.ACTION_CONNECTION_FAILED.equals(action)) {
			Toast.makeText(context,
					"Erro durante a conexão ao bluetooth do arduino",
					Toast.LENGTH_LONG).show();

		}
	}

}
