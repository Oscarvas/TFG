package objetos;

public class Consumible extends Objeto {

	private int vida;
	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	

	public Consumible(String id, String descripcion, String[] atributos) {
		super(id, descripcion);
		this.vida = Integer.parseInt(atributos[VIDA]);
		this.fuerza = Integer.parseInt(atributos[FUERZA]);
		this.destreza = Integer.parseInt(atributos[DESTREZA]);
		this.inteligencia = Integer.parseInt(atributos[INTELIGENCIA]);
		this.codicia = Integer.parseInt(atributos[CODICIA]);
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
	
	public String toString(){
		String mensaje = super.toString();
		mensaje = mensaje + "\nAtributos: ";
		mensaje = mensaje + "\n(" + this.vida 			+ ")\tVida";
		mensaje = mensaje + "\n(" + this.fuerza 		+ ")\tFuerza";
		mensaje = mensaje + "\n(" + this.destreza 		+ ")\tDestreza";
		mensaje = mensaje + "\n(" + this.inteligencia 	+ ")\tInteligencia";
		mensaje = mensaje + "\n(" + this.codicia 		+ ")\tCodicia";
		return mensaje;
	}


}
