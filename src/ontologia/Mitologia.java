package ontologia;

/*
 * String[] TIPOS_LOCALIZACION = {"Pueblo","Bosque","Urbano","Castillo","Guarida","Lago"};
 * Pueblo = 0
 * Bosque = 1
 * Urbano = 2
 * Castillo = 3
 * Guarida = 4
 * Lago = 5
 * 
 * Esta clase mantiene la asociacion entre la clase de un personaje y su localizacion predeterminada en funcion de la clase
 * En el caso del personaje Maligno, puede aparecer en cualquier localizacion
*/
public enum Mitologia {
	CABALLERO(Vocabulario.TIPOS_LOCALIZACION[0]),
	DRUIDA(Vocabulario.TIPOS_LOCALIZACION[1]),
	MAGO(Vocabulario.TIPOS_LOCALIZACION[2]),
	REY(Vocabulario.TIPOS_LOCALIZACION[3]),
	PRINCESA(Vocabulario.TIPOS_LOCALIZACION[3]),
	SECUESTRADOR(Vocabulario.TIPOS_LOCALIZACION[4]),
	EMBOSCADOR(Vocabulario.TIPOS_LOCALIZACION[1]),
	GUARDIAN(Vocabulario.TIPOS_LOCALIZACION[5]),
	MALIGNO(Vocabulario.TIPOS_LOCALIZACION[Vocabulario.LOC_MALIGNO()]); 
	
	private final String tipo;
	
	Mitologia(String tipo){
		this.tipo = tipo;
	}

	public String getZona() {
		return tipo;
	}
}

