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

public class NuevoPersonaje extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtClase;
	/**
	 * Create the dialog.
	 */
	public NuevoPersonaje(Mundo mundo) {
		setBounds(100, 100, 243, 149);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(null);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(66, 11, 153, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		{
			txtClase = new JTextField();
			txtClase.setBounds(66, 42, 153, 20);
			contentPanel.add(txtClase);
			txtClase.setColumns(10);
		}
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNombre);
		
		JLabel lblClase = new JLabel("Clase");
		lblClase.setBounds(10, 45, 46, 14);
		contentPanel.add(lblClase);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GuiEvent ge = new GuiEvent(this, Vocabulario.CREAR_AGENTE);
			            ge.addParameter(txtNombre.getText());
			            if (!txtClase.getText().isEmpty())
			            	ge.addParameter("personajes."+txtClase.getText());
			            dispose();
			            mundo.postGuiEvent(ge);
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
