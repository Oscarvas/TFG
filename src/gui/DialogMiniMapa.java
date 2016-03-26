package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class DialogMiniMapa extends JDialog {
	private final Minimapa contentPanel = new Minimapa();

	public DialogMiniMapa(){
		setTitle("Minimapa");
		setResizable(false);
		setBounds(100, 100, 402, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
}
