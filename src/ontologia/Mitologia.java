package ontologia;

/*
 * Enumerado de las zonas
Esta clase guardara la referencia a la raza propia de cada region del mapa
asi como los atributos predefinidos de cada raza y que afectaran al desarrollo de cada personaje
durante su historia.
Formato:
	Raza	Zona	vida,fuerza,destreza,inteligencia,codicia	RegionesInternas
*/
public enum Mitologia {
	CABALLERO("Pueblo"),
	DRUIDA("Bosque"),
	MAGO("Urbano"),
	REY("Castillo"),
	PRINCESA("Castillo"),
	SECUESTRADOR("Guarida"),
	EMBOSCADOR("Bosque"),
	GUARDIAN("Lago"),
	MALIGNO("Todas"); 
	
	private final String tipo;
	
	Mitologia(String tipo){
		this.tipo = tipo;
	}

	public String getZona() {
		return tipo;
	}

}
