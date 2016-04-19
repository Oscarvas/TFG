package personajes.monstruos;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Fantasma extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria(getLocalName()+" el fantasma ha decidido que es la hora de la venganza en el castillo "+getLocalizacion()+".");
	}
}
