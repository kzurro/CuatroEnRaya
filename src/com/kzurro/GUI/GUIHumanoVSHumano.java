package com.kzurro.GUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.kzurro.settings.RecursosLoader;
import com.kzurro.tablero.Tablero;

public class GUIHumanoVSHumano {
	
	static Tablero tablero = new Tablero();
	static JFrame vemtanaPrincipalFrame;
	static JFrame frameGameOver;
	
	static JPanel panelTableroNumeros;
	static JLayeredPane tableroLayered;
	
//	JUGADOR UNO LETRA -> X. JUGADOR 1 COMIENZA JUEGO
	//	JUGADOR 2 LETRA -> O.
	
	public GUIHumanoVSHumano() {
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
		tableroLayered.setBorder(BorderFactory.createTitledBorder(""));//subpanel

		ImageIcon imagenTablero = new ImageIcon(RecursosLoader.load("images/tablero.gif"));
		JLabel imagenTableroLabel = new JLabel(imagenTablero);

		imagenTableroLabel.setBounds(20, 20, imagenTablero.getIconWidth(), imagenTablero.getIconHeight());
		tableroLayered.add(imagenTableroLabel, 0, 1);

		return tableroLayered;
	}
	
	//PARA CREAR UN NUEVO JUEGO POR PRIMERA VEZ O INICIAR UN NUEVO JUEGO 
	public static void crearNuevoJuego() {
		tablero = new Tablero();   
		if (vemtanaPrincipalFrame != null) vemtanaPrincipalFrame.dispose();
		vemtanaPrincipalFrame = new JFrame("4-en-Raya");
		// PARA QUE LA PANTALLA PRINCIPAL APAREZCA EN EL CENTRO
		centrarVentana(vemtanaPrincipalFrame, 570, 490);
		Component componentesMainVentana = createContenidoComponentes();
		vemtanaPrincipalFrame.getContentPane().add(componentesMainVentana, BorderLayout.CENTER);
		
		vemtanaPrincipalFrame.addWindowListener(new WindowAdapter() {
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
				
				if (boton.equals("1")) {
					mover(0);
				} else if (boton.equals("2")) {
					mover(1);
				} else if (boton.equals("3")) {
					mover(2);
				} else if (boton.equals("4")) {
					mover(3);
				} else if (boton.equals("5")) {
					mover(4);
				} else if (boton.equals("6")) {
					mover(5);
				} else if (boton.equals("7")) {
					mover(6);
				}
				
				if (boton.equals("1") || boton.equals("2") || boton.equals("3") || boton.equals("4")
						|| boton.equals("5") || boton.equals("6") || boton.equals("7")) {
					if (!tablero.isOverflowOccured()) {
						juego();
					} else {
						tablero.setOverflowOccured(false);
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

	}
	
	// CENTRA LA VENTANA EN LA PANTALLA
	public static void centrarVentana(Window ventana, int ancho, int alto) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - ventana.getWidth()) / 2) - (ancho/2));
	    int y = (int) (((dimension.getHeight() - ventana.getHeight()) / 2) - (alto/2));
	    ventana.setLocation(x, y);
	}
	
	// BUSCAR QUE JUGADOR LE TOCA Y REALIZA MOVIMIENTO
	public static void mover(int columna) {
		tablero.setOverflowOccured(false);
		
		int previousRow = tablero.getUltimoMovimiento().getFila();
		int previousCol = tablero.getUltimoMovimiento().getColumna();
		int previousLetter = tablero.getUltimaJugada();
		
		if (tablero.getUltimaJugada() == Tablero.O) {
			tablero.makeMovimiento(columna, Tablero.X);
		} else {
			tablero.makeMovimiento(columna, Tablero.O);
		}
		
		if (tablero.isOverflowOccured()) {
			tablero.getUltimoMovimiento().setFila(previousRow);
			tablero.getUltimoMovimiento().setColumna(previousCol);
			tablero.setUltimaJugada(previousLetter);
		}
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
	
	// SE LLAMA ESTE METODO DESPUES DE LLAMAR AL makeMovimeinto(columna);
	public static void juego() {
	
		int fila = tablero.getUltimoMovimiento().getFila();
		int columna = tablero.getUltimoMovimiento().getColumna();
		
		if (tablero.getUltimaJugada() == Tablero.X) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha("RED", fila, columna);
		} else if (tablero.getUltimaJugada() == Tablero.O) {
			// COLOCA UNA FICHA EN LA [fila][columna] CORRESPONDIENTE EN LA INTERFAZ DE USUARIO (GUI).
			colocarFicha("YELLOW", fila, columna);
		} 
		if (tablero.checkGameOver()) {
			gameOver();
		}

	}
	
	/**
	 * 	DEVUELVE UN COMPONENTE PARA SE DIBUJADO POR LA VENTANA PRINCIPAL.
	 * ESTE METODO CREA LOS COMPONENTES DE LA PANTALLA PRINCIPAL
	 * CUANDO SE HACE CLICK SE LLAMA AL METODO actionListener()
	 */
	public static Component createContenidoComponentes() {
		//CREA UN PANEL PARA CONFIGURAR LOS BOTONES DEL TABLERO
		panelTableroNumeros = new JPanel();
		panelTableroNumeros.setLayout(new GridLayout(1, 7, 6, 4));
		panelTableroNumeros.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		JButton boton_columna1 = new JButton("1");
		boton_columna1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(0);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna2 = new JButton("2");
		boton_columna2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(1);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna3 = new JButton("3");
		boton_columna3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(2);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna4 = new JButton("4");
		boton_columna4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(3);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna5 = new JButton("5");
		boton_columna5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(4);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna6 = new JButton("6");
		boton_columna6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(5);
				if (!tablero.isOverflowOccured()) {
					juego();
				}
				vemtanaPrincipalFrame.requestFocusInWindow();
			}
		});
		
		JButton boton_columna7 = new JButton("7");
		boton_columna7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mover(6);
				if (!tablero.isOverflowOccured()) {
					juego();
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

		// CREA EL TABLERO PRINCIPAL DEL 4 EN RAYA
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
		tablero.setFinJuegor(true);
		frameGameOver = new JFrame("Game over!");
		frameGameOver.setBounds(620, 400, 350, 128);
		centrarVentana(frameGameOver, 0, 0);
		JPanel ganarPanel = new JPanel(new BorderLayout());
		ganarPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

		JLabel ganarLabel;
		tablero.checkEstadoGanador();
		if (tablero.getGanador() == Tablero.X) {
			ganarLabel = new JLabel("¡Ganaste Jugador 1 (ROJO)! ¿Quieres jugar otra partida?");
			ganarPanel.add(ganarLabel);
		} else if (tablero.getGanador() == Tablero.O) {
			ganarLabel = new JLabel("¡Ganaste Jugador 2 (AMARILLO)  ! ¿Quieres jugar otra partida?");
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
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				crearNuevoJuego();
			}
		});
		
		JButton botonSalir = new JButton("Salir");
		botonSalir.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				System.exit(0);
			}
		});
		
		JPanel subPanel = new JPanel();
		subPanel.add(botonSi);
		subPanel.add(botonSalir);
		
		ganarPanel.add(subPanel, BorderLayout.CENTER);
		frameGameOver.getContentPane().add(ganarPanel, BorderLayout.CENTER);
		frameGameOver.setResizable(false);
		frameGameOver.setVisible(true);
	}
//	
//	@SuppressWarnings("static-access")
//	public static void main(String[] args){
//		 GUIHumanoVSHumano cuatroEnRaya = new GUIHumanoVSHumano();
//		cuatroEnRaya.crearNuevoJuego();
//	}
		
}
