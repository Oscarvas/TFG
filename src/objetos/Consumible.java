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
		int aux = 0;
		mensaje = mensaje + "\nEl objeto concede una modificación en los atributos de: ";
		if(this.vida != 0){
			mensaje = mensaje + "\n\t" + this.vida 			+ " de Vida.";
			aux++;
		}
		if(this.fuerza != 0){
			mensaje = mensaje + "\n\t"  + this.fuerza 		+ " de Fuerza.";
			aux++;
		}
		if(this.destreza != 0){
			mensaje = mensaje + "\n\t"  + this.destreza 		+ " de Destreza.";
			aux++;
		}
		if(this.inteligencia != 0){
			mensaje = mensaje + "\n\t"  + this.inteligencia 	+ " de Inteligencia.";
			aux++;
		}
		if(this.inteligencia != 0){
			mensaje = mensaje + "\n\t"  + this.codicia 		+ " de Codicia.";
			aux++;
		}		
		
		if (aux == 0) mensaje = "El objeto no ha concedido nada."; 
		
		return mensaje;
	}


}
