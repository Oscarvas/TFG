package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Granjero extends Personaje {
	protected void setup(){
		Gui.setHistoria("El granjero "+getLocalName()+" prepara los establos");
	}
}
