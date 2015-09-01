package com.example.eventos;

import android.view.View;
import android.view.View.OnClickListener;

import com.exemple.bluetooth.AndroidToArduino;

public class EnviarDadosBinarios implements OnClickListener {

	private char parametro;
	private int valor;
	private AndroidToArduino comunicaArduino;

	public EnviarDadosBinarios(char nomeParametro, int valorParametro,
			AndroidToArduino arduino) {
		this.parametro = nomeParametro;
		this.valor = valorParametro;
		this.comunicaArduino = arduino;
	}

	@Override
	public void onClick(View v) {
		this.comunicaArduino.enviarIntAmarino(parametro, valor);

	}

}
