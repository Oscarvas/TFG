package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Tabernero extends Personaje {
	protected void setup(){
		Gui.setHistoria("El tabernero "+getLocalName()+" limpia las mesas a la espera de clientes");
	}
}
