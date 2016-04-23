package mundo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Estado {

	private HashMap<String, String> objetivos; // <NombrePersonaje,objetivo>
	private HashMap<String, ArrayList<String>> adyacencias;
	private ArrayList<String> locSeguras;
	private HashMap<String, ArrayList<String>> personajes; //<clase, arrayPersonajesDeEsaClase>
	private HashMap<String, String> persEnLoc; // <personaje, localizacion>
	private ArrayList<String> vivos;
	private ArrayList<String> estaLibre;
	private HashMap<String, String> personajeConPrincesa;
	private ArrayList<String> princesasSecuestradas;
	private ArrayList<String> todosNombres;
	private HashMap<String, String> casasDePersonajes;
	private ArrayList<String> princesasSalvadas;
	private ArrayList<String> heroes;
	private HashMap<String, String> princesaObjetivo; //<Secuestrador/salvador,objetivoSecuestro>
	private ArrayList<String> cansado; //flag para el pddl,. Ha realizado alguna accion que no le deja moverse solo
	

	public Estado() {
		
		adyacencias = new HashMap<String, ArrayList<String>>();
		locSeguras = new ArrayList<String>();
		personajes = new HashMap<String, ArrayList<String>>();
		persEnLoc = new HashMap<String, String>();
		vivos = new ArrayList<String>();
		estaLibre = new ArrayList<String>();
		personajeConPrincesa = new HashMap<String, String>();
		princesasSecuestradas = new ArrayList<String>();
		todosNombres = new ArrayList<String>();
		casasDePersonajes = new HashMap<String, String>();
		princesasSalvadas = new ArrayList<String>();
		heroes = new  ArrayList<String>();
		princesaObjetivo = new HashMap<String, String>();
		objetivos = new HashMap<String, String>();
		cansado = new ArrayList<String>();
	}
	
	
	public String toConPrincesa() {
		String eh = "";
		Iterator<?> it;
		it = personajeConPrincesa.entrySet().iterator();
		
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) it.next();
			
			eh += "(conPrinc " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		return eh;
		
	}
	
	
	public void añadirAdyacente(String localizacion, String conectadoCon) {
		
		ArrayList<String> estaConectadoCon;
		
		if ( adyacencias.get(localizacion) == null )
			estaConectadoCon = new ArrayList<String>();
			
		else 		
			estaConectadoCon = adyacencias.get(localizacion);
	
		estaConectadoCon.add(conectadoCon);
		adyacencias.put(localizacion, estaConectadoCon);
	}
	
	
	public void borrarAdyacente(String localizacion, String conectadoCon) {
		
		ArrayList<String> estaConectadoCon = adyacencias.get(localizacion);
		
		if ( estaConectadoCon.size() == 1 )
			adyacencias.remove(localizacion);
		
		else {
			estaConectadoCon.remove(conectadoCon);
			adyacencias.put(localizacion, estaConectadoCon);
		}
	}

	
	public void esSegura(String nombre) {
		
		locSeguras.add(nombre);
	}
	
	
	public void añadirPersonaje(String clase, String nombre) {
		
		ArrayList<String> nombres;
		
		if ( personajes.get(clase) == null )
			nombres = new ArrayList<String>();
			
		else 		
			nombres = personajes.get(clase);
	
		if (clase.equalsIgnoreCase("Dragon") || clase.equalsIgnoreCase("Caballero") || clase.equalsIgnoreCase("Villano"))
			estaLibrePersonaje(nombre);
			
		nombres.add(nombre);
		personajes.put(clase, nombres);
		añadeVivo(nombre);

	}
		
	
	public void añadirLocalizacion(String personaje, String loc) {
		persEnLoc.put(personaje, loc);//nombreCorrecto(loc)
	}
	
	
	public void borrarLocalizacion(String personaje) {
		persEnLoc.remove(personaje);
	}
	
	public boolean estanMismaLocalizacion(String pers1, String pers2) {
		return persEnLoc.get(pers1).equalsIgnoreCase(persEnLoc.get(nombreCorrecto(pers2)));
		
	}
	
	public String getLocalizacion (String personaje){
		return persEnLoc.get(personaje);		
	}
	
	public void añadeVivo(String nombre) {
		vivos.add(nombre);
	}
	
	
	public void mata(String personaje) {
		vivos.remove(personaje);
	}
	
	
	public void estaLibrePersonaje (String personaje) {
		estaLibre.add(personaje);
	}
	
	public void estaCansado (String personaje) {
		cansado.add(personaje);
	}
	
	public void estaLlenoPersonaje (String personaje) {
		estaLibre.remove(personaje);
	}
	
	
	public void añadirPersonajeConPrincesa (String personaje, String princesa) {
		personajeConPrincesa.put(nombreCorrecto(personaje), nombreCorrecto(princesa));
	}
	
	
	public void borrarPersonajeConPrincesa (String personaje) {
		personajeConPrincesa.remove(nombreCorrecto(personaje));
	}
	

	public void secuestrar(String princesa) {
		princesasSecuestradas.add(nombreCorrecto(princesa));
	}
	
	
	public void liberar(String princesa) {
		princesasSecuestradas.remove(princesa);
	}
	
	
	public void añadirNombre(String nombre) {
		if ( !todosNombres.contains(nombre) ) 
			todosNombres.add(nombre);
		
	}
	
	public void añadirCasa (String nombre, String loc) {
		casasDePersonajes.put(nombre, loc);
	}
	
	public void añadirPrincesaSalvada (String princesa) {
		princesasSalvadas.add(princesa);
	}
	
	public void eliminarPrincesaSalvada (String princesa) {
		princesasSalvadas.remove(princesa);
	}
	
	public void añadirHeroe (String caballero) {
		heroes.add(caballero);
	}
	
	public void eliminarHeroe (String caballero) {
		heroes.remove(caballero);
	}
	
	public String nombreCorrecto(String nombre) {
		
		for ( String nombrePersonaje : todosNombres )
			if ( nombrePersonaje.equalsIgnoreCase(nombre) )
				return nombrePersonaje;
		
		return null;
	}
	
	
	public String nombresToString() {
		
		String nombres = "";
		
		for ( String nombre : todosNombres )
				nombres += nombre + "\n";
		
		return nombres;
	}

	
	@SuppressWarnings("rawtypes")
	public String toString(String nombrePersonaje) {
		
		String estado = "";
		Iterator<?> it;
		
		
		// Adyacencias
		
		it = adyacencias.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String[] valor = e.getValue().toString().replace("[", "").replace("]", "").replace(",", "").split(" ");
			
			for ( String adyacenteCon : valor ) 
				estado += "(adyacente " + e.getKey() + " " + adyacenteCon + ")\n";
		}
			
		
		// Localizaciones Seguras
		
		for ( String loc : locSeguras) 
			estado += "(locSegura " + loc + ")\n";
		
		
		// Localización de personajes
		
		it = persEnLoc.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			
			if (personajes.get("Princesa").contains(nombrePersonaje)){
				if (nombrePersonaje.equals(e.getKey()))
					estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
			}				
			else{
				if (!personajes.get("Caballero").contains(nombrePersonaje))//si no soy un caballero				
					estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
				else{
					if (nombrePersonaje.equals(e.getKey()) || !personajes.get("Caballero").contains(e.getKey()))
						estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
				}
			}
			
		}

		
		// Hogar / Guarida de cada Personaje
		
		it = casasDePersonajes.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			
			if ( personajes.get("Dragon").contains(e.getKey().toString()) )
				estado += "(esGuarida ";
			
			else
				estado += "(esCasa ";
			
			estado += e.getKey() + " " + e.getValue() + ")\n";
		}
		
		
		// Pseudónimos de personajes
		
		it = personajes.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String[] nombres = e.getValue().toString().replace("[", "").replace("]", "").replace(",", "").split(" ");
			
			for ( String nombre : nombres) {
				estado += "(es" + e.getKey() + " " + nombre + ")\n";
				
				if ( e.getKey().toString().equalsIgnoreCase("Princesa") || e.getKey().toString().equalsIgnoreCase("Rey") )
					estado += "(esPrincipal ";
				
				else	
					estado += "(esSecundario ";
				
				estado += nombre + ")\n";
			}
		}
		
		
		// Personajes libres
		
		for ( String personaje : estaLibre )
			estado += "(estaLibre " + personaje + ")\n";

		//Personajes cansados
		for ( String personaje : cansado )
			estado += "(cansado " + personaje + ")\n";
		
		// Personajes vivos o muertos
		
		for ( String personaje : vivos )
			estado += "(vivo " + personaje + ")\n";
		
		
		// Princesas salvadas

		for ( String princesa : princesasSalvadas )
			estado += "(salvada " + princesa + ")\n";
		
		
		// Caballeros que han llegado a ser Heroes
		
		for ( String heroe : heroes )
			estado += "(esHeroe " + heroe + ")\n";
		
		// Si un personaje está con la princesa
		
		it = personajeConPrincesa.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			
			estado += "(conPrinc " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		
		// Si está secuestrada la princesa
		
		for (String princesa : princesasSecuestradas)
			estado += "(secuestrada " + princesa + ")\n";
		
		return estado;
		
	}

	public String getPrincesaObjetivo(String secuestrador) {
		return princesaObjetivo.get(secuestrador);
	}

	public void setPrincesaObjetivo(String secuestrador,String princesaObjetivo) {
		this.princesaObjetivo.put(secuestrador, princesaObjetivo);
	}


	public String getObjetivos(String personaje) {
		return objetivos.get(personaje);
	}


	public void setObjetivos(String personaje, String objetivos) {
		this.objetivos.put(personaje, objetivos);
	}
}
