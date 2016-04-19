package personajes.monstruos;

import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Monstruo extends Personaje {

	public void iniciarMonstruo(String localizacion){
		cargarMundo();
		setVida(Vocabulario.VIDA_MONSTRUO);
//		String clase = getClass().getName().substring(21);
		setLocalizacion(localizacion);

	}
}
