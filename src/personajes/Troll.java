package personajes;

import gui.Gui;

@SuppressWarnings("serial")
public class Troll extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria("Parece que mientras "+getLocalName()+" sea el guardi�n del "+getLocalizacion()+", la desgracia caer� sobre cada insensato que pase por ah�.");
	}
}
