package objetos;

public class Clave extends Objeto {
	
	String localizacion;

	public Clave(String nombre, String descripcion, String localizacion) {
		super(nombre, descripcion);
		this.localizacion = localizacion;
	}

	public String getLocalizacion() {
		return this.localizacion;
	}

	@Override
	public String mensaje() {
		return null;
	}
	
}
