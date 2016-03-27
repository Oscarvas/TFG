package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Chaman extends Personaje {
	protected void setup(){
		Gui.setHistoria("El chamán "+getLocalName()+" entra en sintonía con los elementos");
	}
}
