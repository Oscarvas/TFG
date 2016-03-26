package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jade.core.Agent;
import jade.gui.GuiEvent;
import mundo.Mundo;
import ontologia.Vocabulario;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private Libro contentPane;
	private Mundo myAgent;
	private static JTextArea textAreaHistoria;

	/**
	 * Create the frame.
	 * @param mundo 
	 */
	public Gui(Mundo mundo) {
		myAgent = mundo;
		setTitle("La Historia de - " + myAgent.getLocalName());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPersonajes = new JMenu("Personajes");
		menuBar.add(mnPersonajes);
		
		JMenuItem mntmCrearPersonaje = new JMenuItem("Crear Personaje");
		mntmCrearPersonaje.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NuevoPersonaje dialog = new NuevoPersonaje(myAgent);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnPersonajes.add(mntmCrearPersonaje);
		
		JMenuItem mntmVerPersonajes = new JMenuItem("Ver Personajes");
		mnPersonajes.add(mntmVerPersonajes);
		
		JMenu mnMapa = new JMenu("Mapa");
		menuBar.add(mnMapa);
		
		JMenuItem mntmMostrarMapa = new JMenuItem("Mostrar Mapa");
		mntmMostrarMapa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DialogMapa dialog = new DialogMapa();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);				
			}
		});
		mnMapa.add(mntmMostrarMapa);
		
		JMenuItem mntmIniciar = new JMenuItem("Iniciar");
		mntmIniciar.setMaximumSize(new Dimension(70, 32767));
		mntmIniciar.setPreferredSize(new Dimension(0, 2));
		mntmIniciar.setIcon(new ImageIcon(Gui.class.getResource("/images/icon.png")));
		menuBar.add(mntmIniciar);
		
		mntmIniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiEvent ge = new GuiEvent(this, Vocabulario.INICIAR_HISTORIA);
	            mundo.postGuiEvent(ge);
			}
		});
		contentPane = new Libro();
		setContentPane(contentPane);
		
		textAreaHistoria = new JTextArea();
		textAreaHistoria.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		textAreaHistoria.setEditable(false);
		textAreaHistoria.setOpaque(false);
		contentPane.add(textAreaHistoria, BorderLayout.NORTH);
		this.setLocationRelativeTo(null);
	}
	
	public static void setHistoria(String hist){
		String acumulado = textAreaHistoria.getText();
		acumulado+=hist;
		textAreaHistoria.setText(acumulado);
	}

}
