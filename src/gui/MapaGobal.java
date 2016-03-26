package gui;

import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MapaGobal extends JPanelBackground {

	/**
	 * Create the panel.
	 */
	public MapaGobal() {
		super();
		this.setBackground("resources/images/MapaBeta.png");
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
	}

}
