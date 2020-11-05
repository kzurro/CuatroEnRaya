package com.kzurro.app;

import java.util.Scanner;

import com.kzurro.IA.MinimaxIA;
import com.kzurro.tablero.Movimiento;
import com.kzurro.tablero.Tablero;

import java.util.InputMismatchException; // gia thn Scanner
//import java.util.Random;

public class App {
	
	public static void main(String[] args) {
		
		//CRER EL OBJETO INTELIGENCIA ARTIFICIAL (IA) COMO JUGADO 'O' Y EL TABLERO DEL 4 EN RAYA.
        // SETEAR PROFUNDIDAD MAXIMA DEL ALGORITMO  MiniMax A 7 CON UNA PROFUNDIDAD MAYOR LA RESPUESTA DE UN i7 8 GEN  NO ES IMEDIATA.
		// A MAYOR VALOR DE LA 'maxProfundidad' mayor dificultad
		int XColumnaPosicion;
		int maxProfundidad = 4;
		MinimaxIA OJugador = new MinimaxIA(maxProfundidad, Tablero.O);
		Tablero cuatroEnRaya = new Tablero();

        // DESCOMENTANDO ESTA LINEA HACEMOS QUE EL PRIMER EN JUGAR EN LA IA (ORDENADOR)
//		cuatroEnRaya.setUltimaJugada(Tablero.X);

		System.out.println("4-en-Raya\n");
		System.out.println("\n*****************************");
		cuatroEnRaya.print();
		System.out.println();
        // MIENTRAS EL JUEGO NO HAYA ACABADO
		while(!cuatroEnRaya.checkGameOver()) {
			System.out.println("\n*****************************");
			switch (cuatroEnRaya.getUltimaJugada()) {
			
					
                // SI 'O' (IA) FUE EL ULTIMO EN JUGAR, EL SIGUIENTE ES 'X' (HUMANO).
				case Tablero.O:
                    System.out.print("Humano 'X' mueve.");
                    try {
        				do {
        					System.out.print("\n Columna (1-7): ");
							@SuppressWarnings("resource")
							Scanner sc = new Scanner(System.in);
        					XColumnaPosicion = sc.nextInt();
        				} while (cuatroEnRaya.checkColumnaLlena(XColumnaPosicion-1));
        			} catch (ArrayIndexOutOfBoundsException e){
        				System.err.println("\nNumeros validos: 1,2,3, 4,5, 6 or 7.\n");
        				break;
        			} catch (InputMismatchException e){
        				System.err.println("\nIntroduce un numero entero.\n");
        				break;
        			}
					cuatroEnRaya.makeMovimiento(XColumnaPosicion-1, Tablero.X);
					System.out.println();
					break;
					
                // SI 'X' (HUMANO) FUE EL ULTIMO EN JUGAR, EL SIGUIENTE ES 'O' (IA).
				case Tablero.X:
                    System.out.println("IA 'O' mueve.");
                    
                    // MUEVE IA
                    //MODIFICANDO EL ALGORITMO DE IA OBSERVABOS LOS TIEMPOS DE PROCESAMIENTO 
					Movimiento mueve_O = OJugador.MiniMaxAlphaBeta(cuatroEnRaya); //MiniMax(cuatroEnRaya);

					cuatroEnRaya.makeMovimiento(mueve_O.getColumna(), Tablero.O);
					System.out.println();
					break;
					
				default:
					break;
			}
			cuatroEnRaya.print();
		}
		
		System.out.println();

		if (cuatroEnRaya.getGanador() == Tablero.X) {
			System.out.println("¡Humano 'X' gana!");
		} else if (cuatroEnRaya.getGanador() == Tablero.O) {
			System.out.println("¡Inteligencia Artificial 'O' gana!");
		} else {
			System.out.println("¡Tablas!");
		}
		
		System.out.println("Game over.");
				
	}
	
}
