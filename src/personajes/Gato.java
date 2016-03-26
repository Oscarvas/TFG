package personajes;

import java.util.Random;

import acciones.Saludar;

public class Gato extends Personaje{
	String name;

	public Gato() {
		super(new Random().nextInt(100 + 1) + 15);
		name = "garfield";
	}
	
	protected void setup(){
		addBehaviour(new Saludar(this, name));
	}

}
