package mundo;

import java.util.ArrayList;

import objetos.Objeto;

public class Mapa {

	private ArrayList<Localizacion> localizaciones;

	public Mapa() {
		this.localizaciones = new ArrayList<Localizacion>();
	}

	public Localizacion añadirLocalizacion(String loc, String tipo, Objeto obj) throws Exception {

		if ( !localizaciones.contains(getLocalizacion(loc)) ) {
			Localizacion localizacion = new Localizacion(loc, tipo);
			if(obj != null){
				localizacion.añadirObjeto(obj);
			}			
			this.localizaciones.add(localizacion);
			return localizacion;
			
		} else
			throw new Exception("Hay localizaciones repetidas");
	}

	public Localizacion getLocalizacion(String loc) {

		Localizacion localizacion = null;

		for (Localizacion localiz : localizaciones) {

			if (loc.equalsIgnoreCase(localiz.getNombre())) {
				localizacion = localiz;
				break;
			}
		}

		return localizacion;
	}

}
