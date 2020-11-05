package com.kzurro.GUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.kzurro.IA.MinimaxIA;
import com.kzurro.settings.RecursosLoader;
import com.kzurro.tablero.Movimiento;
import com.kzurro.tablero.Parametros;
import com.kzurro.tablero.Tablero;


public class GUIHumanoVSAi {
	
	static Tablero tablero = new Tablero();
	static JFrame vemtanaPrincipalFrame;
	static JFrame gameOverFrame;
	
	static JPanel panelTableroNumeros;
	static JLayeredPane tableroLayered;
	
	//BARRA MENU E ITEMS
	static JMenuBar menuBar;
	static JMenu menu1;
	static JMenuItem item11;
	static JMenuItem item12;
	static JMenuItem item13;
	static JMenu menu2;
	static JMenuItem item21;
	static JMenuItem item22;

	static Parametros parametros = new Parametros();
	static int maxProfundidad = parametros.getMaxProfundidad();
	static String jugador1 = parametros.getJugador1();
	static String jugador2 = parametros.getJugador2();
	

	static MinimaxIA iA = new MinimaxIA(maxProfundidad, Tablero.X);
	
	//	HUMANO -> 'X' PRIMERO EN JUGAR
	//	 AI  -> 'O'
	
	public GUIHumanoVSAi() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		crearNuevoJuego();
	}
		
	// TABLERO DE JUEGO PRINCIPAL  DEL 4 EN RAYA
	public static JLayeredPane crearLayeredTablero() {
		tableroLayered = new JLayeredPane();
		tableroLayered.setPreferredSize(new Dimension(570, 490));
		tableroLayered.setBorder(BorderFactory.createTitledBorder(""));//titulo subpanel

		ImageIcon imagenTablero = new ImageIcon(RecursosLoader.load("images/tablero.gif"));
		JLabel imagenTableroLabel = new JLabel(imagenTablero);

		imagenTableroLabel.setBounds(20, 20, imagenTablero.getIconWidth(), imagenTablero.getIconHeight());
		tableroLayered.add(imagenTableroLabel, 0, 1);

		return tableroLayered;
	}
	
	//PARA CREAR UN NUEVO JUEGO POR PRIMERA VEZ O INICIAR UN NUEVO JUEGO 

	public static void crearNuevoJuego() {
		tablero = new Tablero();
		
		// PARA ESTABLECER LA PROFUNDIDAD QUE MARCA LA DIFICULTAD DEL JUEGO
		iA.setMaxProfundidad(maxProfundidad);
             
		if (vemtanaPrincipalFrame != null) vemtanaPrincipalFrame.dispose();
		vemtanaPrincipalFrame = new JFrame("4-en-Raya");//panel principal
		// PARA QUE LA PANTALLA PRINCIPAL APAREZCA EN EL CENTRO
		centrarVentana(vemtanaPrincipalFrame, 570, 490);
		Component componentesMainVentana = crearContenidoComponentes();
		vemtanaPrincipalFrame.getContentPane().add(componentesMainVentana, BorderLayout.CENTER);

		vemtanaPrincipalFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		
		vemtanaPrincipalFrame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				String boton = KeyEvent.getKeyText(e.getKeyCode());
				
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();

				if (boton.equals("1")) {
					tablero.makeMovimiento(0, Tablero.X);
				} else if (boton.equals("2")) {
					tablero.makeMovimiento(1, Tablero.X);
				} else if (boton.equals("3")) {
					tablero.makeMovimiento(2, Tablero.X);
				} else if (boton.equals("4")) {
					tablero.makeMovimiento(3, Tablero.X);
				} else if (boton.equals("5")) {
					tablero.makeMovimiento(4, Tablero.X);
				} else if (boton.equals("6")) {
					tablero.makeMovimiento(5, Tablero.X);
				} else if (boton.equals("7")) {
					tablero.makeMovimiento(6, Tablero.X);
				}
				
				if (boton.equals("1") || boton.equals("2") || boton.equals("3") || boton.equals("4")
						|| boton.equals("5") || boton.equals("6") || boton.equals("7")) {
					if (!tablero.isOverflowOccured()) {
						juego();
						iAMovimiento();
					} else {
						tablero.getUltimoMovimiento().setFila(filaP);
						tablero.getUltimoMovimiento().setColumna(columnaP);
						tablero.setUltimaJugada(letraP);
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		vemtanaPrincipalFrame.setFocusable(true);
		
		// MOSTRAR VENTANA
		vemtanaPrincipalFrame.pack();
		vemtanaPrincipalFrame.setVisible(true);

		if (tablero.getUltimaJugada() == Tablero.X) {
			Movimiento iAMovimiento = iA.MiniMaxAlphaBeta(tablero);
			tablero.makeMovimiento(iAMovimiento.getColumna(), Tablero.O);
			juego();
		}

	}
	
	
	// CENTRA LA VENTANA EN LA PANTALLA
	public static void centrarVentana(Window ventana, int ancho, int alto) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - ventana.getWidth()) / 2) - (ancho/2));
	    int y = (int) (((dimension.getHeight() - ventana.getHeight()) / 2) - (alto/2));
	    ventana.setLocation(x, y);
	}
	
	
	public static void colocarFicha(String color, int fila, int columna) {
		int xOffset = 75 * columna;
		int yOffset = 75 * fila;
		ImageIcon ficha = new ImageIcon(RecursosLoader.load("images/" + color + ".gif"));
		JLabel fichaLabel = new JLabel(ficha);
		fichaLabel.setBounds(27 + xOffset, 27 + yOffset, ficha.getIconWidth(),ficha.getIconHeight());
		tableroLayered.add(fichaLabel, 0, 0);
		vemtanaPrincipalFrame.paint(vemtanaPrincipalFrame.getGraphics());
	}
	
	// SE LLAMA ESTE METODO DESPUES DE LLAMAR AL makeMovimeinto(int columna);
	public static void juego() {
		int fila = tablero.getUltimoMovimiento().getFila();
		int columna = tablero.getUltimoMovimiento().getColumna();
		int jugadorActual = tablero.getUltimaJugada();
		
		if (jugadorActual == Tablero.X) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha(parametros.getJugador1(), fila, columna);
		}
		
		if (jugadorActual == Tablero.O) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha(parametros.getJugador2(), fila, columna);
		}
		
		if (tablero.checkGameOver()) {
			tablero.setFinJuegor(true);
			gameOver();
		}
		

	}
	
	// RECIBE LA LLAMADA DESUÉS DE QUE EL JUGADOR  REALIZA SU MOVIMIENTO. SE HACE EL MOVIMENTO DE LA IA (minimax)	
	public static void iAMovimiento(){
		if (!tablero.isFinJuego()) {
			// check if human player played last
			if (tablero.getUltimaJugada() == Tablero.X) {
				Movimiento iAMovimiento = iA.MiniMaxAlphaBeta(tablero);
				tablero.makeMovimiento(iAMovimiento.getColumna(), Tablero.O);
				juego();
			}
		}

	}
	
	/**
	 * 	DEVUELVE UN COMPONENTE PARA SE DIBUJADO POR LA VENTANA PRINCIPAL.
	 * ESTE METODO CREA LOS COMPONENTES DE LA PANTALLA PRINCIPAL
	 * CUANDO SE HACE CLICK SE LLAMA AL METODO actionListener()
	 */
	public static Component crearContenidoComponentes() {
		//CREA UN PANEL PARA CONFIGURAR LOS BOTONES DEL TABLERO
		panelTableroNumeros = new JPanel();
		panelTableroNumeros.setLayout(new GridLayout(1, 7, 6, 4));
		panelTableroNumeros.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		JButton boton_columna1 = new JButton("1");
		boton_columna1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(0, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna2 = new JButton("2");
		boton_columna2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(1, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna3 = new JButton("3");
		boton_columna3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(2, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna4 = new JButton("4");
		boton_columna4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(3, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna5 = new JButton("5");
		boton_columna5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(4, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna6 = new JButton("6");
		boton_columna6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(5, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna7 = new JButton("7");
		boton_columna7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablero.setOverflowOccured(false);
				int filaP = tablero.getUltimoMovimiento().getFila();
				int columnaP = tablero.getUltimoMovimiento().getColumna();
				int letraP = tablero.getUltimaJugada();
				tablero.makeMovimiento(6, Tablero.X);
				if (!tablero.isOverflowOccured()) {
					juego();
					iAMovimiento();
				} else {
					tablero.getUltimoMovimiento().setFila(filaP);
					tablero.getUltimoMovimiento().setColumna(columnaP);
					tablero.setUltimaJugada(letraP);
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		panelTableroNumeros.add(boton_columna1);
		panelTableroNumeros.add(boton_columna2);
		panelTableroNumeros.add(boton_columna3);
		panelTableroNumeros.add(boton_columna4);
		panelTableroNumeros.add(boton_columna5);
		panelTableroNumeros.add(boton_columna6);
		panelTableroNumeros.add(boton_columna7);

		// CREACION DEL TABLERO DEL 4 EN RAYA
		tableroLayered = crearLayeredTablero();

		// PARA ALMACENAR TODOS LOS COMPONENTES DEL TABLERO
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// ADD BOTON Y COMPONENTES AL TABLERO
		panelMain.add(panelTableroNumeros, BorderLayout.NORTH);
		panelMain.add(tableroLayered, BorderLayout.CENTER);

		vemtanaPrincipalFrame.setResizable(false);
		return panelMain;
	}
	
	public static void gameOver() {
		gameOverFrame = new JFrame("Game over!");
		gameOverFrame.setBounds(620, 400, 350, 128);
		centrarVentana(gameOverFrame, 0, 0);
		JPanel ganarPanel = new JPanel(new BorderLayout());
		ganarPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		
		JLabel ganarLabel;
		tablero.checkEstadoGanador();
		if (tablero.getGanador() == Tablero.X) {
			ganarLabel = new JLabel("¡Ganaste! ¿Quieres jugar otra partida?");
			ganarPanel.add(ganarLabel);
		} else if (tablero.getGanador() == Tablero.O) {
			ganarLabel = new JLabel("¡Gana la Inteligencia Artificial! ¿Quieres jugar otra partida?");
			ganarPanel.add(ganarLabel);
		} else {
			ganarLabel = new JLabel("¡Tablas! ¿Quieres jugar otra partida?");
			ganarPanel.add(ganarLabel);
		}
		ganarLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		ganarPanel.add(ganarLabel, BorderLayout.NORTH);
		
		JButton botonSi = new JButton("Si");
		botonSi.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		botonSi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOverFrame.setVisible(false);
				crearNuevoJuego();
			}
		});
		
		JButton botonSalir = new JButton("Salir");
		botonSalir.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOverFrame.setVisible(false);
				System.exit(0);
			}
		});
		
		JPanel subPanel = new JPanel();
		subPanel.add(botonSi);
		subPanel.add(botonSalir);
		
		ganarPanel.add(subPanel, BorderLayout.CENTER);
		gameOverFrame.getContentPane().add(ganarPanel, BorderLayout.CENTER);
		gameOverFrame.setResizable(false);
		gameOverFrame.setVisible(true);
	}
	
//	@SuppressWarnings("static-access")
//	public static void main(String[] args){
//		GUIHumanoVSAi cuatroEnRaya = new GUIHumanoVSAi();
//		cuatroEnRaya.crearNuevoJuego();
//	}
	
}
