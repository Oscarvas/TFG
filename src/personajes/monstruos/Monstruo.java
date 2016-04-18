package personajes.monstruos;

import java.util.Random;

import ontologia.Vocabulario;
import personajes.Personaje;

public class Monstruo extends Personaje {
	String localizacion;
	
	public void Monstruo() {
		Object[] args = getArguments(); 
		
		
	}
	public void iniciarMonstruo(String localizacion){
		setVida(Vocabulario.VIDA_MONSTRUO);
		String clase = getClass().getName().substring(11);
		setLocalizacion(localizacion);
//		this.localizacion = loc;
//		
//		switch (clase) {
//		case "monstruos.Dragon":
//			//setLocalizacion(this.localizacion);
//			break;
//		case "monstruos.Fantasma":
//			//setLocalizacion(this.localizacion);
//			break;
//		case "monstruos.Serpiente":
//			//setLocalizacion(this.localizacion);
//			break;
//		case "monstruos.Troll":
//			//setLocalizacion(this.localizacion);
//			break;
//
//		default:
//			break;
//		}

	}
}
