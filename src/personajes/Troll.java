package personajes;

import gui.Gui;

@SuppressWarnings("serial")
public class Troll extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria("Parece que mientras "+getLocalName()+" sea el guardián del "+getLocalizacion()+", la desgracia caerá sobre cada insensato que pase por ahí.");
	}
}
