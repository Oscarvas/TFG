package personajes;

import gui.Gui;

@SuppressWarnings("serial")
public class Serpiente extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria(getLocalizacion()+" se ha sumergido en la penumbra tras el paseo de "+getLocalName()+".");
	}
}
