package personajes.antagonistas;

import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Antagonista extends Personaje {
	String especie;

	public void iniciarMonstruo(String especie, String sexo){
		cargarMundo();
		setVida(Vocabulario.VIDA_MONSTRUO());
//		String clase = getClass().getName().substring(21);
//		setLocalizacion(localizacion);
		localizarPersonaje();
		setEspecie(especie);
		setSexo(sexo);
	}
	
	public String getEspecie(){
		return this.especie;
	}
	
	public void setEspecie(String especie){
		this.especie = especie;
	}
}
