package com.example.lista;

import java.util.LinkedList;

import com.example.appbluetooth.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DispositivoBluetoothAdapter extends BaseAdapter {

	private Context contexto;
	private LinkedList<DispositivoBluetooth> listaDispositivos;
	
	public DispositivoBluetoothAdapter(Context ctx,LinkedList<DispositivoBluetooth> lista) {
		this.contexto = ctx;
		this.listaDispositivos = lista;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaDispositivos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.listaDispositivos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DispositivoBluetooth dispositivo = this.listaDispositivos.get(position);
		//Transformo o xml que contem a estrutura da lista em um objeto do tipo View
		View itemLista = LayoutInflater.from(contexto).inflate(R.layout.item_lista_devices, null);
		TextView txtNome    = (TextView) itemLista.findViewById(R.id.txtNome);
		TextView txtMac     = (TextView) itemLista.findViewById(R.id.txtMac);
		txtNome.setText(dispositivo.getNome());
		txtMac.setText(dispositivo.getMac());
		return itemLista;
	}

}
