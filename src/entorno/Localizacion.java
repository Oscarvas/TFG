package entorno;

import java.util.ArrayList;
public class Localizacion {
	
	private String nombre;
	private ArrayList<String> conectadoCon;	
	private ArrayList<String> personajes;

	public Localizacion (String nombre) {
		this.nombre = nombre;
		this.conectadoCon = new ArrayList<String>();
		this.personajes = new ArrayList<String>();
	}
	
	public void añadirConectado (String localizacion) {		
		if ( !conectadoCon.contains(localizacion) )
			conectadoCon.add(localizacion); 	
	}
	
	public boolean existeConexion (String localizacion) {
		for (String conectado : conectadoCon){
			if ( localizacion.equalsIgnoreCase(conectado) ) 
				return true;
		}		
		return false;
	}	
	
	public void añadirPersonaje (String personaje){		
		personajes.add(personaje);		
	}
	
	public boolean eliminarPersonaje (String personaje) {		
		return personajes.remove(personaje);		
	}

	public String getNombre() {return nombre;}
	
}

