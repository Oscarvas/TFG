package pnjs;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Chaman extends Personaje {
	protected void setup(){
		Gui.setHistoria("El cham�n "+getLocalName()+" entra en sinton�a con los elementos");
	}
}
