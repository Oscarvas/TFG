package objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class Almacen {
	// HashMap que guarda los objetos en funcion de su tipo. Ejemplo:
	// "consumible", Lista consumibles
	private HashMap<String, ArrayList<Objeto>> objetos;

	public Almacen() {
		this.objetos = new HashMap<String, ArrayList<Objeto>>();
	}

	public void añadirObjeto(String tipo, Objeto objeto) {
		if (this.objetos.get(tipo) == null) {
			this.objetos.put(tipo, new ArrayList<Objeto>());
		}
		ArrayList<Objeto> aux = this.objetos.get(tipo);
		aux.add(objeto);
		this.objetos.put(tipo, aux);
	}

	public Objeto extraerObjeto(String tipo, int index) {
		Objeto o = null;
		if (this.objetos.get(tipo) != null) {
			if (this.objetos.get(tipo).get(index) != null) {
				o = this.objetos.get(tipo).get(index);
				this.objetos.get(tipo).remove(index);
			}
		}
		return o;
	}

	public boolean hayObjetos(String tipo) {
		boolean hay = false;
		if (this.objetos.get(tipo) != null) {
			if (!this.objetos.get(tipo).isEmpty()) {
				hay = true;
			}
		}
		return hay;
	}

	/**
	 * Mira si hay objetos claves que se hayan guardado.
	 * 
	 * @param localizacion
	 * @return la posicion del objeto clave, -1 si no hay objeto clave.
	 */
	public int hayObjetoClave(String localizacion) {
		int i = 0;
		Clave c;
		Iterator it = (Iterator) this.objetos.get("clave").iterator();
		while (it.hasNext()) {
			c = (Clave) it.next();
			if (c.getLocalizacion().equals(localizacion)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public int consumibleAleatorio() {
		Random rnd = new Random();
		return (int) (rnd.nextDouble() * this.objetos.get("consumible").size());
	}

	public int ultimoObjeto(String tipo) {
		int i = -1;
		if (this.objetos.get(tipo) != null)
			i = this.objetos.get(tipo).size() - 1;
		return i;
	}

	public String toString() {
		String mensaje = "\nConsumibles:";
		for (Objeto s : this.objetos.get("consumible")) {
			mensaje = mensaje + "\n" + s.toString() + "\n";
		}
		mensaje = mensaje + "\nClave:";
		for (Objeto s : this.objetos.get("clave")) {
			mensaje = mensaje + "\n" + s.toString() + "\n";
		}

		return mensaje;
	}

}
