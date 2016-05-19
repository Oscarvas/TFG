package ontologia;

import java.util.HashMap;

public class Diccionario {
	// Se guardara de la siguiente forma <Clase,Frases>	
	public HashMap<String, Frases> diccionarioFrases;

	public HashMap<String, Frases> getDiccionario() {
		return this.diccionarioFrases;
	}
	
	public void addFraseConPersonaje(String personaje, String accion, String frase){
		if(this.diccionarioFrases.get(personaje) == null){
			this.diccionarioFrases.put(personaje, new Frases());
		}
		
		this.diccionarioFrases.get(personaje).addFrase(accion, frase);		
	}
}
