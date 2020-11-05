package com.kzurro.tablero;
public class Movimiento {
	
	private int fila;
	private int columna;
	private int valor;
	
	
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setFila(int fila) {
		this.fila = fila;
	}
	
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	//CONSTRUCTORES
	public Movimiento() {
		fila = -1;
		columna = -1;
		valor = 0;
	}
	
	public Movimiento(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.valor = -1;
	}
	
	public Movimiento(int valor) {
		this.fila = -1;
		this.columna = -1;
		this.valor = valor;
	}
	
	public Movimiento(int fila, int columna, int valor) {
		this.fila = fila;
		this.columna = columna;
		this.valor = valor;
	}
	
}
