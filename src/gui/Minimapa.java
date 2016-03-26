package gui;

import java.awt.BorderLayout;

import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Minimapa extends JPanelBackground {
	public Minimapa(){
		super();
		this.setBackground("resources/images/miniMapa.png");
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
	}
}
