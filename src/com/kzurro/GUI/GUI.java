package com.kzurro.GUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.kzurro.IA.MinimaxIA;
import com.kzurro.settings.Preferencias;
import com.kzurro.settings.RecursosLoader;
import com.kzurro.tablero.Movimiento;
import com.kzurro.tablero.Parametros;
import com.kzurro.tablero.Tablero;


public class GUI {
	
	static Tablero tablero = new Tablero();
	static JFrame vemtanaPrincipalFrame;
	static JFrame gameOverFrame;
	
	static JPanel panelMain;
	static JPanel panelTableroNumeros;
	static JLayeredPane tableroLayered;
	
	static final int DEFAULT_WIDTH = 570;
	static final int DEFAULT_HEIGHT = 515;
	
	static boolean firstGame = true;

	static JButton boton_columna_1 = new JButton("1");
	static JButton boton_columna_2 = new JButton("2");
	static JButton boton_columna_3 = new JButton("3");
	static JButton boton_columna_4 = new JButton("4");
	static JButton boton_columna_5 = new JButton("5");
	static JButton boton_columna_6 = new JButton("6");
	static JButton boton_columna_7 = new JButton("7");

	static Parametros p_juego = new Parametros();
	static int modoJuego = p_juego.getModoJuego();
	static int maxProfundidad = p_juego.getMaxProfundidad();
	static String jugador1 = p_juego.getJugador1();
	static String jugador2 = p_juego.getJugador2();
	

	static MinimaxIA iA = new MinimaxIA(maxProfundidad, Tablero.X);
	
//	JUGADOR UNO LETRA -> X. JUGADOR 1 COMIENZA JUEGO
	//	JUGADOR 2 LETRA -> O.
	
	public static JLabel fichaLabel = null;
	
	// PARA OPERACIONES  Undo 
	public static int undoFilaHumano;
	public static int undoColumnaHumano;
	public static int letraHumano;
	public static JLabel undoCheckerLabelHumano;

	
	public GUI() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// AGREGAR LAS BARRA DE MENU Y ELEMENTOS A LA VENTANA.
	private static void AddMenus() {
		
		// BARRA DE MENU Y ITEMS
		JMenuBar menuBar;
		JMenu menu1;
		JMenuItem item11;
		JMenuItem item12;
		JMenuItem item13;
		JMenuItem item14;
		JMenu menu2;
		JMenuItem item21;
		JMenuItem item22;
		
		//ADD BARRA MENU
		menuBar = new JMenuBar();
		
		menu1 = new JMenu("Archivo");
		item11 = new JMenuItem("Nuevo Juego");
		item12 = new JMenuItem("Desacer   Ctrl+Z");
		item13 = new JMenuItem("Preferencias");
		item14 = new JMenuItem("Salir");
		menu1.add(item11);
		menu1.add(item12);
		menu1.add(item13);
		menu1.add(item14);
		
		menu2 = new JMenu("Help");
		item21 = new JMenuItem("Como se Juega");
		item22 = new JMenuItem("About");
		menu2.add(item21);
		menu2.add(item22);

		
		item11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearNuevoJuego();
			}
		});
		
		item12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		
		item13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferencias prefs = new Preferencias(p_juego);
				prefs.setVisible(true);
			}
		});
		
		item14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		item21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
			"Haga clic en los botones o presione 1-7 en el teclado para insertar una nueva ficha. \nPara ganar debe colocar 4 fichas seguidas, horizontal, vertical o diagonalmente .",
			"Como se Juega", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		item22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"© Created by: kzurro",
            "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		vemtanaPrincipalFrame.setJMenuBar(menuBar);
		// HACE EL TABLERO VISIBLE DESPUES DE AGREGAR LOS MENUS
		vemtanaPrincipalFrame.setVisible(true);
		
	}
	
	
	// TABLERO PRINCIPAL DEL 4 EN RAYA
	public static JLayeredPane crearLayeredTablero() {
		tableroLayered = new JLayeredPane();
		tableroLayered.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		tableroLayered.setBorder(BorderFactory.createTitledBorder(""));

		ImageIcon imagenTablero = new ImageIcon(RecursosLoader.load("images/tablero.gif"));
		JLabel imagenTableroLabel = new JLabel(imagenTablero);

		imagenTableroLabel.setBounds(20, 20, imagenTablero.getIconWidth(), imagenTablero.getIconHeight());
		tableroLayered.add(imagenTableroLabel, 0, 1);

		return tableroLayered;
	}
	
	
	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			String button = KeyEvent.getKeyText(e.getKeyCode());
			
			if (button.equals("1")) {
				mover(0);
			} else if (button.equals("2")) {
				mover(1);
			} else if (button.equals("3")) {
				mover(2);
			} else if (button.equals("4")) {
				mover(3);
			} else if (button.equals("5")) {
				mover(4);
			} else if (button.equals("6")) {
				mover(5);
			} else if (button.equals("7")) {
				mover(6);
			}
			
			else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                undo();
            }
			
			if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
					|| button.equals("5") || button.equals("6") || button.equals("7")) {
				if (!tablero.isOverflowOccured()) {
					juego();
					saveUndoMove();
					if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	};
	
	
	public static void undo() {
		// DESHACER IMPLEMENTACION PARA EL MODO HUMANO VS HUMANO
		if (p_juego.getModoJuego() == Parametros.humanoVSHumano) {
			try {
				tablero.setFinJuegor(false);
				enableBotones();
				if (vemtanaPrincipalFrame.getKeyListeners().length == 0) {
					vemtanaPrincipalFrame.addKeyListener(gameKeyListener);
				}
				tablero.undoMove(tablero.getUltimoMovimiento().getFila(), tablero.getUltimoMovimiento().getColumna(), letraHumano);
				tableroLayered.remove(fichaLabel);
				vemtanaPrincipalFrame.paint(vemtanaPrincipalFrame.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("Aún no se ha hecho ningún movimiento!");
				System.err.flush();
			}
		}
		
		// DESHACER IMPLEMENTACION PARA EL MODO HUMANO VS INTELIGENCIA ARTIFICIAL(IA)
		else if (p_juego.getModoJuego() == Parametros.humanoVSIa) {
			try {
				tablero.setFinJuegor(false);
				enableBotones();
				if (vemtanaPrincipalFrame.getKeyListeners().length == 0) {
					vemtanaPrincipalFrame.addKeyListener(gameKeyListener);
				}
				tablero.undoMove(tablero.getUltimoMovimiento().getFila(), tablero.getUltimoMovimiento().getColumna(), letraHumano);
				tableroLayered.remove(fichaLabel);
				tablero.undoMove(undoFilaHumano, undoColumnaHumano, letraHumano);
				tableroLayered.remove(undoCheckerLabelHumano);
				vemtanaPrincipalFrame.paint(vemtanaPrincipalFrame.getGraphics());
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("¡Aún no se ha hecho ningún movimiento!");
				System.err.flush();
			}
		}
	}
	
	
	//PARA CREAR UN NUEVO JUEGO POR PRIMERA VEZ O INICIAR UN NUEVO JUEGO 
	public static void crearNuevoJuego() {
		tablero = new Tablero();
		
		// OBTENER LO NUEVOS PARAMETROS MODIFICADOS
		modoJuego = p_juego.getModoJuego();
		maxProfundidad = p_juego.getMaxProfundidad();
		jugador1 = p_juego.getJugador1();
		jugador2 = p_juego.getJugador2();
		
		// SETTEAR LA NUEVA maximaProfundidad(DIFICULTAD)
		iA.setMaxProfundidad(maxProfundidad);
		
		if (vemtanaPrincipalFrame != null) vemtanaPrincipalFrame.dispose();
		vemtanaPrincipalFrame = new JFrame("4-en-Raya");
		// PARA QUE LA PANTALLA PRINCIPAL APAREZCA EN EL CENTRO
		centrarVentana(vemtanaPrincipalFrame, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Component componentesMainVentana = crearContenidoComponentes();
		vemtanaPrincipalFrame.getContentPane().add(componentesMainVentana, BorderLayout.CENTER);
		
		vemtanaPrincipalFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		vemtanaPrincipalFrame.addKeyListener(gameKeyListener);
		vemtanaPrincipalFrame.setFocusable(true);
		
		// MUESTRA LA VENTANA
		vemtanaPrincipalFrame.pack();
		// HACE EL TABLERO VISIBL ANTES DE CARGAR LA BARRA MEN
		if (modoJuego == Parametros.humanoVSIa)  {
			if (tablero.getUltimaJugada() == Tablero.X) {
				Movimiento iAMovimiento = iA.MiniMaxAlphaBeta(tablero);// modificar a minimax pruebas
				tablero.makeMovimiento(iAMovimiento.getColumna(), Tablero.O);
				juego();
			}
		}
		
		AddMenus();
		
	}
		
	
	// CENTRA LA VENTANA EN LA PANTALLA
	public static void centrarVentana(Window ventana, int ancho, int alto) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - ventana.getWidth()) / 2) - (ancho/2));
	    int y = (int) (((dimension.getHeight() - ventana.getHeight()) / 2) - (alto/2));
	    ventana.setLocation(x, y);
	}
	
	
	// BUSCA QUE JUAGADOR LE TOCA Y HACE MOVIMIENTO E
	public static void mover(int columna) {
		tablero.setOverflowOccured(false);
		
		int filaP = tablero.getUltimoMovimiento().getFila();
		int columnaP = tablero.getUltimoMovimiento().getColumna();
		int letraP = tablero.getUltimaJugada();
		
		if (tablero.getUltimaJugada() == Tablero.O) {
			tablero.makeMovimiento(columna, Tablero.X);
		} else {
			tablero.makeMovimiento(columna, Tablero.O);
		}
		
		if (tablero.isOverflowOccured()) {
			tablero.getUltimoMovimiento().setFila(filaP);
			tablero.getUltimoMovimiento().setColumna(columnaP);
			tablero.setUltimaJugada(letraP);
		}

	}
	
	
	// FICHA EN EL TABLERO
	public static void colocarFicha(String color, int fila, int columna) {
		int xOffset = 75 * columna;
		int yOffset = 75 * fila;
		ImageIcon ficha = new ImageIcon(RecursosLoader.load("images/" + color + ".gif"));
		fichaLabel = new JLabel(ficha);
		fichaLabel.setBounds(27 + xOffset, 27 + yOffset, ficha.getIconWidth(),ficha.getIconHeight());
		tableroLayered.add(fichaLabel, 0, 0);
		vemtanaPrincipalFrame.paint(vemtanaPrincipalFrame.getGraphics());
	}
	
	
	public static void saveUndoMove() {
		undoFilaHumano = tablero.getUltimoMovimiento().getFila();
		undoColumnaHumano = tablero.getUltimoMovimiento().getColumna();
		letraHumano = tablero.getUltimaJugada();
		undoCheckerLabelHumano = fichaLabel;
	}
	
	
	// SE LLAMA ESTE METODO DESPUES DE LLAMAR AL makeMovimeinto(columna);
	public static void juego() {	
		int fila = tablero.getUltimoMovimiento().getFila();
		int columna = tablero.getUltimoMovimiento().getColumna();
		int jugadorActual = tablero.getUltimaJugada();
		
		if (jugadorActual == Tablero.X) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha(jugador1, fila, columna);
		}
			
		if (jugadorActual == Tablero.O) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha(jugador2, fila, columna);
		}
		
		if (tablero.checkGameOver()) {
			gameOver();
		}
		tablero.print();
		System.out.println("\n*****************************");
	}
	
	
	//RECIBE LA LLAMADA DESUÉS DE QUE EL  HUMANO REALIZA SU MOVIMIENTO. SE HACE EL MOVIMENTO DE LA IA (minimax)		// Gets called after the human player makes a move. It makes an minimax AI move.
	public static void iAMovimiento(){
		if (!tablero.isFinJuego()) {
			// COMPRUEBAR SI EL HUMANO HA SIDO EL ULTIMO EN JUGAR
			if (tablero.getUltimaJugada() == Tablero.X) {
				Movimiento iAMovimiento = iA.MiniMaxAlphaBeta(tablero);
				tablero.makeMovimiento(iAMovimiento.getColumna(), Tablero.O);
				juego();
			}
		}

	}
	
	
	public static void enableBotones() {
		boton_columna_1.setEnabled(true);
		boton_columna_2.setEnabled(true);
		boton_columna_3.setEnabled(true);
		boton_columna_4.setEnabled(true);
		boton_columna_5.setEnabled(true);
		boton_columna_6.setEnabled(true);
		boton_columna_7.setEnabled(true);
	}
	
	
	public static void disableButones() {
		boton_columna_1.setEnabled(false);
		boton_columna_2.setEnabled(false);
		boton_columna_3.setEnabled(false);
		boton_columna_4.setEnabled(false);
		boton_columna_5.setEnabled(false);
		boton_columna_6.setEnabled(false);
		boton_columna_7.setEnabled(false);
	}
	
	
	/**
	 * 	DEVUELVE UN COMPONENTE PARA SE DIBUJADO POR LA VENTANA PRINCIPAL.
	 * ESTE METODO CREA LOS COMPONENTES DE LA PANTALLA PRINCIPAL
	 * CUANDO SE HACE CLICK SE LLAMA AL METODO actionListener()
	 */
	public static Component crearContenidoComponentes() {	
		// CREAR U SETEAR BOTONES
		panelTableroNumeros = new JPanel();
		panelTableroNumeros.setLayout(new GridLayout(1, 7, 6, 4));
		panelTableroNumeros.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		enableBotones();
		
		if (firstGame) {
		
			boton_columna_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					mover(0);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(1);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(2);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(3);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(4);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(5);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}
			});
			
			boton_columna_7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mover(6);
					if (!tablero.isOverflowOccured()) {
						juego();
						saveUndoMove();
						if (modoJuego == Parametros.humanoVSIa) iAMovimiento();
					}
					vemtanaPrincipalFrame.requestFocusInWindow();
				}

			});
				

			firstGame = false;
		}
		
		panelTableroNumeros.add(boton_columna_1);
		panelTableroNumeros.add(boton_columna_2);
		panelTableroNumeros.add(boton_columna_3);
		panelTableroNumeros.add(boton_columna_4);
		panelTableroNumeros.add(boton_columna_5);
		panelTableroNumeros.add(boton_columna_6);
		panelTableroNumeros.add(boton_columna_7);

		// CREACION DEL TABLERO DEL 4 EN RAYA
		tableroLayered = crearLayeredTablero();

		// PARA ALMACENAR TODOS LOS COMPONENTES DEL TABLERO
		panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// ANADIR BOTON Y COMPONENTES AL TABLERO
		panelMain.add(panelTableroNumeros, BorderLayout.NORTH);
		panelMain.add(tableroLayered, BorderLayout.CENTER);

		vemtanaPrincipalFrame.setResizable(false);
		return panelMain;
	}
	
	
	public static void gameOver() {
		tablero.setFinJuegor(true);

		int elige = 0;
		tablero.checkEstadoGanador();
		
		if (tablero.getGanador() == Tablero.X) {
			if (modoJuego == Parametros.humanoVSIa)
				elige = JOptionPane.showConfirmDialog(null, "¡Ganaste! ¿Quieres jugar otra partida?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (modoJuego == Parametros.humanoVSHumano)
				elige = JOptionPane.showConfirmDialog(null, "¡Ganaste Jugador 1 ! ¿Quieres jugar otra partida?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else if (tablero.getGanador() == Tablero.O) {
			if (modoJuego == Parametros.humanoVSIa)
				elige = JOptionPane.showConfirmDialog(null, "¡Gana la Inteligencia Artificial! ¿Quieres jugar otra partida?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
			else if (modoJuego == Parametros.humanoVSHumano)
				elige = JOptionPane.showConfirmDialog(null, "¡Ganaste Jugador 2 ! ¿Quieres jugar otra partida?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		} else {
			elige = JOptionPane.showConfirmDialog(null, "¡Tablas! ¿Quieres jugar otra partida?" ,"GAME OVER", JOptionPane.YES_NO_OPTION);
		}
		
		if (elige == JOptionPane.YES_OPTION) {
			crearNuevoJuego();
		} else {
			//DESHABILITAR LOS BOTONES
			disableButones();
			
			//QUITAR EL KEY LISTENER
			vemtanaPrincipalFrame.removeKeyListener(vemtanaPrincipalFrame.getKeyListeners()[0]);
		}

	}
	
	
//	@SuppressWarnings("static-access")
//	public static void main(String[] args){
//		GUI cuatroEnRaya = new GUI();
//		cuatroEnRaya.crearNuevoJuego();
//	}
	
	
}
