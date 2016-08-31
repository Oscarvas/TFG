package ontologia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/*
 * Clase que tendrá cada personaje donde se guadará
 */
public class Frases {	
	// Se guardara de la siguiente forma <Accion,Array de frases>	
	public HashMap<String, ArrayList<String>> frases;
	
	public Frases(){
		this.frases = new HashMap<String,ArrayList<String>>();
	}

	public HashMap<String, ArrayList<String>> getFrases() {
		return this.frases;
	}
	
	public void addFrase(String accion, String frase){
		
		if(this.frases.get(accion) == null){
			this.frases.put(accion, new ArrayList<String>());
		}
		ArrayList<String> aux = this.frases.get(accion);
		aux.add(frase);
		this.frases.put(accion, aux);
	}
	
	public String getFraseAccion(String accion){
		int i;
		Random rnd = new Random();
		i = (int) (rnd.nextDouble() * this.frases.get(accion).size() + 0);
		return this.frases.get(accion).get(i);
	}

}
