package com.example.lista;

import java.util.LinkedList;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		meuBluetooth = BluetoothAdapter.getDefaultAdapter();
		LinkedList<DispositivoBluetooth> listaDispositivos = getListaDispositivos(meuBluetooth
				.getBondedDevices());
		DispositivoBluetoothAdapter adapter = new DispositivoBluetoothAdapter(
				this, listaDispositivos);
		this.setListAdapter(adapter);
	}

	private LinkedList<DispositivoBluetooth> getListaDispositivos(
			Set<BluetoothDevice> dispositivos) {
		LinkedList<DispositivoBluetooth> lista = new LinkedList<>();

		if (dispositivos.size() > 0) {
			for (BluetoothDevice dispositivo : dispositivos) {
				String nome = dispositivo.getName();
				String mac = dispositivo.getAddress();
				lista.add(new DispositivoBluetooth(mac, nome));

			}
		}
		return lista;

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		DispositivoBluetooth dispositivo = (DispositivoBluetooth) this.getListAdapter().getItem(position);
		Intent retornaMac = new Intent();
		retornaMac.putExtra(ENDERECO_MAC, dispositivo.getMac());
		setResult(RESULT_OK, retornaMac);
		finish();
	}

}
