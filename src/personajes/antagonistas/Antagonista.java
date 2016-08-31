package personajes.antagonistas;

import mundo.Mundo;
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
		setFrases(Mundo.diccionario.getFrasesPersonaje(this.especie));
	}
	
	public String getEspecie(){
		return this.especie;
	}
	
	public void setEspecie(String especie){
		this.especie = especie;
	}
}
