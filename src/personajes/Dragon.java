package personajes;

import gui.Gui;

@SuppressWarnings("serial")
public class Dragon extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria("El imponente rugido del dragón "+getLocalName()+" se escucha por todo el reino.");
	}
}
