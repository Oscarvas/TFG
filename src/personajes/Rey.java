package personajes;

import gui.Gui;
import ontologia.Mitologia;

@SuppressWarnings("serial")
public class Rey extends Personaje {
	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), true);
		}
		localizarPersonaje();
		mandarCrearArchivo();
		Gui.setHistoria("El rey "+getLocalName()+" apenas despierta, y la que se ha liado en su reino es digna de una buena historia.");
	}
}
