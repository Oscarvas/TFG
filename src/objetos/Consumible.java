package objetos;

public class Consumible extends Objeto {
	
	private String[] atributos;
	private int vida;
	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	

	public Consumible(String id, String descripcion, String[] atributos) {
		super(id, descripcion);
		this.atributos = atributos;
		this.vida = Integer.parseInt(atributos[VIDA]);
		this.fuerza = Integer.parseInt(atributos[FUERZA]);
		this.destreza = Integer.parseInt(atributos[DESTREZA]);
		this.inteligencia = Integer.parseInt(atributos[INTELIGENCIA]);
		this.codicia = Integer.parseInt(atributos[CODICIA]);
	}
	
	
	public String mensaje(){
		String mensaje = "";
		for(int i = 0; i < Objeto.ATRIBUTOS; i++){
			mensaje += atributos[i]+ " ";
		}
		
		return mensaje;
		
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
