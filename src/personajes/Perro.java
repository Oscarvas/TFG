package personajes;

import java.util.Random;

import acciones.Saludar;

public class Perro extends Personaje {
	String name;
	
	public Perro(){
		super(new Random().nextInt(100 + 1) + 15);
		name = "toby";
	}
	
	protected void Setup(){
		// aqui se a�ade el behaviour
		addBehaviour(new Saludar(this, name));
		//addBehaviour(new Batalla(this, gato));
	}

}
