package ontologia;

import java.util.ArrayList;
import java.util.HashMap;

public class Frases {
	// Se guardara de la siguiente forma <Clase,<Accion,Frases>>	
	public HashMap<String, HashMap<String, ArrayList<String>>> frases;

	public HashMap<String, HashMap<String, ArrayList<String>>> getFrases() {
		return frases;
	}
	
	public void añadirFrase(String personaje, String accion, String frase){
		if(this.frases.get(personaje) == null){
			this.frases.put(personaje, new HashMap<String, ArrayList<String>>());
		}
		if(this.frases.get(personaje).get(accion) == null){
			this.frases.get(personaje).put(accion, new ArrayList<String>());
		}
		ArrayList<String> aux = this.frases.get(personaje).get(accion);
		aux.add(frase);
		this.frases.get(personaje).put(accion, aux);
	}

}
