package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jade.gui.GuiEvent;
import mundo.Mundo;
import ontologia.Vocabulario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import java.awt.Font;

@SuppressWarnings("serial")
public class NuevoPersonaje extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NuevoPersonaje(Mundo mundo) {
		setTitle("Creaci\u00F3n de Personajes");
		setBounds(100, 100, 243, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(null);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(86, 11, 133, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNombre);
		
		JLabel lblClase = new JLabel("Raza");
		lblClase.setBounds(10, 76, 46, 14);
		contentPanel.add(lblClase);
		
		JComboBox razas = new JComboBox(Vocabulario.RAZAS);
		razas.setBounds(86, 73, 133, 20);
		contentPanel.add(razas);
		
		JLabel lblClase_1 = new JLabel("Clase");
		lblClase_1.setBounds(10, 45, 46, 14);
		contentPanel.add(lblClase_1);
		
		JComboBox clases = new JComboBox(Vocabulario.CLASES);
		clases.setBounds(86, 42, 133, 20);
		contentPanel.add(clases);
		
		JSpinner spVida = new JSpinner();
		spVida.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spVida.setBounds(86, 101, 133, 20);
		contentPanel.add(spVida);
		
		JSpinner spFuerza = new JSpinner();
		spFuerza.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spFuerza.setBounds(86, 132, 133, 20);
		contentPanel.add(spFuerza);
		
		JSpinner spDestreza = new JSpinner();
		spDestreza.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spDestreza.setBounds(86, 163, 133, 20);
		contentPanel.add(spDestreza);
		
		JSpinner spInteligencia = new JSpinner();
		spInteligencia.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spInteligencia.setBounds(86, 194, 133, 20);
		contentPanel.add(spInteligencia);
		
		JSpinner spCodicia = new JSpinner();
		spCodicia.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spCodicia.setBounds(86, 225, 133, 20);
		contentPanel.add(spCodicia);
		
		JLabel lblVida = new JLabel("Vida");
		lblVida.setBounds(10, 104, 46, 14);
		contentPanel.add(lblVida);
		
		JLabel lblFuerza = new JLabel("Fuerza");
		lblFuerza.setBounds(10, 135, 46, 14);
		contentPanel.add(lblFuerza);
		
		JLabel lblDestreza = new JLabel("Destreza");
		lblDestreza.setBounds(10, 166, 56, 14);
		contentPanel.add(lblDestreza);
		
		JLabel lblInteligencia = new JLabel("Inteligencia");
		lblInteligencia.setBounds(10, 197, 78, 14);
		contentPanel.add(lblInteligencia);
		
		JLabel lblCodicia = new JLabel("Codicia");
		lblCodicia.setBounds(10, 228, 46, 14);
		contentPanel.add(lblCodicia);
		
		JTextArea txtrLaClaseDetermina = new JTextArea();
		txtrLaClaseDetermina.setOpaque(false);
		txtrLaClaseDetermina.setFont(new Font("Monotype Corsiva", Font.PLAIN, 16));
		txtrLaClaseDetermina.setLineWrap(true);
		txtrLaClaseDetermina.setEditable(false);
		txtrLaClaseDetermina.setText("La Clase determina las acciones que \r\ntomar\u00E1 el personaje.\r\n\r\nLa Raza influir\u00E1 en la regi\u00F3n donde nacer\u00E1 el personaje.\r\n\r\nLos atributos de vida, fuerza, \r\ndestreza... van a afectar a las \r\ncaracter\u00EDsticas iniciales del personaje, teniendo en cuenta que cada Raza dispone de modificadores que \r\ninfluir\u00E1n en los que se definan aqu\u00ED.\r\n\r\nM\u00E1ximo 10pts en cada atributo.");
		txtrLaClaseDetermina.setBounds(10, 253, 209, 364);
		contentPanel.add(txtrLaClaseDetermina);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!txtNombre.getText().isEmpty()){
							GuiEvent ge = new GuiEvent(this, Vocabulario.CREAR_AGENTE);
				            ge.addParameter(txtNombre.getText());
				            ge.addParameter("personajes."+clases.getSelectedItem());
				            ge.addParameter(razas.getSelectedItem());
				            ge.addParameter(spVida.getValue());
				            ge.addParameter(spFuerza.getValue());
				            ge.addParameter(spDestreza.getValue());
				            ge.addParameter(spInteligencia.getValue());
				            ge.addParameter(spCodicia.getValue());
				            mundo.postGuiEvent(ge);
						}
			            txtNombre.setText("");//vaciando el cuadro de txto al darle ok
			            
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
