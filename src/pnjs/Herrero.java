package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Herrero extends Personaje {
	protected void setup(){
		Gui.setHistoria("El herrero "+getLocalName()+" empieza a forjar las armas de la batlla");
	}
}
