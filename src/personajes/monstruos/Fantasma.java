package personajes.monstruos;

import gui.Gui;

@SuppressWarnings("serial")
public class Fantasma extends Monstruo {
	
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1], (String) args[2]);
		Gui.setHistoria(getLocalName()+ " " + getSexo() +" " + getEspecie() +" ha decidido que es la hora de la venganza en el castillo "+getLocalizacion()+".");
	}
}
