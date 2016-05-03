package personajes.principal;

import acciones.ConsumirObjeto;
import objetos.Objeto;
import ontologia.Raza;
import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Protagonista extends Personaje {

	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	
	
	
	public Protagonista() {
		// TODO Auto-generated constructor stub
	}
	
	public void iniciarPrincipal(String nombreRaza, int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean rey){
		cargarMundo();
		Raza raza = new Raza();
		raza.getRazaByName(nombreRaza);
		setVida(vida * raza.getVidaModificador());
		setFuerza(fuerza * raza.getFuerzaModificador());
		setDestreza(destreza * raza.getDestrezaModificador());
		setInteligencia(inteligencia * raza.getDestrezaModificador());
		setCodicia(codicia * raza.getCodiciaModificador());
		if (!rey){
			setTesoro(Vocabulario.SALARIO * getCodicia());
		}			
		else{
			setTesoro(Vocabulario.SALARIO_REY * getCodicia());
		}
		
		addBehaviour(new ConsumirObjeto(this));
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
	
	public String usarObjeto(String atributos){
		
		String [] args = atributos.split(" ");	
				
		this.añadirVida(Integer.parseInt((String) args[Objeto.VIDA]));
		this.fuerza += Integer.parseInt((String) args[Objeto.FUERZA]);
		this.destreza += Integer.parseInt((String) args[Objeto.DESTREZA]);
		this.codicia += Integer.parseInt((String) args[Objeto.CODICIA]);
		this.inteligencia += Integer.parseInt((String) args[Objeto.INTELIGENCIA]);
		
		return ":" + args[Objeto.ATRIBUTOS] + ".";
	}


}
