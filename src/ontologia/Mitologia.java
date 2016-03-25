package ontologia;

/*
 * Enumerado de las zonas
Esta clase guardara la referencia a la raza propia de cada region del mapa
asi como los atributos predefinidos de cada raza y que afectaran al desarrollo de cada personaje
durante su historia.
Formato:
	Zona	Raza	vida,fuerza,destreza,inteligencia,codicia	RegionesInternas
*/
public enum Mitologia {
	EGERIA	("Egerian"	,5,5,2,2,1,new String[]{"Turis", "Caligo", "Egeria"}),
	ILLUC	("Illidar"	,4,2,5,6,2,new String[]{"Sagren","Hospital","Illuc"}),
	TRAGUS	("Trag"		,1,6,4,3,4,new String[]{"Minas","Cuevas","Tragus"}),
	LUCTA	("Lucs"		,3,1,6,5,3,new String[]{"Segrex","Lucta","Biblioteca","Nordrassil"}),
	TESQUA	("Tesq"		,6,4,1,1,6,new String[]{"Tesqua","Taberna","Cruce","Fatum","Inibi","Egestas","Ignis","Illunis"}),
	SCRUTHOR("Scrull"	,2,3,3,4,5,new String[]{"Sastreria","Establos","Herreria","Echidna","Scruthor"});
	
	private final String raza;
	private final int vida;
	private final int fuerza;
	private final int destreza;
	private final int inteligencia;
	private final int codicia;
	private final String[] regiones;
	
	Mitologia(String raza,int vida,int fuerza, int destreza, int inteligencia, int codicia,String[] regiones){
		this.raza = raza;
		this.vida = vida;
		this.fuerza = fuerza;
		this.destreza = destreza;
		this.inteligencia = inteligencia;
		this.codicia = codicia;
		this.regiones = regiones;
	}

	public String getRaza() {
		return raza;
	}

	public int getVida() {
		return vida;
	}

	public int getFuerza() {
		return fuerza;
	}

	public int getDestreza() {
		return destreza;
	}

	public int getInteligencia() {
		return inteligencia;
	}

	public int getCodicia() {
		return codicia;
	}

	public String[] getRegiones() {
		return regiones;
	}
}
