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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class NuevoAntagonista extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textEspecie;
	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NuevoAntagonista(Mundo mundo) {
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Nuevo Antagonista");
		setBounds(100, 100, 243, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(null);
		
		//-------------------------------area nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(86, 11, 133, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNombre);
		
		//-------------------------------clase
		JLabel lblClase_1 = new JLabel("Clase");
		lblClase_1.setBounds(10, 45, 46, 14);
		contentPanel.add(lblClase_1);
		
		JComboBox clases = new JComboBox(Vocabulario.CLASES_ANT);
		clases.setSelectedIndex(-1);
		clases.setBounds(86, 42, 133, 20);
		contentPanel.add(clases);
		
		//-------------------------------Sexo
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(10, 101, 46, 14);
		contentPanel.add(lblSexo);
		
		JRadioButton rdbtnM = new JRadioButton("M");
		rdbtnM.setSelected(true);
		rdbtnM.setBounds(86, 100, 56, 23);
		contentPanel.add(rdbtnM);
		
		JRadioButton rdbtnF = new JRadioButton("F");
		rdbtnF.setBounds(141, 100, 78, 23);
		contentPanel.add(rdbtnF);
		
		buttonGroup.add(rdbtnM);
		buttonGroup.add(rdbtnF);		
		
		textEspecie = new JTextField();
		textEspecie.setBounds(86, 73, 131, 20);
		contentPanel.add(textEspecie);
		textEspecie.setColumns(10);
		
		JLabel lblEspecie = new JLabel("Especie");
		lblEspecie.setBounds(10, 76, 46, 14);
		contentPanel.add(lblEspecie);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!txtNombre.getText().isEmpty()){
							GuiEvent ge = new GuiEvent(this, Vocabulario.CREAR_ANTAGONISTA);
				            ge.addParameter(txtNombre.getText().replaceAll("\\s",""));
				            ge.addParameter("personajes.antagonistas."+clases.getSelectedItem());
				            ge.addParameter(textEspecie.getText().replaceAll("\\s",""));
				            if(rdbtnM.isSelected())
				            	ge.addParameter(rdbtnM.getText());
				            else
				            	ge.addParameter(rdbtnF.getText());				            
				            
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
