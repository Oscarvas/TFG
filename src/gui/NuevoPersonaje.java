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

public class NuevoPersonaje extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	/**
	 * Create the dialog.
	 */
	public NuevoPersonaje(Mundo mundo) {
		setTitle("Creaci\u00F3n de Personajes");
		setBounds(100, 100, 243, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(null);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(76, 11, 143, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNombre);
		
		JLabel lblClase = new JLabel("Raza");
		lblClase.setBounds(10, 76, 46, 14);
		contentPanel.add(lblClase);
		
		JComboBox razas = new JComboBox(Vocabulario.RAZAS);
		razas.setBounds(76, 73, 143, 20);
		contentPanel.add(razas);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinner.setBounds(76, 101, 143, 20);
		contentPanel.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinner_1.setBounds(76, 132, 143, 20);
		contentPanel.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinner_2.setBounds(76, 163, 143, 20);
		contentPanel.add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinner_3.setBounds(76, 194, 143, 20);
		contentPanel.add(spinner_3);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinner_4.setBounds(76, 225, 143, 20);
		contentPanel.add(spinner_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(76, 42, 143, 20);
		contentPanel.add(comboBox);
		
		JLabel lblVida = new JLabel("Vida");
		lblVida.setBounds(10, 104, 46, 14);
		contentPanel.add(lblVida);
		
		JLabel lblFuerza = new JLabel("Fuerza");
		lblFuerza.setBounds(10, 135, 46, 14);
		contentPanel.add(lblFuerza);
		
		JLabel lblDestreza = new JLabel("Destreza");
		lblDestreza.setBounds(10, 166, 46, 14);
		contentPanel.add(lblDestreza);
		
		JLabel lblInteligencia = new JLabel("Inteligencia");
		lblInteligencia.setBounds(10, 197, 56, 14);
		contentPanel.add(lblInteligencia);
		
		JLabel lblCodicia = new JLabel("Codicia");
		lblCodicia.setBounds(10, 228, 46, 14);
		contentPanel.add(lblCodicia);
		
		JLabel lblClase_1 = new JLabel("Clase");
		lblClase_1.setBounds(10, 45, 46, 14);
		contentPanel.add(lblClase_1);
		
		JTextArea txtrLaClaseDetermina = new JTextArea();
		txtrLaClaseDetermina.setOpaque(false);
		txtrLaClaseDetermina.setFont(new Font("Monotype Corsiva", Font.PLAIN, 16));
		txtrLaClaseDetermina.setLineWrap(true);
		txtrLaClaseDetermina.setEditable(false);
		txtrLaClaseDetermina.setText("La Clase determina las acciones que \r\ntomar\u00E1 el personaje.\r\nLa Raza influir\u00E1 en la regi\u00F3n donde nacer\u00E1 el personaje.\r\nLos atributos de vida, fuerza, \r\ndestreza... van a afectar a las \r\ncaracter\u00EDsticas iniciales del personaje, teniendo en cuenta que cada Raza dispone de modificadores que \r\ninfluir\u00E1n en los que se definan aqu\u00ED.");
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
				            ge.addParameter("personajes."+razas.getSelectedItem());
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
