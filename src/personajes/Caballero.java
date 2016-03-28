package personajes;

import gui.Gui;
import ontologia.Mitologia;

@SuppressWarnings("serial")
public class Caballero extends Personaje {

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
		}
		localizarPersonaje();
		mandarCrearArchivo();
		Gui.setHistoria("El caballero "+getLocalName()+" se ha despertado en "+getLocalizacion()+" con su armadura hecha polvo.");
	}
	

}
