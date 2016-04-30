package mundo;

import java.util.ArrayList;

import objetos.Objeto;
public class Localizacion {
	
	private String nombre;
	private String tipo;
	private ArrayList<String> conectadoCon;	
	private ArrayList<String> personajes;
	private ArrayList<Objeto> cofre;

	public Localizacion (String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.conectadoCon = new ArrayList<String>();
		this.personajes = new ArrayList<String>();
		this.cofre = new ArrayList<Objeto>();		
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
	
	public String getTipo() {return tipo;}
	
	public void añadirObjeto(Objeto o){
		this.cofre.add(o);
	}
	
	public boolean cofreVacio(){
		return this.cofre.isEmpty();
	}

	public ArrayList<Objeto> getCofre() {
		return this.cofre;
	}
	
	
	
}

