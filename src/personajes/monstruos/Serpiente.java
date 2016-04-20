package personajes.monstruos;

import gui.Gui;

@SuppressWarnings("serial")
public class Serpiente extends Monstruo {
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1], (String) args[2]);
		Gui.setHistoria(getLocalizacion()+" se ha sumergido en la penumbra tras el paseo de "+getSexo() +" " + getEspecie()+ " "+getLocalName()+".");
	}
}
