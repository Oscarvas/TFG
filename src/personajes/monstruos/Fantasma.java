package personajes.monstruos;

import personajes.Personaje;
import gui.Gui;

@SuppressWarnings("serial")
public class Fantasma extends Monstruo {
	
	protected void setup(){
		Object[] args = getArguments(); 
		this.localizacion = (String)args[0];
		
		iniciarMonstruo(this.localizacion);
		Gui.setHistoria(getLocalName()+" el fantasma ha decidido que es la hora de la venganza en el castillo "+getLocalizacion()+".");
	}
}
