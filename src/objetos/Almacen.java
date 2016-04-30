package objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

public class Almacen {
	// HashMap que guarda los objetos en funcion de su tipo. Ejemplo: "consumible", Lista consumibles 
	private HashMap<String, ArrayList<Objeto>> objetos;
	
	public Almacen(){
		this.objetos = new HashMap<String, ArrayList<Objeto>>();
	}	
	
	public void añadirObjeto(String tipo, Objeto objeto){		
		if(this.objetos.get(tipo) == null){
			this.objetos.put(tipo, new ArrayList<Objeto>());
		}
		ArrayList<Objeto> aux = this.objetos.get(tipo);
		aux.add(objeto);
		this.objetos.put(tipo, aux);
	}
	
	public Objeto extraerObjeto(String tipo, int i){
		Objeto o = this.objetos.get(tipo).get(i);
		this.objetos.get(tipo).remove(i);
		return o;
	}
	
	public boolean hayObjetosConsumibles(){		
		return !this.objetos.get("consumible").isEmpty();
	}
	/**
	 * Mira si hay objetos claves que se hayan guardado.
	 * @param localizacion
	 * @return la posicion del objeto clave, -1 si no hay objeto clave.
	 */
	public int hayObjetoClave(String localizacion){
		int i = -1;
		Clave c;
		Iterator it = (Iterator) this.objetos.get("clave").iterator();
		while(it.hasNext()){
			i++;
			c = (Clave) it.next();
			if(c.getLocalizacion().equals(localizacion)){
				return i;
			}
		}
		return i;
	}
	
	public int consumibleAleatorio(){
		Random rnd = new Random();
		return (int) (rnd.nextDouble() * this.objetos.get("consumible").size());
	}
	
	public String toString(){
		String mensaje = "\nConsumibles:";		
		for(Objeto s : this.objetos.get("consumible")){
			mensaje = mensaje + "\n" + s.toString() + "\n";
		}
		mensaje = mensaje + "\nClave:";
		for(Objeto s : this.objetos.get("clave")){
			mensaje = mensaje + "\n" + s.toString() + "\n";
		}
		
		return mensaje;
	}

}
