package com.kzurro.settings;
import javax.swing.*;

import com.kzurro.tablero.Parametros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Preferencias extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	
	private JComboBox<String> desplegable_1;
	private JComboBox<Integer> desplegable_2;
	private JComboBox<String> desplegable_3;
	private JComboBox<String> desplegable_4;
	
	private JButton guardar;
	private JButton cancelar;
	
	private EventHandler handler;
	
	private Parametros pJuego; 
	
	
	public Preferencias(Parametros parametros) {
		super("Preferencias");
		
		this.pJuego = parametros; 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		label1 = new JLabel("Modo de Juego: ");
		label2 = new JLabel("Dificultad IA: ");
		label3 = new JLabel("Color Jugador 1: ");
		label4 = new JLabel("Color Jugador 2: ");
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		label1.setBounds(20, 25, 175, 20);
		label2.setBounds(20, 75, 175, 20);
		label3.setBounds(20, 125, 175, 20);
		label4.setBounds(20, 175, 175, 20);
		
		desplegable_1 = new JComboBox<String>();
		desplegable_1.addItem("Humano VS IA");
		desplegable_1.addItem("Humano VS Humano");
		
		int modoSeleccionado = pJuego.getModoJuego();
		if (modoSeleccionado == Parametros.humanoVSIa)
			desplegable_1.setSelectedIndex(0);
		else if (modoSeleccionado == Parametros.humanoVSHumano)
			desplegable_1.setSelectedIndex(1);
		
		desplegable_2 = new JComboBox<Integer>();
		desplegable_2.addItem(1);
		desplegable_2.addItem(2);
		desplegable_2.addItem(3);
		desplegable_2.addItem(4);
		desplegable_2.addItem(5);
		desplegable_2.addItem(6);
		desplegable_2.addItem(7);
		//desplegable_2.addItem(8);// TARDA EN PROCESAR
		//desplegable_2.addItem(9);// TARDA EN PROCESAR
		//desplegable_2.addItem(10);// TARDA EN PROCESAR
		
		int index = pJuego.getMaxProfundidad() - 1;
		desplegable_2.setSelectedIndex(index);
		
		
		desplegable_3 = new JComboBox<String>();
		desplegable_3.addItem("ROJO");
		desplegable_3.addItem("AMARILLO");
		desplegable_3.addItem("NEGRO");
		desplegable_3.addItem("VERDE");
		desplegable_3.addItem("NARANJA");
		desplegable_3.addItem("MORADO");
		
		String jugador1Seleccionado = pJuego.getJugador1();
		if (jugador1Seleccionado.equals("ROJO"))
			desplegable_3.setSelectedIndex(0);
		else if (jugador1Seleccionado.equals("AMARILLO"))
			desplegable_3.setSelectedIndex(1);
		else if (jugador1Seleccionado.equals("NEGRO"))
			desplegable_3.setSelectedIndex(2);
		else if (jugador1Seleccionado.equals("VERDE"))
			desplegable_3.setSelectedIndex(3);
		else if (jugador1Seleccionado.equals("NARANJA"))
			desplegable_3.setSelectedIndex(4);
		else if (jugador1Seleccionado.equals("MORADO"))
			desplegable_3.setSelectedIndex(5);
		
		desplegable_4 = new JComboBox<String>();
		desplegable_4.addItem("ROJO");
		desplegable_4.addItem("AMARILLO");
		desplegable_4.addItem("NEGRO");
		desplegable_4.addItem("VERDE");
		desplegable_4.addItem("NARANJA");
		desplegable_4.addItem("MORADO");
		
		String jugador2Seleccionado = pJuego.getJugador2();
		if (jugador2Seleccionado.equals("ROJO"))
			desplegable_4.setSelectedIndex(0);
		else if (jugador2Seleccionado.equals("AMARILLO"))
			desplegable_4.setSelectedIndex(1);
		else if (jugador2Seleccionado.equals("NEGRO"))
			desplegable_4.setSelectedIndex(2);
		else if (jugador2Seleccionado.equals("VERDE"))
			desplegable_4.setSelectedIndex(3);
		else if (jugador2Seleccionado.equals("NARANJA"))
			desplegable_4.setSelectedIndex(4);
		else if (jugador2Seleccionado.equals("MORADO"))
			desplegable_4.setSelectedIndex(5);
		
		
		add(desplegable_1);
		add(desplegable_2);
		add(desplegable_3);
		add(desplegable_4);
		desplegable_1.setBounds(220,25,160,20);
		desplegable_2.setBounds(220,75,160,20);
		desplegable_3.setBounds(220,125,160,20);
		desplegable_4.setBounds(220,175,160,20);
		
		guardar = new JButton("Guardar");
		cancelar = new JButton("Cancelar");
		add(guardar);
		add(cancelar);
		guardar.setBounds(80, 225, 100, 30);
		guardar.addActionListener(handler);
		cancelar.setBounds(220, 225, 100, 30);
		cancelar.addActionListener(handler);
	}


	private class EventHandler implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			
			if(ev.getSource() == cancelar) {
				dispose();
			}
			
			else if(ev.getSource() == guardar) {
				try {
					
					String modo_juego_String = (String)desplegable_1.getSelectedItem();
					int profundidad = (int) desplegable_2.getSelectedItem();
					String color_juagador1 = (String)desplegable_3.getSelectedItem();
					String color_juagador2 = (String)desplegable_4.getSelectedItem();
					
					int modo_juego = (modo_juego_String.equals("Humano VS IA")) ? Parametros.humanoVSIa : Parametros.humanoVSHumano;
					
					if(color_juagador1 == color_juagador2) {
						JOptionPane.showMessageDialog(null ,  "¡El Jugador 1 y el Jugador 2 no pueden tener el mismo color de FICHAS!" , "ERROR" , JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					pJuego.setModoJuego(modo_juego);
					pJuego.setMaxProfundidad(profundidad);
					pJuego.setJugador1(color_juagador1);
					pJuego.setJugador2(color_juagador2);
					
					JOptionPane.showMessageDialog(null , "La configuración de Juego ha cambiado.Los cambios se modificarán en la próxima partida." , ""  , JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			} 
			
		} 
		
	} 
	
} 
