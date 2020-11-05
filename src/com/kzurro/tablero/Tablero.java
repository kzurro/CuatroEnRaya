package com.kzurro.tablero;

import java.util.ArrayList;

public class Tablero {
	
	// VALORES PARA EL TABLERO
	public static final int X = 1; // JUGADOR 1
	public static final int O = -1; // JUGADOR 2
	public static final int EMPTY = 0;
	
	//ULTIMO MOVIMIENTO DE TABLERO 
    private Movimiento ultimoMovimeinto;
    
	//UNA VARIABLE PARA ALMACENAR EL JUGADOR QUE MOVIO POR ULTIMA VEZ, 
    //LLEVANDO EL ESTADO ACTUAL DEL TABLERO
    private int ultimaJugada;
    private int ganador;
	private int [][] tableroJuego;
	private boolean overflowOccured = false;	
	private boolean finJuego;
	
	
	
	
	
	public Movimiento getUltimoMovimiento() {
		return ultimoMovimeinto;
	}
	
	public int getUltimaJugada() {
		return ultimaJugada;
	}
	
	public int[][] getTableroJuego() {
		return tableroJuego;
	}
	
	public int getGanador() {
		return ganador;
	}
	
	public void setUltimoMovimiento(Movimiento ultimoMovimiento) {
		this.ultimoMovimeinto.setFila(ultimoMovimiento.getFila());
		this.ultimoMovimeinto.setColumna(ultimoMovimiento.getColumna());
		this.ultimoMovimeinto.setValor(ultimoMovimiento.getValor());
	}
	
	public void setUltimaJugada(int ultimaJugada) {
		this.ultimaJugada = ultimaJugada;
	}
	
	public void setTableroJuego(int[][] tableroJuego) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				this.tableroJuego[i][j] = tableroJuego[i][j];
			}
		}
	}
	
	public void setGanador(int ganador) {
		this.ganador = ganador;
	}
	
	public boolean isFinJuego() {
		return finJuego;
	}

	public void setFinJuegor(boolean isGameOver) {
		this.finJuego = isGameOver;
	}
	
	public boolean isOverflowOccured() {
		return overflowOccured;
	}
	
	public void setOverflowOccured(boolean overflowOccured) {
		this.overflowOccured = overflowOccured;
	}
	
	// CONSTRUCTOR
		public Tablero() {
			ultimoMovimeinto = new Movimiento();
			ultimaJugada = O;
			ganador = 0;
			tableroJuego = new int[6][7];
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					tableroJuego[i][j] = EMPTY;
				}
			}
		}
		
		
		// CLONAR TABLERO
		public Tablero(Tablero tablero) {
			ultimoMovimeinto = tablero.ultimoMovimeinto;
			ultimaJugada = tablero.ultimaJugada;
			ganador = tablero.ganador;
			tableroJuego = new int[6][7];
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					tableroJuego[i][j] = tablero.tableroJuego[i][j];
				}
			}
		}

	//REALIZA UN MOVIMEINTO BASADO EN LA LA COLUMNA PASADA POR PARAMETROS.
	//METE AUTOMATICAMENTE EN QUE FILA DEBE IR EL 
	public void makeMovimiento(int columna, int letra) {
		try {
			//LA VARIABLE ultimoMovimiento DEBE CAMBIAR ANTES QUE tableroJuego[][] 
			//POR LA FUNCION getPosicionFila(columna)
			this.ultimoMovimeinto = new Movimiento(getPosicionFila(columna), columna);
			this.ultimaJugada = letra;
			this.tableroJuego[getPosicionFila(columna)][columna] = letra;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Column " + (columna+1) + " is full!");
			setOverflowOccured(true);
		}
	}
	
	
	//FILA COLUMNA ESPECIFICADA ESTA VACIA
	public void undoMove(int fila, int columno, int letra) {
		this.tableroJuego[fila][columno] = 0;
		if (letra == O) {
			this.ultimaJugada = X;
		} else if (letra == X) {
			this.ultimaJugada = O;
		}
	}
	
	
	//BUSCAR DENTRO DE LOS LIMITES DE TODO EL TABLERO
	public boolean canMovimiento(int fila, int columna) {
		if ((fila <= -1) || (columna <= -1) || (fila > 5) || (columna > 6)) {
			return false;
		}
		return true;
	}
	
	
	public boolean checkColumnaLlena(int columna) {
		if (tableroJuego[0][columna] == EMPTY)
			return false;
		return true;
	}
	
	
	// DEVUELVE LA POSICION DE LA ULTIMA FILA VACIA EN UNA COLUMNA.
	public int getPosicionFila(int columna) {
		int posicionFila = -1;
		for (int fila = 0; fila < 6; fila++) {
			if (tableroJuego[fila][columna] == EMPTY) {
				posicionFila = fila;
			}
		}
		return posicionFila;
	}
	
	
	/* GENERA LOS HIJOS DEL ESTADO, COMO MAXIMO
	 *  LOS ESTADO GENERADOS SERÁN 7 POR EL NUMERO DE 
	 *  COLUMNAS QUE TENEMOS
	 */
	public ArrayList<Tablero> getChildren(int letra) {
		ArrayList<Tablero> children = new ArrayList<Tablero>();
		for(int columna = 0; columna < 7; columna++) {
			if(!checkColumnaLlena(columna)) {
				Tablero child = new Tablero(this);
				child.makeMovimiento(columna, letra);
				children.add(child);
			}
		}
		return children;
	}
	
	
	public int evaluate() {
		// +100  GANA 'X', -100  GANA 'O'
		// +100 'X' GABA, -100 'O' GANA,
		// +10 POR CADA TRES 'X' EN UNA FILA, -10 POR CADA TRES 'O' EN UNA FILA,
		// +1 POR CADA DOS 'X' EN UNA FILA, -1 POR CADA DOS 'O' EN UNA FILA
		int Xlines = 0;
		int Olines = 0;

        if (checkEstadoGanador()) {
			if(getGanador() == X) {
				Xlines = Xlines + 100;
			} else if (getGanador() == O) {
				Olines = Olines + 100;
			}
		}
		
        Xlines  = Xlines + check3EnRaya(X)*10 + check2EnRaya(X);
        Olines  = Olines + check3EnRaya(O)*10 + check2EnRaya(O);
		
        // RESULTADO TABLAS SI  DEVUELVE 0
		return Xlines - Olines;
	}
	
	
	

	public boolean checkEstadoGanador() {
		//COMPROBAR QUE HAY 4 EN RAYA EN UNA FILA (HORIZONTAL).
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				if (tableroJuego[i][j] == tableroJuego[i][j+1]
						&& tableroJuego[i][j] == tableroJuego[i][j+2]
						&& tableroJuego[i][j] == tableroJuego[i][j+3]
						&& tableroJuego[i][j] != EMPTY) {
					setGanador(tableroJuego[i][j]);
					return true;
				}
			}
		}
		
		//COMPROBAR QUE HAY 4 EN RAYA EN UNA COLUMNA (VERTICAL)
		for (int i = 5; i >= 3; i--) {
			for (int j = 0; j < 7; j++) {
				if (tableroJuego[i][j] == tableroJuego[i-1][j]
						&& tableroJuego[i][j] == tableroJuego[i-2][j]
						&& tableroJuego[i][j] == tableroJuego[i-3][j]
						&& tableroJuego[i][j] != EMPTY) {
					setGanador(tableroJuego[i][j]);
					return true;
				}
			}
		}
		
		//COMPROBAR 4 EN RAYA EN DIAGONALES DESCENDENTES
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (tableroJuego[i][j] == tableroJuego[i+1][j+1]
						&& tableroJuego[i][j] == tableroJuego[i+2][j+2]
						&& tableroJuego[i][j] == tableroJuego[i+3][j+3] 
						&& tableroJuego[i][j] != EMPTY) {
					setGanador(tableroJuego[i][j]);
					return true;
				}
			}
		}
		
		//COMPROBAR 4 EN RAYA EN DIAGONALES ASCNDENTES
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i-3,j+3)) {
					if (tableroJuego[i][j] == tableroJuego[i-1][j+1]
							&& tableroJuego[i][j] == tableroJuego[i-2][j+2]
							&& tableroJuego[i][j] == tableroJuego[i-3][j+3] 
							&& tableroJuego[i][j] != EMPTY) {
						setGanador(tableroJuego[i][j]);
						return true;
					}
				}
			}
		}
		
		setGanador(0); //TABLAS
		return false;

	}
	
    public boolean checkGameOver() {
    	// COMPROBAR SI HAY GANADOR
    	if (checkEstadoGanador()) {
    		return true;
    	}
    	
    	// COMPRUEBA SI HAY UNA FILA/COLUMNA(CELDA) VACIA, ES DECIR, CORUEBA SI HAY TABLAS(EMPATE)
    	//SI TODAS LAS CELDAS ESTAN LLENAS NADIA HA GANADO (EMPATE)
    	for(int fila = 0; fila < 6; fila++) {
			for(int columna = 0; columna < 7; columna++) {
				if(tableroJuego[fila][columna] == EMPTY) {
                    return false;
                }
            }
        }
    	
    	return true;
    }
	
	//DEVUELVE FRECUENCIA DE 3 EN RAYA PARA EL JUGADOR QUE PASO POR PARAMETRO
	public int check3EnRaya(int jugador) {
		
		int frecuencia = 0;
		
		// COMPROBAR 3 EN RAYA EN UNA FILA (HORIZONTAL).
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i, j + 2)) {
					if (tableroJuego[i][j] == tableroJuego[i][j + 1]
							&& tableroJuego[i][j] == tableroJuego[i][j + 2]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		// COMPROBAR 3 EN RAYA EN UNA COLUMNA (VERTICAL).
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i - 2, j)) {
					if (tableroJuego[i][j] == tableroJuego[i - 1][j]
							&& tableroJuego[i][j] == tableroJuego[i - 2][j]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		//COMPROBAR 3 EN RAYA EN DIAGONALES DESCENDENTES
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i + 2, j + 2)) {
					if (tableroJuego[i][j] == tableroJuego[i + 1][j + 1]
							&& tableroJuego[i][j] == tableroJuego[i + 2][j + 2]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		//COMPROBAR 3 EN RAYA EN DIAGONALES ASCENDENTES
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i - 2, j + 2)) {
					if (tableroJuego[i][j] == tableroJuego[i - 1][j + 1]
							&& tableroJuego[i][j] == tableroJuego[i - 2][j + 2]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		return frecuencia;
				
	}
	
	//DEVUELVE FRECUENCIA DE 2 EN RAYA PARA EL JUGADOR QUE PASO POR PARAMETRO

	public int check2EnRaya(int jugador) {
		
		int frecuencia = 0;
		
		// COMPROBAR 2 EN RAYA EN UNA FILA (HORIZONTAL).	
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i, j + 1)) {
					if (tableroJuego[i][j] == tableroJuego[i][j + 1]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		// COMPROBAR 2 EN RAYA EN UNA COLUMNA (VERTICAL).
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i - 1, j)) {
					if (tableroJuego[i][j] == tableroJuego[i - 1][j]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		//COMPROBAR 2 EN RAYA EN DIAGONALES DESCENDENTES
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i + 1, j + 1)) {
					if (tableroJuego[i][j] == tableroJuego[i + 1][j + 1]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		///COMPROBAR 2 EN RAYA EN DIAGONALES ASCENDENTE
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (canMovimiento(i - 1, j + 1)) {
					if (tableroJuego[i][j] == tableroJuego[i - 1][j + 1]
							&& tableroJuego[i][j] == jugador) {
						frecuencia++;
					}
				}
			}
		}

		return frecuencia;
				
	}

    // PARA PINTAR EL TABLERO POR CONSOLA
  	public void print() {
  		
  		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
  		System.out.println();
  		for (int i=0; i<6; i++) {
  			for (int j=0; j<7; j++) {
  				if (j!=6) {
  					if (tableroJuego[i][j] == 1) {
  						System.out.print("| " + "X" + " ");
  					} else if (tableroJuego[i][j] == -1) {
  						System.out.print("| " + "O" + " ");
  					} else {
  						System.out.print("| " + "-" + " ");
  					}
  				} else {
  					if (tableroJuego[i][j] == 1) {
  						System.out.println("| " + "X" + " |");
  					} else if (tableroJuego[i][j] == -1) {
  						System.out.println("| " + "O" + " |");
  					} else {
  						System.out.println("| " + "-" + " |");
  					}
  				}
  			}
  		}
  		
  	}


}
