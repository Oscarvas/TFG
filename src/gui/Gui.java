package gui;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import jade.gui.GuiEvent;
import mundo.Mundo;
import ontologia.Vocabulario;

import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private Libro contentPane;
	private Mundo myAgent;
	private static JTextArea textAreaHistoria;
	private static Logger logger;

	/**
	 * Create the frame.
	 * @param mundo 
	 */
	public Gui(Mundo mundo) {
		
		myAgent = mundo;
		
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(myAgent.getLocalName());
				
		setTitle(myAgent.getLocalName());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPersonajes = new JMenu("Personajes");
		menuBar.add(mnPersonajes);
		
		JMenuItem mntmNuevoProtagonista = new JMenuItem("Nuevo Protagonista");
		mntmNuevoProtagonista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NuevoPersonaje dialog = new NuevoPersonaje(myAgent);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnPersonajes.add(mntmNuevoProtagonista);
		
		JMenuItem mntmNuevoAntagonista = new JMenuItem("Nuevo Antagonista");
		mntmNuevoAntagonista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NuevoAntagonista dialog = new NuevoAntagonista(myAgent);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnPersonajes.add(mntmNuevoAntagonista);
		
		JMenuItem mntmNuevoPNJ = new JMenuItem("Nuevo PNJ");
		mntmNuevoPNJ.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NuevoPNJ dialog = new NuevoPNJ(myAgent);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnPersonajes.add(mntmNuevoPNJ);
		
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
		
		
		JMenuItem mntmMostrarMinimapa = new JMenuItem("Mostrar Minimapa");
		mntmMostrarMinimapa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DialogMiniMapa minimapa = new DialogMiniMapa();
				minimapa.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				minimapa.setLocation((int) (xFrame+this.getWidth()), yFrame);
				minimapa.setVisible(true);				
			}
		});
		mnMapa.add(mntmMostrarMapa);
		mnMapa.add(mntmMostrarMinimapa);
		
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
		textAreaHistoria.setLineWrap(true);
		textAreaHistoria.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		textAreaHistoria.setEditable(false);
		textAreaHistoria.setOpaque(false);
		//para que la ventana se actualice conforme se van mostrando mas mensajes
		DefaultCaret caret = (DefaultCaret) textAreaHistoria.getCaret(); //
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);       //
		//
		JScrollPane scrollPane = new JScrollPane(textAreaHistoria);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(null);
		contentPane.add(scrollPane, BorderLayout.CENTER);
//		Este codigo habilita la barra de scroll vertical para el area de texto, se queda comentado porque no tiene transparencia
//		contentPane.add(textAreaHistoria, BorderLayout.CENTER);
		
		this.setLocationRelativeTo(null);
	}
		
	public static synchronized void setHistoria(String hist){
		
		//Logger
		logger.info(hist);
		
		String acumulado = textAreaHistoria.getText();
		acumulado+=hist+"\n";
		textAreaHistoria.setText(acumulado);
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
