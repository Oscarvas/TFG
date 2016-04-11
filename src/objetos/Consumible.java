package objetos;

public class Consumible extends Objeto {

	protected Consumible(String id, String descripcion) {
		super(id, descripcion);
	}

	@Override
	public boolean puedeUsarse() {
		return false;
	}

	@Override
	public boolean puedeCogerse() {
		return false;
	}

}
