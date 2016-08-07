package mundo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import loaders.LoaderObjetos;
import objetos.Almacen;
import objetos.Objeto;

public class Estado {

	private HashMap<String, String> objetivos; // <NombrePersonaje,objetivo>
	private HashMap<String, ArrayList<String>> adyacencias;
	private ArrayList<String> locSeguras;
	private HashMap<String, ArrayList<String>> personajes; // <clase,
															// arrayPersonajesDeEsaClase>
	private HashMap<String, String> persEnLoc; // <personaje, localizacion>
	private ArrayList<String> vivos;
	private ArrayList<String> estaLibre;
	private HashMap<String, String> personajeConVictima;
	private ArrayList<String> victimasSecuestradas;
	private ArrayList<String> todosNombres;
	private HashMap<String, String> casasDePersonajes;
	private ArrayList<String> victimasSalvadas;
	private ArrayList<String> heroes;
	private HashMap<String, String> victimaObjetivo; // <Secuestrador/salvador,objetivoSecuestro>
	private ArrayList<String> cansado; // flag para el pddl,. Ha realizado alguna accion que no le deja moverse solo
	private Almacen almacen;
	private HashMap<String, String> objetoEnLoc; // <nombreObjetoClave, localizacion>
	private ArrayList<String> consumidor;//guardara las clases que puden usar objetos consumibles
	private ArrayList<Objeto> objetosClave; //guarda una copia de los objetos clave
	private ArrayList<String> atracable; //guarda las clases susceptible a interactuar con el ladron
	
	public Estado() {

		this.adyacencias = new HashMap<String, ArrayList<String>>();
		this.locSeguras = new ArrayList<String>();
		this.personajes = new HashMap<String, ArrayList<String>>();
		this.persEnLoc = new HashMap<String, String>();
		this.vivos = new ArrayList<String>();
		this.estaLibre = new ArrayList<String>();
		this.personajeConVictima = new HashMap<String, String>();
		this.victimasSecuestradas = new ArrayList<String>();
		this.todosNombres = new ArrayList<String>();
		this.casasDePersonajes = new HashMap<String, String>();
		this.victimasSalvadas = new ArrayList<String>();
		this.heroes = new ArrayList<String>();
		this.victimaObjetivo = new HashMap<String, String>();
		this.objetivos = new HashMap<String, String>();
		this.cansado = new ArrayList<String>();
		this.almacen = LoaderObjetos.loaderObjetos();
		this.objetoEnLoc = new HashMap<String, String>();
		this.consumidor = new ArrayList<String>();
		this.objetosClave = new ArrayList<Objeto>();
		this.atracable = new ArrayList<String>();
	}
	public void guardaClave(Objeto obj){
		this.objetosClave.add(obj);
	}
	
	public Objeto extraerObjeto(String nombreObj){
		Objeto ret = null;
		for (Objeto obj : this.objetosClave)
			if (obj.getId().equalsIgnoreCase(nombreObj))
				ret = obj;
		
		return ret;
		
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public String toConVictima() {
		String eh = "";
		Iterator<?> it;
		it = personajeConVictima.entrySet().iterator();

		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) it.next();

			eh += "(conPrinc " + e.getKey() + " " + e.getValue() + ")\n";
		}

		return eh;

	}

	public void añadirAdyacente(String localizacion, String conectadoCon) {

		ArrayList<String> estaConectadoCon;

		if (adyacencias.get(localizacion) == null)
			estaConectadoCon = new ArrayList<String>();

		else
			estaConectadoCon = adyacencias.get(localizacion);

		estaConectadoCon.add(conectadoCon);
		adyacencias.put(localizacion, estaConectadoCon);
	}

	public void borrarAdyacente(String localizacion, String conectadoCon) {

		ArrayList<String> estaConectadoCon = adyacencias.get(localizacion);

		if (estaConectadoCon.size() == 1)
			adyacencias.remove(localizacion);

		else {
			estaConectadoCon.remove(conectadoCon);
			adyacencias.put(localizacion, estaConectadoCon);
		}
	}

	public void esSegura(String nombre) {

		locSeguras.add(nombre);
	}
	
	public boolean esConsumidor(String clase){
		return this.consumidor.contains(clase);		
	}
	
	public boolean esAtracable(String clase){
		return this.atracable.contains(clase);		
	}

	public void añadirPersonaje(String clase, String nombre) {

		ArrayList<String> nombres;

		if (personajes.get(clase) == null)
			nombres = new ArrayList<String>();

		else
			nombres = personajes.get(clase);

		if (!clase.equalsIgnoreCase("Rey") && !clase.equalsIgnoreCase("Victima"))
			estaLibrePersonaje(nombre);

//		if (clase.equalsIgnoreCase("Rey") || clase.equalsIgnoreCase("Victima") 
//				|| clase.equalsIgnoreCase("Ayudante") || clase.equalsIgnoreCase("Aspirante") || clase.equalsIgnoreCase("Druida"))
//			consumidor.add(clase);
		if ( clase.equalsIgnoreCase("Ayudante") || clase.equalsIgnoreCase("Aspirante"))
			consumidor.add(clase);
		
		if (clase.equalsIgnoreCase("Aspirante"))
			atracable.add(clase);
		
		nombres.add(nombre);
		personajes.put(clase, nombres);
		añadeVivo(nombre);

	}

	public void añadirLocalizacion(String personaje, String loc) {
		persEnLoc.put(personaje, loc);// nombreCorrecto(loc)
	}

	public void borrarLocalizacion(String personaje) {
		persEnLoc.remove(personaje);
	}

	public boolean estanMismaLocalizacion(String pers1, String pers2) {
		return persEnLoc.get(pers1).equalsIgnoreCase(persEnLoc.get(nombreCorrecto(pers2)));

	}

	public String getLocalizacion(String personaje) {
		return persEnLoc.get(personaje);
	}

	public void añadeVivo(String nombre) {
		vivos.add(nombre);
	}

	public void mata(String personaje) {
		vivos.remove(personaje);
	}

	public void estaLibrePersonaje(String personaje) {
		estaLibre.add(personaje);
	}

	public void estaCansado(String personaje) {
		cansado.add(personaje);
	}

	public void estaLlenoPersonaje(String personaje) {
		estaLibre.remove(personaje);
	}

	public void añadirPersonajeConVictima(String personaje, String victima) {
		personajeConVictima.put(nombreCorrecto(personaje), nombreCorrecto(victima));
	}

	public void borrarPersonajeConVictima(String personaje) {
		personajeConVictima.remove(nombreCorrecto(personaje));
	}

	public void secuestrar(String victima) {
		victimasSecuestradas.add(nombreCorrecto(victima));
	}

	public void liberar(String victima) {
		victimasSecuestradas.remove(victima);
	}

	public void añadirNombre(String nombre) {
		if (!todosNombres.contains(nombre))
			todosNombres.add(nombre);

	}

	public void añadirCasa(String nombre, String loc) {
		casasDePersonajes.put(nombre, loc);
	}

	public void añadirVictimaSalvada(String victima) {
		victimasSalvadas.add(victima);
	}

	public void eliminarVictimaSalvada(String victima) {
		victimasSalvadas.remove(victima);
	}

	public void añadirHeroe(String aspirante) {
		heroes.add(aspirante);
	}

	public void eliminarHeroe(String aspirante) {
		heroes.remove(aspirante);
	}

	public String nombreCorrecto(String nombre) {

		for (String nombrePersonaje : todosNombres)
			if (nombrePersonaje.equalsIgnoreCase(nombre))
				return nombrePersonaje;

		return null;
	}

	public String nombresToString() {

		String nombres = "";

		for (String nombre : todosNombres)
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

			for (String adyacenteCon : valor)
				estado += "(adyacente " + e.getKey() + " " + adyacenteCon + ")\n";
		}

		// Localización de personajes

		it = persEnLoc.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			if (personajes.get("Victima").contains(nombrePersonaje)) {
				if (nombrePersonaje.equals(e.getKey()))
					estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
			} else {
				if (!personajes.get("Aspirante").contains(nombrePersonaje))// si
																			// no
																			// soy
																			// un
																			// aspirante
					estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
				else {
					if (nombrePersonaje.equals(e.getKey()) || !personajes.get("Aspirante").contains(e.getKey()))
						estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";
				}
			}

		}
		// Localizaciones de objetos
		
		it = objetoEnLoc.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			estado += "(objetoEnLoc " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		
		// Hogar / Guarida de cada Personaje

		it = casasDePersonajes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			if (personajes.get("Secuestrador").contains(e.getKey().toString()) || personajes.get("Guardian").contains(e.getKey().toString()))
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

			for (String nombre : nombres) {
				estado += "(es" + e.getKey() + " " + nombre + ")\n";

				if (e.getKey().toString().equalsIgnoreCase("Victima") || e.getKey().toString().equalsIgnoreCase("Rey"))
					estado += "(esPrincipal ";

				else
					estado += "(esSecundario ";

				estado += nombre + ")\n";
			}
		}

		// Personajes libres

		for (String personaje : estaLibre)
			estado += "(estaLibre " + personaje + ")\n";

		// Personajes cansados
		for (String personaje : cansado)
			estado += "(cansado " + personaje + ")\n";

		// Personajes vivos o muertos

		for (String personaje : vivos)
			estado += "(vivo " + personaje + ")\n";

		// Victimas salvadas

		for (String victima : victimasSalvadas)
			estado += "(salvada " + victima + ")\n";

		// Aspirantes que han llegado a ser Heroes

		for (String heroe : heroes)
			estado += "(esHeroe " + heroe + ")\n";

		// Si un personaje está con la victima

		it = personajeConVictima.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			estado += "(conPrinc " + e.getKey() + " " + e.getValue() + ")\n";
		}

		// Si está secuestrada la victima

		for (String victima : victimasSecuestradas)
			estado += "(secuestrada " + victima + ")\n";

		return estado;

	}

	public String getVictimaObjetivo(String secuestrador) {
		return victimaObjetivo.get(secuestrador);
	}

	public void setVictimaObjetivo(String secuestrador, String victimaObjetivo) {
		this.victimaObjetivo.put(secuestrador, victimaObjetivo);
	}

	public String getObjetivos(String personaje) {
		return objetivos.get(personaje);
	}

	public void setObjetivos(String personaje, String objetivos) {
		this.objetivos.put(personaje, objetivos);
	}

	public String getObjetoEnLoc(String objeto) {
		return this.objetoEnLoc.get(objeto);
	}

	public void setObjetoEnLoc(String objeto, String localizacion) {
		this.objetoEnLoc.put(objeto, localizacion);
	}
}
