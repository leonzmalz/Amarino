package com.example.lista;

public class DispositivoBluetooth {

	private String mac;
	private String nome;
	
	
	public DispositivoBluetooth(String mac, String nome) {
		this.mac = mac;
		this.nome = nome;
	}
	
	public String getMac() {
		return mac;
	}
	public String getNome() {
		return nome;
	}
	
	
}
