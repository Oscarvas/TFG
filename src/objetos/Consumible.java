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
		mensaje = mensaje + "\nCantidad: "+ this.cantidad;
		mensaje = mensaje + "\nVida: "+ this.vida;
		mensaje = mensaje + "\nFuerza: "+ this.fuerza;
		mensaje = mensaje + "\nDestreza: "+ this.destreza;
		mensaje = mensaje + "\nInteligencia: "+ this.inteligencia;
		mensaje = mensaje + "\nCodicia: "+ this.codicia;
		return mensaje;
	}


}
