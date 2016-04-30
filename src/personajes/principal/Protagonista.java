package personajes.principal;

import java.util.Random;

import acciones.ConsumirObjeto;
import objetos.Objeto;
import ontologia.Mitologia;
import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Protagonista extends Personaje {
	private Mitologia raza; 

	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	
	
	
	
	public Protagonista() {
		// TODO Auto-generated constructor stub
	}
	
	public void iniciarPrincipal(Mitologia raza,int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean rey){
		cargarMundo();
		String loc[] = raza.getRegiones() ;
		setRaza(raza);
		setVida(vida * raza.getVida());
		setFuerza(fuerza * raza.getFuerza());
		setDestreza(destreza * raza.getDestreza());
		setInteligencia(inteligencia * raza.getInteligencia());
		setCodicia(codicia * raza.getCodicia());
		if (!rey){
			setLocalizacion(loc[new Random().nextInt(loc.length)]);
			setTesoro(Vocabulario.SALARIO * getCodicia());
		}			
		else{
			setTesoro(Vocabulario.SALARIO_REY * getCodicia());
			if (raza.getZona().equalsIgnoreCase("Tesqua"))
				setLocalizacion(Vocabulario.CASTILLOS[0]);
			if (raza.getZona().equalsIgnoreCase("Lucta"))
				setLocalizacion(Vocabulario.CASTILLOS[1]);
		}
		
		addBehaviour(new ConsumirObjeto(this));
	}
	
	public Mitologia getRaza() {
		return raza;
	}

	public void setRaza(Mitologia raza) {
		this.raza = raza;
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
	
	public void usarObjeto(String atributos){
		
		String [] args = atributos.split(" ");
		
		this.añadirVida(Integer.parseInt((String) args[Objeto.VIDA]));
		this.fuerza += Integer.parseInt((String) args[Objeto.FUERZA]);
		this.destreza += Integer.parseInt((String) args[Objeto.DESTREZA]);
		this.codicia += Integer.parseInt((String) args[Objeto.CODICIA]);
		this.inteligencia += Integer.parseInt((String) args[Objeto.INTELIGENCIA]);
	}


}
