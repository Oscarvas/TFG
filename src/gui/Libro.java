package gui;

import java.awt.BorderLayout;

import javax.swing.border.EmptyBorder;

public class Libro extends JPanelBackground {

	/**
	 * Create the panel.
	 */
	public Libro() {
		super();
		this.setBackground("resources/images/OnePage.png");
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
	}

}
