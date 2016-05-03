package ontologia;

public class Raza{
	private String  nombre;
	private int vidaModificador;
	private int fuerzaModificador;
	private int destrezaModificador;
	private int inteligenciaModificador;	
	private int codiciaModificador;
	
	
	public Raza(String  nombre, int vida, int fuerza, int destreza, int inteligencia, int codicia) {
		this.nombre= nombre;
		this.vidaModificador = vida;
		this.fuerzaModificador = fuerza;
		this.destrezaModificador = destreza;
		this.inteligenciaModificador = inteligencia;
		this.codiciaModificador = codicia;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getVidaModificador() {
		return vidaModificador;
	}


	public void setVidaModificador(int vida) {
		this.vidaModificador = vida;
	}


	public int getFuerzaModificador() {
		return fuerzaModificador;
	}


	public void setFuerzaModificador(int fuerza) {
		this.fuerzaModificador = fuerza;
	}


	public int getDestrezaModificador() {
		return destrezaModificador;
	}


	public void setDestrezaModificador(int destreza) {
		this.destrezaModificador = destreza;
	}


	public int getInteligenciaModificador() {
		return inteligenciaModificador;
	}


	public void setInteligenciaModificador(int inteligencia) {
		this.inteligenciaModificador = inteligencia;
	}


	public int getCodiciaModificador() {
		return codiciaModificador;
	}


	public void setCodiciaModificador(int codicia) {
		this.codiciaModificador = codicia;
	}
	
}
