package mundo;

import java.util.ArrayList;

import objetos.Almacen;
import objetos.Objeto;
public class Localizacion {
	
	private String nombre;
	private String tipo;
	private ArrayList<String> conectadoCon;	
	private ArrayList<String> personajes;
	private Almacen cofre;

	public Localizacion (String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.conectadoCon = new ArrayList<String>();
		this.personajes = new ArrayList<String>();
		this.cofre = new Almacen();		
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
	
	public void añadirObjeto(String tipo, Objeto objeto){
		this.cofre.añadirObjeto(tipo, objeto);
	}
	
	public boolean cofreVacio(String tipo){
		return this.cofre.hayObjetos(tipo);
	}

	public Objeto abrirCofre(String tipo) {		
		return this.cofre.extraerObjeto(tipo,this.cofre.ultimoObjeto(tipo));
	}
	
	
	
}

