package com.example.appbluetooth;

import com.example.eventos.EnviarDadosBinarios;
import com.example.lista.ListaDeDispositivos;
import com.exemple.bluetooth.AndroidToArduino;
import com.exemple.bluetooth.StatusAmarino;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import at.abraxas.amarino.AmarinoIntent;

public class MainActivity extends Activity {

	private Button btnConectar, btnDesconectar, btnLigar1, btnDesligar1,
			btnLigar2, btnDesligar2;

	private static final int SOLICITA_ATIVACAO = 1;
	public static final int SOLICITA_CONEXAO = 2;
	private BluetoothAdapter meuBluetooth;
	private AndroidToArduino comunicaArduino;
	private StatusAmarino statusAmarino;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.meuBluetooth = BluetoothAdapter.getDefaultAdapter();

		if (this.meuBluetooth == null) {
			if (!this.ativarBluetooth()) {
				Toast.makeText(this, "Não foi possível ativar o bluetooth",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

		btnConectar = (Button) findViewById(R.id.btnConectar);
		btnDesconectar = (Button) findViewById(R.id.btnDesconectar);
		btnLigar1 = (Button) findViewById(R.id.btnLigar1);
		btnDesligar1 = (Button) findViewById(R.id.btnDesligar1);
		btnLigar2 = (Button) findViewById(R.id.btnLigar2);
		btnDesligar2 = (Button) findViewById(R.id.btnDesligar2);

		btnConectar.setOnClickListener(new BotaoConectar());
		btnDesconectar.setOnClickListener(new BotaoDesconectar());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		boolean sucesso = false;
		switch (requestCode) {
		case SOLICITA_ATIVACAO:
			sucesso = verificaAtivacao(resultCode == Activity.RESULT_OK);
			break;
		case SOLICITA_CONEXAO:
			sucesso = iniciaConexao(resultCode == Activity.RESULT_OK, data);
			break;
		}

		if (!sucesso)
			finish();

	}

	private boolean verificaAtivacao(boolean ativou) {
		if (ativou) {
			Toast.makeText(this, "Bluetooth Ativado", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		Toast.makeText(this, "Bluetooth não foi ativado", Toast.LENGTH_SHORT)
				.show();
		return false;
	}

	private boolean iniciaConexao(boolean conectou, Intent data) {
		if (conectou) {
			this.comunicaArduino = new AndroidToArduino(this, data.getExtras()
					.getString(ListaDeDispositivos.ENDERECO_MAC));
			this.habilitarComunicacao();
			this.habilitarEventos();
			return true;
		}
		Toast.makeText(this, "Falha ao obter o endereço mac",
				Toast.LENGTH_SHORT).show();
		return false;

	}

	private void habilitarComunicacao() {
		this.statusAmarino = new StatusAmarino(this.comunicaArduino);
		registerReceiver(statusAmarino, new IntentFilter(
				AmarinoIntent.ACTION_CONNECTED));

	}

	private void habilitarEventos() {
		btnLigar1.setOnClickListener(new EnviarDadosBinarios('A', 1,
				this.comunicaArduino));
		btnDesligar1.setOnClickListener(new EnviarDadosBinarios('A', 0,
				this.comunicaArduino));
		btnLigar2.setOnClickListener(new EnviarDadosBinarios('B', 1,
				this.comunicaArduino));
		btnDesligar2.setOnClickListener(new EnviarDadosBinarios('B', 0,
				this.comunicaArduino));
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (this.comunicaArduino != null) {
			this.comunicaArduino.desconectar();
			unregisterReceiver(statusAmarino);

		}

	}

	private boolean ativarBluetooth() {
		if (!meuBluetooth.isEnabled()) {
			Intent ativaBluetooth = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
			return true;
		}
		return false;

	}

	public class BotaoDesconectar implements OnClickListener {
		@Override
		public void onClick(View v) {
			comunicaArduino.desconectar();
			unregisterReceiver(statusAmarino);

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

}
