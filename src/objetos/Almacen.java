package objetos;

import java.util.ArrayList;
import java.util.HashMap;

public class Almacen {
	private HashMap<String, ArrayList<Objeto>> objetos;
	
	public Almacen(){
		this.objetos = new HashMap<String, ArrayList<Objeto>>();
	}

	public HashMap<String, ArrayList<Objeto>> getObjetos() {
		return objetos;
	}

	public void setObjetos(HashMap<String, ArrayList<Objeto>> objetos) {
		this.objetos = objetos;
	}
	
	public void añadirObjeto(String tipo, Objeto objeto){		
		if(this.objetos.get(tipo).isEmpty()){
			this.objetos.put(tipo, new ArrayList<Objeto>());
		}
		ArrayList<Objeto> aux = this.objetos.get(tipo);
		aux.add(objeto);
		this.objetos.put(tipo, aux);
	}
	
	public String toString(){
		String mensaje = "";
		
		for(Objeto s : this.objetos.get("consumible")){
			mensaje = mensaje + s.toString();
		}
		
		for(Objeto s : this.objetos.get("clave")){
			mensaje = mensaje + s.toString();
		}
		
		return mensaje;
	}

}
