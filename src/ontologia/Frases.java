package ontologia;

import java.util.ArrayList;
import java.util.HashMap;

public class Frases {
	// Se guardara de la siguiente forma <Clase,<Accion,Frases>>	
	public HashMap<String, HashMap<String, ArrayList<String>>> frases;

	public HashMap<String, HashMap<String, ArrayList<String>>> getFrases() {
		return frases;
	}

	public void setFrases(HashMap<String, HashMap<String, ArrayList<String>>> frases) {
		this.frases = frases;
	}	
	
}
