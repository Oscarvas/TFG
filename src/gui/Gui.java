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

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel contentPane;
	private Mundo myAgent;

	/**
	 * Create the frame.
	 * @param mundo 
	 */
	public Gui(Mundo mundo) {
		myAgent = mundo;
		setTitle("La Historia de - " + myAgent.getLocalName());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 212);
		
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
		
		JMenu mnHistoria = new JMenu("Historia");
		menuBar.add(mnHistoria);
		
		JMenuItem mntmIniciarHistoria = new JMenuItem("Iniciar Historia");
		mnHistoria.add(mntmIniciarHistoria);
		mntmIniciarHistoria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GuiEvent ge = new GuiEvent(this, Vocabulario.INICIAR_HISTORIA);
	            mundo.postGuiEvent(ge);
			}
		});
		
		JMenuItem mntmVerHistoria = new JMenuItem("Ver Historia");
		mnHistoria.add(mntmVerHistoria);
		mntmVerHistoria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DialogHistoria dialog = new DialogHistoria();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);				
			}
		});
		contentPane = new JPanel();
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);
	}

}
