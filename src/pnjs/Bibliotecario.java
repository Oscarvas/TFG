package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Bibliotecario extends Personaje {
	protected void setup(){
		Gui.setHistoria("La bibliotecaria "+getLocalName()+" abre la biblioteca");
	}
}
