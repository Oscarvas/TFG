package personajes.principal;


import java.util.Random;

import ontologia.Mitologia;
import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Protagonista extends Personaje {
	private Mitologia ralea;
	private String zona;

	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	
	
	
	public Protagonista() {
		// TODO Auto-generated constructor stub
	}
	
	public void iniciarPrincipal(int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean rey){
		cargarMundo();
//		String loc[] = raza.getRegiones() ;
		localizarInicial();
		setVida(vida);
		setFuerza(fuerza );
		setDestreza(destreza );
		setInteligencia(inteligencia );
		setCodicia(codicia );
//		if (!rey){
//			setLocalizacion(loc[new Random().nextInt(loc.length)]);
//			setTesoro(Vocabulario.SALARIO * getCodicia());
//		}			
//		else{
//			setTesoro(Vocabulario.SALARIO_REY * getCodicia());
//			if (raza.getZona().equalsIgnoreCase("Tesqua"))
//				setLocalizacion(Vocabulario.CASTILLOS[0]);
//			if (raza.getZona().equalsIgnoreCase("Lucta"))
//				setLocalizacion(Vocabulario.CASTILLOS[1]);
//		}
	}

	public int getFuerza() {
		return fuerza;
	}

	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	public int getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	public int getCodicia() {
		return codicia;
	}

	public void setCodicia(int codicia) {
		this.codicia = codicia;
	}


}
