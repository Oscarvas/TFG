package personajes.monstruos;

import gui.Gui;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Serpiente extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria(getLocalizacion()+" se ha sumergido en la penumbra tras el paseo de "+getLocalName()+".");
	}
}
