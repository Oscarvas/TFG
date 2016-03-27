package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Sastre extends Personaje {
	protected void setup(){
		Gui.setHistoria("La sastre "+getLocalName()+" estira las telas y recoge los hilos de la luna");
	}

}
