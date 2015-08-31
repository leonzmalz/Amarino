package com.example.lista;

import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaDeDispositivos extends ListActivity {

	public static final String ENDERECO_MAC = null;
	private BluetoothAdapter meuBluetooth;
	private String mac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ArrayAdapter<String> arrayBluetooth = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		meuBluetooth = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> dispositivosPareados = meuBluetooth
				.getBondedDevices();

		if (dispositivosPareados.size() > 0) {
			for (BluetoothDevice dispositivo : dispositivosPareados) {
				String nome = dispositivo.getName();
				String mac = dispositivo.getAddress();

				arrayBluetooth.add(nome + "\n" + mac);
			}
		}
		setListAdapter(arrayBluetooth);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String infoGeral = ((TextView) v).getText().toString();
		//Total menos o tamanho do endereço mac (17)
		String endereco = infoGeral.substring(infoGeral.length() - 17); 
		
		Intent retornaMac = new Intent();
		retornaMac.putExtra(ENDERECO_MAC, endereco);
		setResult(RESULT_OK,retornaMac);
		finish();
	}

}
