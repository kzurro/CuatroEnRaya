package com.kzurro.tablero;
public class Parametros {
	
	private int modoJuego;
	private int maxProfundidad;
	private String jugador1;
	private String jugador2;
	
	// VARIABLES PARA INDICAR QUE TIPO DE MODOS DE JUEGO
	public static final int humanoVSIa = 1; // JUGADOR VS INTELIGENCIA ARTIFICIAL
	public static final int humanoVSHumano = 2; //// JUGADOR 1 VS JUGADOR 2
	
	public Parametros() {
		// VALORES POR DEFECTO
		this.maxProfundidad = 4;
		this.jugador1 = "ROJO";
		this.jugador2 = "AMARILLO";
		this.modoJuego = humanoVSIa;
	}

	public int getMaxProfundidad() {
		return this.maxProfundidad;
	}

	public void setMaxProfundidad(int dificultad) {
		this.maxProfundidad = dificultad;
	}
	
	public String getJugador1() {
		return jugador1;
	}

	public void setJugador1(String jugador1) {
		this.jugador1 = jugador1;
	}
	
	public String getJugador2() {
		return jugador2;
	}

	public void setJugador2(String jugador2) {
		this.jugador2 = jugador2;
	}

	public int getModoJuego() {
		return modoJuego;
	}

	public void setModoJuego(int modoJuego) {
		this.modoJuego = modoJuego;
	}


}
