package objetos;

public class Equipo extends Objeto {

	protected Equipo(String id, String descripcion, String[] atributos) {
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
