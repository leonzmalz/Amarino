package com.example.appbluetooth;

import com.example.lista.ListaDeDispositivos;
import com.exempla.bluetooth.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;

public class MainActivity extends Activity {

	private Button btnConectar, btnDesconectar, btnLigar1, btnDesligar1,
			btnLigar2, btnDesligar2;

	private static final int SOLICITA_ATIVACAO = 1;
	private static final int SOLICITA_CONEXAO = 2;
	private Bluetooth meuBluetooth;
	private StatusAmarino statusAmarino;
	private boolean conectou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnConectar = (Button) findViewById(R.id.btnConectar);
		btnDesconectar = (Button) findViewById(R.id.btnDesconectar);
		btnLigar1 = (Button) findViewById(R.id.btnLigar1);
		btnDesligar1 = (Button) findViewById(R.id.btnDesligar1);
		btnLigar2 = (Button) findViewById(R.id.btnLigar2);
		btnDesligar2 = (Button) findViewById(R.id.btnDesligar2);

		this.statusAmarino = new StatusAmarino();
		this.meuBluetooth = new Bluetooth(this);
		if (!this.ativarBluetooth())
			Toast.makeText(this, "Não foi possível ativar o bluetooth",
					Toast.LENGTH_SHORT).show();

		//Registro de eventos
		btnConectar.setOnClickListener(new BotaoConectar());
		btnDesconectar.setOnClickListener(new BotaoDesconectar());
		btnLigar1.setOnClickListener(new EnviarComandoBinario('A', 1));
		btnDesligar1.setOnClickListener(new EnviarComandoBinario('A', 0));
		btnLigar2.setOnClickListener(new EnviarComandoBinario('B', 1));
		btnDesligar2.setOnClickListener(new EnviarComandoBinario('B', 0));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SOLICITA_ATIVACAO:
			if (resultCode == Activity.RESULT_OK)
				Toast.makeText(this, "Bluetooth Ativado", Toast.LENGTH_SHORT)
						.show();
			else {
				Toast.makeText(this, "Bluetooth não foi ativado",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			break;

		case SOLICITA_CONEXAO:
			if (resultCode == Activity.RESULT_OK) {
				meuBluetooth.setMac(data.getExtras().getString(
						ListaDeDispositivos.ENDERECO_MAC));
				registerReceiver(statusAmarino, new IntentFilter(
						AmarinoIntent.ACTION_CONNECTED));
				
				meuBluetooth.conectar();

			} else {
				Toast.makeText(this, "Falha ao obter o endereço mac",
						Toast.LENGTH_SHORT).show();
				finish();

			}
			break;
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		

	}

	private boolean ativarBluetooth() {
		if (!meuBluetooth.isNull()) {
			if (!meuBluetooth.isEnabled()) {
				Intent ativaBluetooth = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
				return true;
			}
		}
		return false;

	}

	public class StatusAmarino extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			if (AmarinoIntent.ACTION_CONNECTED.equals(action)) {
				Toast.makeText(context, "Bluetooth do arduino está conectado",
						Toast.LENGTH_SHORT).show();
				conectou = true;
			} else if (AmarinoIntent.ACTION_CONNECTION_FAILED.equals(action)) {
				Toast.makeText(context,
						"Erro durante a conexão ao bluetooth do arduino",
						Toast.LENGTH_SHORT).show();

			}
		}

	}

	public class BotaoConectar implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent abreLista = new Intent(MainActivity.this,
					ListaDeDispositivos.class);
			startActivityForResult(abreLista, SOLICITA_CONEXAO);

		}

	}

	public class BotaoDesconectar implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (conectou) {
				Amarino.disconnect(MainActivity.this, meuBluetooth.getMac());
				unregisterReceiver(statusAmarino);
				conectou = false;
			}

		}

	}

	// Envia um comando para o arduino
	public class EnviarComandoBinario implements OnClickListener {

		private char parametro;
		private int valor;

		public EnviarComandoBinario(char nomeParametro, int valorParametro) {
			this.parametro = nomeParametro;
			this.valor = valorParametro;
		}

		@Override
		public void onClick(View v) {
			if (conectou) {
				meuBluetooth.enviarIntAmarino(parametro, valor);
				
			}

		}

	}
}
