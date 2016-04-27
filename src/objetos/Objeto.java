package objetos;

public abstract class Objeto {
	static final public int VIDA = 0;
	static final public int FUERZA = 1;
	static final public int DESTREZA = 2;
	static final public int INTELIGENCIA = 3;
	static final public int CODICIA = 4;
	// La cantidad de atributos es la suma de todos los que hay
	static final public int ATRIBUTOS = 5;	
	
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
