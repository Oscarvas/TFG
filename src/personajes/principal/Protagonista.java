package personajes.principal;

import acciones.ConsumirObjeto;
import objetos.Objeto;
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
	
	public void iniciarPrincipal(int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean rey){
		cargarMundo();
		setVida(vida);
		setFuerza(fuerza );
		setDestreza(destreza );
		setInteligencia(inteligencia );
		setCodicia(codicia );
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
