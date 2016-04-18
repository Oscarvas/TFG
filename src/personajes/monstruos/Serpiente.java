package personajes.monstruos;

import personajes.Personaje;
import gui.Gui;

@SuppressWarnings("serial")
public class Serpiente extends Monstruo {
	protected void setup(){
		Object[] args = getArguments(); 
		this.localizacion = (String)args[0];
		
		iniciarMonstruo(this.localizacion);
		Gui.setHistoria(getLocalizacion()+" se ha sumergido en la penumbra tras el paseo de "+getLocalName()+".");
	}
}
