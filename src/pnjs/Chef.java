package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Chef extends Personaje {
	protected void setup(){
		Gui.setHistoria("El chef "+getLocalName()+" calienta el horno");
	}

}
