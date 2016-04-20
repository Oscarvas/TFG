package objetos;

public abstract class Objeto {	
	static final public int CANTIDAD = 0;
	static final public int VIDA = 1;
	static final public int FUERZA = 2;
	static final public int DESTREZA = 3;
	static final public int INTELIGENCIA = 4;
	static final public int CODICIA = 5;
	// La cantidad de atributos es la suma de todos los que hay
	static final public int ATRIBUTOS = 6;	
	
	private String nombre;
	private String descripcion;
	
	public Objeto (String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public String getId() {
		return this.nombre;
	}
	
	public String toString() {		
		return "- " + this.nombre + ": " + this.descripcion;
	}
	
//	public abstract boolean usar(Personaje quien, Localizacion donde);

}
