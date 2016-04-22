package objetos;

public class Consumible extends Objeto {
	
	private int cantidad;
	private int vida;
	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	

	public Consumible(String id, String descripcion, String[] atributos) {
		super(id, descripcion);
		this.cantidad = Integer.parseInt(atributos[CANTIDAD]);
		this.vida = Integer.parseInt(atributos[VIDA]);
		this.fuerza = Integer.parseInt(atributos[FUERZA]);
		this.destreza = Integer.parseInt(atributos[DESTREZA]);
		this.inteligencia = Integer.parseInt(atributos[INTELIGENCIA]);
		this.codicia = Integer.parseInt(atributos[CODICIA]);
	}
	
	public String toString(){
		String mensaje = super.toString();
		mensaje = mensaje + "\nAtributos: ";
		mensaje = mensaje + "\n(" + this.cantidad 		+ ")\tCantidad";
		mensaje = mensaje + "\n(" + this.vida 			+ ")\tVida";
		mensaje = mensaje + "\n(" + this.fuerza 		+ ")\tFuerza";
		mensaje = mensaje + "\n(" + this.destreza 		+ ")\tDestreza";
		mensaje = mensaje + "\n(" + this.inteligencia 	+ ")\tInteligencia";
		mensaje = mensaje + "\n(" + this.codicia 		+ ")\tCodicia";
		return mensaje;
	}


}
