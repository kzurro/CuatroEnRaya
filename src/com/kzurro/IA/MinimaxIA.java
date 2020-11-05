package com.kzurro.IA;
import java.util.ArrayList;
import java.util.Random;

import com.kzurro.tablero.Movimiento;
import com.kzurro.tablero.Tablero;

public class MinimaxIA {
	
	//VARIABLE INDICA LA PROFUNDIDAD MAXIMA QUE EL ALGORTIMO MiniMax ALCANZARA PARA UN JUGADOR
	private int maxProfundidad;
	//VARIABLE QUE INDICA QUE LETRA CONTROLA AL JUGADOR
	private int letra;
	
	public int getMaxProfundidad() {
		return maxProfundidad;
	}

	public void setMaxProfundidad(int maxDepth) {
		this.maxProfundidad = maxDepth;
	}

	public int getLetra() {
		return letra;
	}

	public void setLetra(int letra) {
		this.letra = letra;
	}

	//CONSTRUCTOR POR DEFECTO CON LOS VALORES PARA INICIAR
	public MinimaxIA() {
		maxProfundidad = 4;
		letra = Tablero.X;
	}
	
	public MinimaxIA(int maxProfundidad, int letra) {
		this.maxProfundidad = maxProfundidad;
		this.letra = letra;
	}
	
	//ALGORITMO -----MiniMax----- SIN PODA

	public Movimiento MiniMax(Tablero tablero) {
		// SI JUEGA EL JUGADOR 'X' QUIERE MAXIMIZAR LA HEURISTICA
        if (letra == Tablero.X) {
            return max(new Tablero(tablero), 0);
        }
        // SI JUEGA EL JUGADOR 'O' QUIERE MINIMIZAR LA HEURISTICA
        else {
            return min(new Tablero(tablero), 0);
        }
	}

	//SE VAN LLAMANDO LOS METODOS min  Y max ALTERNATIVAMENTE HASTA ALCANZAR LA PROFUNDIDAD NECESARIA 
	public Movimiento max(Tablero tablero, int profundidad) {
        Random aleatorio = new Random();

        /* SI SE LLAMA A LA FUNCION max EN UN ESTADO TERMINAL O DESPUES DE ALCANZAR LA MAXIMA PROFUNDIDAD, 
		*SE CALCULA UNA HEURÍSTICA SOBRE EL ESTADO Y EL MOVIMEINTO DEVUELTO
		*/
		if((tablero.checkGameOver()) || (profundidad == maxProfundidad))
		{
			Movimiento ultimoMovimiento = new Movimiento(tablero.getUltimoMovimiento().getFila(), tablero.getUltimoMovimiento().getColumna(), tablero.evaluate());
			return ultimoMovimiento;
		}
        //CALCULAR EL ESTADO DE LOS HIJOS
		ArrayList<Tablero> children = new ArrayList<Tablero>(tablero.getChildren(Tablero.X));
		Movimiento maxMovimiento = new Movimiento(Integer.MIN_VALUE);
		for (Tablero child : children) {
            //PARA CADA HIJO SE LLAMA AL METODO min A MENOR PROFUNDIDAD
			Movimiento movimiento = min(child, profundidad + 1);
            //EL MOVIMIENTO CON EL MAYOR ES SELECCINADO Y DEVUELTO POR MAX
			if(movimiento.getValor() >= maxMovimiento.getValor()) {
                if ((movimiento.getValor() == maxMovimiento.getValor())) {
                    //SI LA HEURISTICA TIENE EL MISMO VALOR SE ESCOGE UNO ALEATORIAMENTE DE LOS DOS MOVIENTOS
                    if (aleatorio.nextInt(2) == 0) {
                        maxMovimiento.setFila(child.getUltimoMovimiento().getFila());
                        maxMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                        maxMovimiento.setValor(movimiento.getValor());
                    }
                }
                else {
                    maxMovimiento.setFila(child.getUltimoMovimiento().getFila());
                    maxMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                    maxMovimiento.setValor(movimiento.getValor());
                }
			}
		}
		return maxMovimiento;
	}

	//PARA min DE FORMA SIMILAR HA LA QUE SE HA HECHO CON max
	public Movimiento min(Tablero tablero, int profundidad) {
        Random aleatorio = new Random();

		if((tablero.checkGameOver()) || (profundidad == maxProfundidad)) {
			Movimiento ultimoMovimiento = new Movimiento(tablero.getUltimoMovimiento().getFila(), tablero.getUltimoMovimiento().getColumna(), tablero.evaluate());
			return ultimoMovimiento;
		}
		ArrayList<Tablero> children = new ArrayList<Tablero>(tablero.getChildren(Tablero.O));
		Movimiento minMovimiento = new Movimiento(Integer.MAX_VALUE);
		for (Tablero child : children) {
			Movimiento movimiento = max(child, profundidad + 1);
			if(movimiento.getValor() <= minMovimiento.getValor()) {
                if ((movimiento.getValor() == minMovimiento.getValor())) {
                    if (aleatorio.nextInt(2) == 0) {
                        minMovimiento.setFila(child.getUltimoMovimiento().getFila());
                        minMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                        minMovimiento.setValor(movimiento.getValor());
                    }
                }
                else {
                        minMovimiento.setFila(child.getUltimoMovimiento().getFila());
                        minMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                        minMovimiento.setValor(movimiento.getValor());
                }
            }
        }
        return minMovimiento;
	}
	
	//ALGORITMO -----MiniMax----- ALFABETA ----------------CON PODA
	public Movimiento MiniMaxAlphaBeta(Tablero tablero) {
		// SI JUEGA EL JUGADOR 'X' QUIERE MAXIMIZAR LA HEURISTICA
        if (letra == Tablero.X) {
            return maxAlphaBeta(new Tablero(tablero), 0, Double.MIN_VALUE , Double.MAX_VALUE);
        }
        // SI JUEGA EL JUGADOR 'O' QUIERE MINIMIZAR LA HEURISTICA
        else {
            return minAlphaBeta(new Tablero(tablero), 0, Double.MIN_VALUE , Double.MAX_VALUE);
        }
	}

	//SE VAN LLAMANDO LOS METODOS min  Y max ALTERNATIVAMENTE HASTA ALCANZAR LA PROFUNDIDAD NECESARIA 
	public Movimiento maxAlphaBeta(Tablero tablero, int profundidad, double a, double b) {
        Random aleatorio = new Random();

        /* SI SE LLAMA A LA FUNCION max EN UN ESTADO TERMINAL O DESPUES DE ALCANZAR LA MAXIMA PROFUNDIDAD, 
		*SE CALCULA UNA HEURÍSTICA SOBRE EL ESTADO Y EL MOVIMEINTO DEVUELTO
		*/
		if((tablero.checkGameOver()) || (profundidad == maxProfundidad))
		{
			Movimiento ultimoMovimiento = new Movimiento(tablero.getUltimoMovimiento().getFila(), tablero.getUltimoMovimiento().getColumna(), tablero.evaluate());
			return ultimoMovimiento;
		}
        //CALCULAR EL ESTADO DE LOS HIJOS
		ArrayList<Tablero> children = new ArrayList<Tablero>(tablero.getChildren(Tablero.X));
		Movimiento maxMovimiento = new Movimiento(Integer.MIN_VALUE);
		for (Tablero child : children) {
			 //PARA CADA HIJO SE LLAMA AL METODO min A MENOR PROFUNDIDAD
			Movimiento movimiento = min(child, profundidad + 1);
			//EL MOVIMIENTO CON EL MAYOR ES SELECCIONADO Y DEVUELTO POR MAX
			if(movimiento.getValor() >= maxMovimiento.getValor()) {
                if ((movimiento.getValor() == maxMovimiento.getValor())) {
               	 //SI LA HEURISTICA TIENE EL MISMO VALOR SE ESCOGE UNO ALEATORIAMENTE DE LOS DOS MOVIENTOS
                    if (aleatorio.nextInt(2) == 0) {
                        maxMovimiento.setFila(child.getUltimoMovimiento().getFila());
                        maxMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                        maxMovimiento.setValor(movimiento.getValor());
                    }
                }
                else {
                    maxMovimiento.setFila(child.getUltimoMovimiento().getFila());
                    maxMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                    maxMovimiento.setValor(movimiento.getValor());
                }
			}
			
			// PODA Beta 
	        if (maxMovimiento.getValor() >= b) { 
	        	return maxMovimiento; 
	        }

	        // ACTALIZAR a DEL NODO MAX ACTUAL.
	        a = (a > maxMovimiento.getValor()) ? a : maxMovimiento.getValor();
		}
		
		return maxMovimiento;
	}

    //min DE FORMA SIMILAR A max
	public Movimiento minAlphaBeta(Tablero board, int depth, double a, double b) {
        Random aleatorio = new Random();

		if((board.checkGameOver()) || (depth == maxProfundidad)) {
			Movimiento ultimoMovimiento = new Movimiento(board.getUltimoMovimiento().getFila(), board.getUltimoMovimiento().getColumna(), board.evaluate());
			return ultimoMovimiento;
		}
		ArrayList<Tablero> children = new ArrayList<Tablero>(board.getChildren(Tablero.O));
		Movimiento minMovimiento = new Movimiento(Integer.MAX_VALUE);
		for (Tablero child : children) {
			Movimiento movimiento = max(child, depth + 1);
			if(movimiento.getValor() <= minMovimiento.getValor()) {
                if ((movimiento.getValor() == minMovimiento.getValor())) {
                    if (aleatorio.nextInt(2) == 0) {
                        minMovimiento.setFila(child.getUltimoMovimiento().getFila());
                        minMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
                        minMovimiento.setValor(movimiento.getValor());
                    }
                }
                else {
	                minMovimiento.setFila(child.getUltimoMovimiento().getFila());
	                minMovimiento.setColumna(child.getUltimoMovimiento().getColumna());
	                minMovimiento.setValor(movimiento.getValor());
                }
            }
			// PODA ALPHA
            if (minMovimiento.getValor() <= a) return minMovimiento;

            // ACTUALIZO b DEL NODO MIN ACTUAL .
            b = (b < minMovimiento.getValor()) ? b : minMovimiento.getValor();
        }
        return minMovimiento;
	}
		
}
