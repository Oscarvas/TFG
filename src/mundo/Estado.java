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
	 * Asigna un objeto a un personaje
	 * 
	 * @param 
	 * 
	 */
	public void guardaObjeto(String personaje, String objeto){
		poseeObjeto.put(personaje, objeto);
	}
	
	/**
	 * Elimina el objeto de un personaje
	 * 
	 * @param 
	 *  
	 */
	public void pierdeObjeto(String personaje){
		poseeObjeto.remove(personaje);
	}
	
	/**
	 * Busca que objeto tiene un personaje
	 * 
	 * @param 
	 * @return El objeto del personaje
	 */
	public String tieneObjeto(String personaje){
		return poseeObjeto.get(personaje);
	}
	
	/**
	 * Guarda un nuevo objeto clave 
	 * 
	 * @param 
	 * @return 
	 */
	public void guardaClave(Objeto obj){
		this.objetosClave.add(obj);
	}
	
	/**
	 * Busca y elimina un objeto para asignarselo a un personaje
	 * 
	 * @param 
	 * @return el objeto, null si no se ha encontrado
	 */
	public Objeto extraerObjeto(String nombreObj){
		Objeto ret = null;
		for (Objeto obj : this.objetosClave)
			if (obj.getId().equalsIgnoreCase(nombreObj))
				ret = obj;
		
		return ret;
		
	}

	/**
	 * Devuelve el almacen de objetos
	 * 
	 * @param 
	 * @return el almacen
	 */
	public Almacen getAlmacen() {
		return almacen;
	}

	/**
	 * Asigna un almacen al estado
	 * 
	 * @param 
	 * 
	 */
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}


	/**
	 * Conecta dos localizaciones, ademas asigna nueva localizacion a la lista
	 * de adyacentes de una localizacion
	 * 
	 * @param 
	 *  
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
	 * Elimina la conexion entre dos localizaciones
	 * 
	 * @param 
	 * 
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
	 * Indica si la clase especificada esta en la lista de clases que
	 * pueden interactuar con objetos y PNJs (es consumidora)
	 * 
	 * @param 
	 * @return true si la clase es consumidora , false en caso contrario
	 */
	public boolean esConsumidor(String clase){
		return this.consumidor.contains(clase);		
	}
	
	/**
	 * Se usa para verificar si esa clase dispara el evento con el ladron
	 * 
	 * @param 
	 * @return true si la clase se ha encontrado, false en otro caso
	 */
	public boolean esAtracable(String clase){
		return this.atracable.contains(clase);		
	}

	/**
	 * Incorpora la informacion de la clase y nombre de un nuevo personaje.
	 * Inicializa valores como:
	 * 	consumidor
	 * 	estaLibre
	 * 	atracable
	 * 	vivo
	 * 
	 * @param 
	 * 
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
	 * Indica que un personaje esta en la localizacion especificada
	 * 
	 * @param 
	 *  
	 */
	public void añadirLocalizacion(String personaje, String loc) {
		persEnLoc.put(personaje, loc);// nombreCorrecto(loc)
	}

	/**
	 * Elimina a un personaje de su localizacion actual
	 * 
	 * @param 
	 *  
	 */
	public void borrarLocalizacion(String personaje) {
		persEnLoc.remove(personaje);
	}

	/**
	 * Evalua que dos personajes esten en la misma localizacion
	 * 
	 * @param 
	 * @return true si ambos en la misma localizacion, false en otro caso
	 */
	public boolean estanMismaLocalizacion(String pers1, String pers2) {
		return persEnLoc.get(pers1).equalsIgnoreCase(persEnLoc.get(nombreCorrecto(pers2)));

	}

	/**
	 * Devuelve la localizacion actual de un personaje
	 * 
	 * @param 
	 * @return String nombre de la locaclizacion del personaje
	 */
	public String getLocalizacion(String personaje) {
		return persEnLoc.get(personaje);
	}

	/**
	 * Agrega personaje a la lista de vivos
	 * 
	 * @param 
	 * 
	 */
	public void añadeVivo(String nombre) {
		vivos.add(nombre);
	}

	/**
	 * Elimina a un personaje de la lista de vivos
	 *  
	 * @param 
	 * 
	 */
	public void mata(String personaje) {
		vivos.remove(personaje);
	}

	/**
	 * Agrega un personaje a la lista de personajes libres
	 * 
	 * @param 
	 * 
	 */
	public void estaLibrePersonaje(String personaje) {
		estaLibre.add(personaje);
	}

	/**
	 * Agrega un personaje a la lista de personajes cansados
	 * 
	 * @param 
	 * 
	 */
	public void estaCansado(String personaje) {
		cansado.add(personaje);
	}

	/**
	 * Elimina un personaje de la lista de personajes libres
	 * 
	 * @param 
	 *  
	 */
	public void estaLlenoPersonaje(String personaje) {
		estaLibre.remove(personaje);
	}

	/**
	 * Indica que un personaje y la victima estan juntos 
	 * 
	 * @param 
	 *  
	 */
	public void añadirPersonajeConVictima(String personaje, String victima) {
		personajeConVictima.put(nombreCorrecto(personaje), nombreCorrecto(victima));
	}

	/**
	 * Indica que el personaje y la victima ya no estan juntos
	 * 
	 * @param 
	 *  
	 */
	public void borrarPersonajeConVictima(String personaje) {
		personajeConVictima.remove(nombreCorrecto(personaje));
	}

	/**
	 * Indica que la victima ha sido secuestrada
	 * 
	 * @param 
	 *  
	 */
	public void secuestrar(String victima) {
		victimasSecuestradas.add(nombreCorrecto(victima));
	}

	/**
	 * Elimina a la victima de la lista de victimas secuestradas
	 * 
	 * @param 
	 *  
	 */
	public void liberar(String victima) {
		victimasSecuestradas.remove(victima);
	}

	/**
	 * Agrega el nombre del nuevo agente a la lista para tener un registro
	 * de todas las entidades que maneja el sistema
	 * 
	 * @param 
	 *  
	 */
	public void añadirNombre(String nombre) {
		if (!todosNombres.contains(nombre))
			todosNombres.add(nombre);

	}

	/**
	 * Asigna una localizacion inicial a un personaje
	 * 
	 * @param 
	 * 
	 */
	public void añadirCasa(String nombre, String loc) {
		casasDePersonajes.put(nombre, loc);
	}

	/**
	 * Agrega la victima a la lista de victimas salvadas
	 * 
	 * @param 
	 *  
	 */
	public void añadirVictimaSalvada(String victima) {
		victimasSalvadas.add(victima);
	}

	/**
	 * Elimina a la victima de la lista de victimas salvadas
	 * 
	 * @param 
	 *  
	 */
	public void eliminarVictimaSalvada(String victima) {
		victimasSalvadas.remove(victima);
	}

	/**
	 * Agrega un ayudante a la lista de sabios
	 * 
	 * @param 
	 *  
	 */
	public void añadirSabio(String ayudante) {
		sabios.add(ayudante);
	}

	/**
	 * Elimina a un ayudante de la lista de sabios
	 * 
	 * @param 
	 *  
	 */
	public void eliminarSabio(String ayudante) {
		sabios.remove(ayudante);
	}
	
	/**
	 * Agrega un aspirante a la lista de heroes
	 * 
	 * @param 
	 *  
	 */
	public void añadirHeroe(String aspirante) {
		heroes.add(aspirante);
	}

	/**
	 * Elimina un aspirante de la lista de heroes
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarHeroe(String aspirante) {
		heroes.remove(aspirante);
	}
	
	/**
	 * Agrega un aspirante a la lista de villanos
	 * 
	 * @param 
	 * @return 
	 */
	public void añadirVillano(String aspirante) {
		villanos.add(aspirante);
	}
	
	/**
	 * Elimina un aspirante de la lñista de villanos
	 * 
	 * @param 
	 * @return 
	 */
	public void eliminarVillano(String aspirante) {
		villanos.remove(aspirante);
	}

	/**
	 * Obtiene el nombre inicial con el que se registro 
	 * en un principio la entidad en el sistema
	 * 
	 * @param 
	 * @return String con el nombre original, null si no se ha encontrado
	 */
	public String nombreCorrecto(String nombre) {

		for (String nombrePersonaje : todosNombres)
			if (nombrePersonaje.equalsIgnoreCase(nombre))
				return nombrePersonaje;

		return null;
	}
	
	/**
	 * Devuelve una lista de nombres de todas las entidades del sistema
	 * 
	 * @param 
	 * @return String con todos los nombres de entidades 
	 */
	public String nombresToString() {

		String nombres = "";

		for (String nombre : todosNombres)
			nombres += nombre + "\n";

		return nombres;
	}
	
	/**
	 * Devuelve un String con la lista de adyacencias
	 * 
	 * @param 
	 * @return String con la lista de adyacencias
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
	 * Para cada personaje de la lista
	 * Devuelve String indicando que un personaje esta en cierta localizacion
	 * 
	 * @param lista con nombre de personajes
	 * @return String indicando que un personaje esta en cierta localizacion
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
	 * Devuelve String lista con las localizaciones actuales de cada objeto
	 * 
	 * @param 
	 * @return String lista con las localizaciones actuales de cada objeto
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
	 * Devuelve string lista indicando guarida o casa de un personaje en funcion del tipo
	 * 
	 * @param lista de personajes
	 * @return Lista de guaridas y casas de personajes
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
	 * Indica si un personaje es principal o secundario
	 * 
	 * @param lista de personajes
	 * @return String lista de personajes principales y secundarios
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
	 * Indica si el personaje esta libre
	 * 
	 * @param lista de personajes
	 * @return String lista de personajes libres
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
	 * Indica si un personaje esta cansado
	 * 
	 * @param lista de personajes
	 * @return String lista de personajes cansados
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
	 * Indica si un personaje esta vivo
	 * 
	 * @param lista de personajes
	 * @return String lista de personajes vivos
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
	 * Indica todas las victimas que han sido salvadas
	 * 
	 * @param 
	 * @return String lista de victimas salvadas
	 */
	private String estaSalvada (){
		String estado = "";
		// Victimas salvadas

		for (String victima : victimasSalvadas)
			estado += "(salvada " + victima + ")\n";
		
		return estado;
	}
	
	/**
	 * Indica los aspirantes que han llegado a ser heroes 
	 * 
	 * @param 
	 * @return String lista de personajes heroes
	 */
	private String esHeroe (){
		String estado = "";
		// Aspirantes que han llegado a ser Heroes

		for (String heroe : heroes)
			estado += "(esHeroe " + heroe + ")\n";
		
		return estado;
	}
	
	
	/**
	 * Indica los personajes que son sabios
	 * 
	 * @param 
	 * @return String lista de personajes sabios
	 */
	private String esSabio (){
		String estado = "";
		// Ayudantes que han llegado a ser Sabios

		for (String heroe : heroes)
			estado += "(esSabio " + heroe + ")\n";
		
		return estado;
	}
	
	/**
	 * Indica los personajes que ha llegado a ser villanos
	 * 
	 * @param 
	 * @return String lista de personajes villanos
	 */
	private String esVillano (){
		String estado = "";
		// Aspirantes que han llegado a ser Villanos

		for (String heroe : heroes)
			estado += "(esVillano " + heroe + ")\n";
		
		return estado;
	}
	
	/**
	 * Indica personajes con victimas
	 * 
	 * @param 
	 * @return String lista de personajes con victimas
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
	 * Indica que objeto tiene un personaje
	 * 
	 * @param 
	 * @return String lista de personajes y que objeto tienen en caso de tenerlo
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
	 * Indica que princesas estan secuestradas
	 * 
	 * @param 
	 * @return String lista de princesas secuestradas
	 */
	private String estaSecuestrada (){
		String estado = "";		
		// Si está secuestrada la victima

		for (String victima : victimasSecuestradas)
			estado += "(secuestrada " + victima + ")\n";

		return estado;
	}

	/**
	 * Genera la informacion que debe conocer una victima en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para la victima
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
	 * Genera la informacion que debe conocer un aspirante en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el aspirante
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
	 * Genera la informacion que debe conocer un ayudante en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el ayudante
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
	 * Genera la informacion que debe conocer un secuestrador en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el secuestrador
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
	 * Genera la informacion que debe conocer un ladron en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el secuestrador
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
	 * Genera la informacion que debe conocer un emboscador en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el emboscador
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
	 * Genera la informacion que debe conocer un asesino en su pddl
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el asesino
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
	 * Genera la informacion especifica que debe conocer un personaje
	 * en fuincion del tipo de personaje que sea.
	 * Informacion para el PDDL problema
	 * 
	 * @param 
	 * @return String con la informacion filtrada para el personaje
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
	 * Indica que victima es objetivo del secuestrador
	 * 
	 * @param 
	 * @return nombre de la victima
	 */
	public String getVictimaObjetivo(String secuestrador) {
		return victimaObjetivo.get(secuestrador);
	}

	/**
	 * Asigna a un secuestrador una victima
	 * 
	 * @param 
	 *  
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
	 * Asigna a una victima, el aspirante que va a intentar ayudarla
	 * 
	 * @param 
	 */
	public void setAspirante(String victima, String aspirante) {
		this.victimaHeroe.put(victima, aspirante);
	}
	
	/**
	 * Devuelve los objetivos que se han cargado para un personaje
	 * 
	 * @param 
	 * @return String con los objetivos en formato PDDL del personaje
	 */
	public String getObjetivos(String personaje) {
		return objetivos.get(personaje);
	}

	/**
	 * Asigna unos objetivos a un personaje
	 * 
	 * @param 
	 */
	public void setObjetivos(String personaje, String objetivos) {
		this.objetivos.put(personaje, objetivos);
	}

	/**
	 * Dado un objeto , busca su localizacio actual
	 * 
	 * @param 
	 * @return String localizacion del objeto
	 */
	public String getObjetoEnLoc(String objeto) {
		return this.objetoEnLoc.get(objeto);
	}

	/**
	 * Asigna un objeto a una localizacion
	 * 
	 * @param 
	 *  
	 */
	public void setObjetoEnLoc(String objeto, String localizacion) {
		this.objetoEnLoc.put(objeto, localizacion);
	}

	/**
	 * Busca que pnj esta en cierta localizacion
	 * 
	 * @param 
	 * @return String nombre del pnj en esa localizacion
	 */
	public String getPnjEnLoc(String localizacion) {
		return this.pnjEnLoc.get(localizacion);
	}
	
	/**
	 * Asigna un pnj a una localizacion
	 * 
	 * @param 
	 *  
	 */
	public void setPnjEnLoc(String localizacion, String nombrePnj) {
		this.pnjEnLoc.put(localizacion, nombrePnj);
	}
}
