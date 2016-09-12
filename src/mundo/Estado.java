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
	private ArrayList<String> sabios;
	private ArrayList<String> villanos;
	private HashMap<String, String> victimaObjetivo; // <Secuestrador/salvador,objetivoSecuestro>
	private HashMap<String, String> victimaHeroe; // <Victima,heroe> par que determina el salvador dada una victima
	private ArrayList<String> cansado; // flag para el pddl,. Ha realizado alguna accion que no le deja moverse solo
	private Almacen almacen;
	private HashMap<String, String> objetoEnLoc; // <nombreObjetoClave, localizacion>
	private ArrayList<String> consumidor;//guardara las clases que puden usar objetos consumibles
	private ArrayList<Objeto> objetosClave; //guarda una copia de los objetos clave
	private ArrayList<String> atracable; //guarda las clases susceptible a interactuar con el ladron
	private HashMap<String, String> poseeObjeto; // <personaje, objeto> indica que objeto tiene un personaje dado 
	private HashMap<String, String> pnjEnLoc; // <localizacion, nombrePNJ> dada la localizacion devuelve el pnj
	
	
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
		this.sabios = new ArrayList<String>();
		this.villanos = new ArrayList<String>();
		this.victimaObjetivo = new HashMap<String, String>();
		this.victimaHeroe = new HashMap<String, String>();
		this.objetivos = new HashMap<String, String>();
		this.cansado = new ArrayList<String>();
		this.almacen = LoaderObjetos.loaderObjetos();
		this.objetoEnLoc = new HashMap<String, String>();
		this.consumidor = new ArrayList<String>();
		this.objetosClave = new ArrayList<Objeto>();
		this.atracable = new ArrayList<String>();
		this.poseeObjeto = new HashMap<String, String>();
		this.pnjEnLoc = new HashMap<String, String>();
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void guardaObjeto(String personaje, String objeto){
		poseeObjeto.put(personaje, objeto);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void pierdeObjeto(String personaje){
		poseeObjeto.remove(personaje);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String tieneObjeto(String personaje){
		return poseeObjeto.get(personaje);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void guardaClave(Objeto obj){
		this.objetosClave.add(obj);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public Objeto extraerObjeto(String nombreObj){
		Objeto ret = null;
		for (Objeto obj : this.objetosClave)
			if (obj.getId().equalsIgnoreCase(nombreObj))
				ret = obj;
		
		return ret;
		
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public Almacen getAlmacen() {
		return almacen;
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
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

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirAdyacente(String localizacion, String conectadoCon) {

		ArrayList<String> estaConectadoCon;

		if (adyacencias.get(localizacion) == null)
			estaConectadoCon = new ArrayList<String>();

		else
			estaConectadoCon = adyacencias.get(localizacion);

		estaConectadoCon.add(conectadoCon);
		adyacencias.put(localizacion, estaConectadoCon);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void borrarAdyacente(String localizacion, String conectadoCon) {

		ArrayList<String> estaConectadoCon = adyacencias.get(localizacion);

		if (estaConectadoCon.size() == 1)
			adyacencias.remove(localizacion);

		else {
			estaConectadoCon.remove(conectadoCon);
			adyacencias.put(localizacion, estaConectadoCon);
		}
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void esSegura(String nombre) {

		locSeguras.add(nombre);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public boolean esConsumidor(String clase){
		return this.consumidor.contains(clase);		
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public boolean esAtracable(String clase){
		return this.atracable.contains(clase);		
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirPersonaje(String clase, String nombre) {

		ArrayList<String> nombres;

		if (personajes.get(clase) == null)
			nombres = new ArrayList<String>();

		else
			nombres = personajes.get(clase);

		if (!clase.equalsIgnoreCase("Allegado") && !clase.equalsIgnoreCase("Victima"))
			estaLibrePersonaje(nombre);

		if ( clase.equalsIgnoreCase("Ayudante") || clase.equalsIgnoreCase("Aspirante"))
			consumidor.add(clase);
		
		if (clase.equalsIgnoreCase("Aspirante"))
			atracable.add(clase);
		
		nombres.add(nombre);
		personajes.put(clase, nombres);
		añadeVivo(nombre);

	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirLocalizacion(String personaje, String loc) {
		persEnLoc.put(personaje, loc);// nombreCorrecto(loc)
	}

	public void borrarLocalizacion(String personaje) {
		persEnLoc.remove(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public boolean estanMismaLocalizacion(String pers1, String pers2) {
		return persEnLoc.get(pers1).equalsIgnoreCase(persEnLoc.get(nombreCorrecto(pers2)));

	}

	public String getLocalizacion(String personaje) {
		return persEnLoc.get(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadeVivo(String nombre) {
		vivos.add(nombre);
	}

	public void mata(String personaje) {
		vivos.remove(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void estaLibrePersonaje(String personaje) {
		estaLibre.add(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void estaCansado(String personaje) {
		cansado.add(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void estaLlenoPersonaje(String personaje) {
		estaLibre.remove(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirPersonajeConVictima(String personaje, String victima) {
		personajeConVictima.put(nombreCorrecto(personaje), nombreCorrecto(victima));
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void borrarPersonajeConVictima(String personaje) {
		personajeConVictima.remove(nombreCorrecto(personaje));
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void secuestrar(String victima) {
		victimasSecuestradas.add(nombreCorrecto(victima));
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void liberar(String victima) {
		victimasSecuestradas.remove(victima);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirNombre(String nombre) {
		if (!todosNombres.contains(nombre))
			todosNombres.add(nombre);

	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirCasa(String nombre, String loc) {
		casasDePersonajes.put(nombre, loc);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirVictimaSalvada(String victima) {
		victimasSalvadas.add(victima);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarVictimaSalvada(String victima) {
		victimasSalvadas.remove(victima);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirSabio(String ayudante) {
		sabios.add(ayudante);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarSabio(String ayudante) {
		sabios.remove(ayudante);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirHeroe(String aspirante) {
		heroes.add(aspirante);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarHeroe(String aspirante) {
		heroes.remove(aspirante);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirVillano(String aspirante) {
		villanos.add(aspirante);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarVillano(String aspirante) {
		villanos.remove(aspirante);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String nombreCorrecto(String nombre) {

		for (String nombrePersonaje : todosNombres)
			if (nombrePersonaje.equalsIgnoreCase(nombre))
				return nombrePersonaje;

		return null;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String nombresToString() {

		String nombres = "";

		for (String nombre : todosNombres)
			nombres += nombre + "\n";

		return nombres;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String adyacente (){
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
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String enLoc (ArrayList<String> lista){
		String estado = "";
		Iterator<?> it;
		// Localización de personajes

		it = persEnLoc.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			
			if(lista.contains(e.getKey()))
				estado += "(enLoc " + e.getKey() + " " + e.getValue() + ")\n";

		}
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String objetoEnLoc(){
		String estado = "";
		Iterator<?> it;
		
		// Localizaciones de objetos
		
		it = objetoEnLoc.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			estado += "(objetoEnLoc " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String esHogar (ArrayList<String> lista){
		String estado = "";
		Iterator<?> it;
		
		// Hogar / Guarida de cada Personaje

		it = casasDePersonajes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			
			if(lista.contains(e.getKey())){
				if (personajes.get("Secuestrador").contains(e.getKey().toString()) || personajes.get("Ladron").contains(e.getKey().toString()))
//				if (personajes.get("Secuestrador").contains(e.getKey().toString()))	
					estado += "(esGuarida ";

				else
					estado += "(esCasa ";

				estado += e.getKey() + " " + e.getValue() + ")\n";
			}
		}
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String esPrincipal (ArrayList<String> lista){
		String estado = "";
		Iterator<?> it;
		
		// Pseudónimos de personajes

		it = personajes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String[] nombres = e.getValue().toString().replace("[", "").replace("]", "").replace(",", "").split(" ");

			for (String nombre : nombres) {				
				if(lista.contains(nombre)){
					estado += "(es" + e.getKey() + " " + nombre + ")\n";

					if (e.getKey().toString().equalsIgnoreCase("Victima") || e.getKey().toString().equalsIgnoreCase("Allegado"))
						estado += "(esPrincipal ";

					else
						estado += "(esSecundario ";

					estado += nombre + ")\n";
				}
			}
		}
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String estaLibre (ArrayList<String> lista){
		String estado = "";
		
		// Personajes libres

		for (String personaje : estaLibre)
			if(lista.contains(personaje))
				estado += "(estaLibre " + personaje + ")\n";

		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String estanCansados (ArrayList<String> lista){
		String estado = "";
		
		// Personajes cansados
		for (String personaje : cansado)
			if(lista.contains(personaje))
				estado += "(cansado " + personaje + ")\n";
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String estanVivos (ArrayList<String> lista){
		String estado = "";
		// Personajes vivos o muertos

		for (String personaje : vivos)
			if(lista.contains(personaje))
				estado += "(vivo " + personaje + ")\n";
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String estaSalvada (){
		String estado = "";
		// Victimas salvadas

		for (String victima : victimasSalvadas)
			estado += "(salvada " + victima + ")\n";
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String esHeroe (){
		String estado = "";
		// Aspirantes que han llegado a ser Heroes

		for (String heroe : heroes)
			estado += "(esHeroe " + heroe + ")\n";
		
		return estado;
	}
	
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String esSabio (){
		String estado = "";
		// Ayudantes que han llegado a ser Sabios

		for (String heroe : heroes)
			estado += "(esSabio " + heroe + ")\n";
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String esVillano (){
		String estado = "";
		// Aspirantes que han llegado a ser Villanos

		for (String heroe : heroes)
			estado += "(esVillano " + heroe + ")\n";
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */	
	@SuppressWarnings("rawtypes")
	private String conPrincesa (){
		String estado = "";
		Iterator<?> it;
		// Si un personaje está con la victima

		it = personajeConVictima.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			estado += "(conPrinc " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	private String conObjeto (){
		String estado = "";
		Iterator<?> it;
		// Personajes y objetos que tienen

		it = poseeObjeto.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			estado += "(conObjeto " + e.getKey() + " " + e.getValue() + ")\n";
		}
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String estaSecuestrada (){
		String estado = "";		
		// Si está secuestrada la victima

		for (String victima : victimasSecuestradas)
			estado += "(secuestrada " + victima + ")\n";

		return estado;
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoVictima(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=estaSalvada();
		estado+=conPrincesa();
		estado+=estaSecuestrada();
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoAspirante(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		lista.addAll(personajes.get("Secuestrador"));
		lista.addAll(personajes.get("Victima"));
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=estaSalvada();
		estado+=conPrincesa();
		estado+=estaSecuestrada();
		estado+=esHeroe();
		estado+=esVillano();
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoAyudante(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		lista.addAll(personajes.get("Ladron"));
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=esSabio();
		estado+=objetoEnLoc();
		estado+=conObjeto();
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoSecuestrador(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		lista.addAll(personajes.get("Victima"));
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=estaSalvada();
		estado+=conPrincesa();
		estado+=estaSecuestrada();
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoLadron(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=objetoEnLoc();
		estado+=conObjeto();
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoEmboscador(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	private String conocimientoAsesino(String nombre){
		ArrayList<String> lista = new ArrayList<String>();
		lista.add(nombre);
		lista.addAll(personajes.get("Allegado"));
		
		String estado = "";
		
		estado+=enLoc(lista);
		estado+=esHogar(lista);
		estado+=esPrincipal(lista);
		estado+=estaLibre(lista);
		estado+=estanVivos(lista);
		estado+=estanCansados(lista);
		
		return estado;
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String toString(String nombrePersonaje) {

		String estado = "";
				
		//Todos los personajes necesitan conocer el mapa de adyacencias
		estado+=adyacente();
		
		if(personajes.get("Victima").contains(nombrePersonaje))
			estado+=conocimientoVictima(nombrePersonaje);
		
		else if(personajes.get("Aspirante").contains(nombrePersonaje))
			estado+=conocimientoAspirante(nombrePersonaje);
		
		else if(personajes.get("Ayudante").contains(nombrePersonaje))
			estado+=conocimientoAyudante(nombrePersonaje);			
			
		else if(personajes.get("Secuestrador").contains(nombrePersonaje))
			estado+=conocimientoSecuestrador(nombrePersonaje);
		
		else if(personajes.get("Ladron").contains(nombrePersonaje))
			estado+=conocimientoLadron(nombrePersonaje);
		
		else if(personajes.get("Emboscador").contains(nombrePersonaje))
			estado+=conocimientoEmboscador(nombrePersonaje);
		
		else if(personajes.get("Asesino").contains(nombrePersonaje))
			estado+=conocimientoAsesino(nombrePersonaje);

		return estado;

	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String getVictimaObjetivo(String secuestrador) {
		return victimaObjetivo.get(secuestrador);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setVictimaObjetivo(String secuestrador, String victimaObjetivo) {
		this.victimaObjetivo.put(secuestrador, victimaObjetivo);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String getAspirante(String victima) {
		return victimaHeroe.get(victima);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setAspirante(String victima, String aspirante) {
		this.victimaHeroe.put(victima, aspirante);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String getObjetivos(String personaje) {
		return objetivos.get(personaje);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setObjetivos(String personaje, String objetivos) {
		this.objetivos.put(personaje, objetivos);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String getObjetoEnLoc(String objeto) {
		return this.objetoEnLoc.get(objeto);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setObjetoEnLoc(String objeto, String localizacion) {
		this.objetoEnLoc.put(objeto, localizacion);
	}

	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public String getPnjEnLoc(String localizacion) {
		return this.pnjEnLoc.get(localizacion);
	}
	
	/**
	 * Descripcion
	 * 
	 * @param 
	 * @return 
	 */
	public void setPnjEnLoc(String localizacion, String nombrePnj) {
		this.pnjEnLoc.put(localizacion, nombrePnj);
	}
}
