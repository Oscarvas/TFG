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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class NuevoPNJ extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textOficio;
	private JTextField textLocaliz;
	/**
	 * Create the dialog.
	 */
	public NuevoPNJ(Mundo mundo) {
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Nuevo PNJ");
		setBounds(100, 100, 263, 222);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setLocationRelativeTo(null);
		
		//-------------------------------area nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(104, 11, 133, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNombre);
		
		//-------------------------------localizacion
		JLabel lblLocalizacion = new JLabel("Localizaci\u00F3n");
		lblLocalizacion.setBounds(10, 76, 84, 14);
		contentPanel.add(lblLocalizacion);
		
		//-------------------------------oficio
		JLabel lblOficio = new JLabel("Oficio");
		lblOficio.setBounds(10, 45, 46, 14);
		contentPanel.add(lblOficio);
		
		//-------------------------------Sexo
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(10, 101, 46, 14);
		contentPanel.add(lblSexo);
		
		JRadioButton rdbtnM = new JRadioButton("M");
		rdbtnM.setSelected(true);
		rdbtnM.setBounds(104, 100, 56, 23);
		contentPanel.add(rdbtnM);
		
		JRadioButton rdbtnF = new JRadioButton("F");
		rdbtnF.setBounds(159, 100, 78, 23);
		contentPanel.add(rdbtnF);
		
		buttonGroup.add(rdbtnM);
		buttonGroup.add(rdbtnF);
		
		textOficio = new JTextField();
		textOficio.setBounds(104, 42, 133, 20);
		contentPanel.add(textOficio);
		textOficio.setColumns(10);
		
		textLocaliz = new JTextField();
		textLocaliz.setBounds(104, 73, 133, 20);
		contentPanel.add(textLocaliz);
		textLocaliz.setColumns(10);
		

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!txtNombre.getText().isEmpty()){
							GuiEvent ge = new GuiEvent(this, Vocabulario.CREAR_PNJ);
				            ge.addParameter(txtNombre.getText().replaceAll("\\s",""));
				            ge.addParameter("personajes.pnjs.PNJ");
				            
				            if(rdbtnM.isSelected())
				            	ge.addParameter(rdbtnM.getText());
				            else
				            	ge.addParameter(rdbtnF.getText());
				            
				            ge.addParameter(textOficio.getText().replaceAll("\\s",""));
				            ge.addParameter(textLocaliz.getText().replaceAll("\\s",""));
				            
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
