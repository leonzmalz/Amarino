package com.example.eventos;

import android.view.View;
import android.view.View.OnClickListener;

import com.exempla.bluetooth.Bluetooth;

public class ConectarBluetooth implements OnClickListener{
	
	private Bluetooth meuBluetooth;
	
	public ConectarBluetooth(Bluetooth value) {
		this.meuBluetooth = value;
	}

	@Override
	public void onClick(View v) {
		
	}

}
