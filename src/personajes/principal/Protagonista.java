package personajes.principal;

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
	protected String atr;
	protected int principal;
	
	
	
	public Protagonista() {
		super();
	}
	
	public void iniciarPrincipal(String nombreRaza, int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean rey){
		cargarMundo();
		Raza raza = Vocabulario.RAZAS2.get(nombreRaza);
		setVida(vida * raza.getVidaModificador());
		setFuerza(fuerza * raza.getFuerzaModificador());
		setDestreza(destreza * raza.getDestrezaModificador());
		setInteligencia(inteligencia * raza.getDestrezaModificador());
		setCodicia(codicia * raza.getCodiciaModificador());
		setPrincipal();
		if (!rey){
			setTesoro(Vocabulario.SALARIO * getCodicia());
		}			
		else{
			setTesoro(Vocabulario.SALARIO_REY * getCodicia());
		}
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
	
	public int getPrincipal(){
		return this.principal;
	}
	public void setPrincipal(){
		this.principal = this.getPrincipal() + this.getVida();//el atributo de pelea es su atributo principal + la vida
		super.setPrincipal(this.principal);
	}
	
	public String usarObjeto(String atributos){
		
		String [] args = atributos.split(" ");	
				
		this.aņadirVida(Integer.parseInt((String) args[Objeto.VIDA]));
		this.fuerza += Integer.parseInt((String) args[Objeto.FUERZA]);
		this.destreza += Integer.parseInt((String) args[Objeto.DESTREZA]);
		this.codicia += Integer.parseInt((String) args[Objeto.CODICIA]);
		this.inteligencia += Integer.parseInt((String) args[Objeto.INTELIGENCIA]);
		
		return getLocalName()+ ": Vida:" + getVida()
			+" Fuerza:"+this.fuerza
			+" Destreza:"+this.destreza
			+" Inteligencia"+this.inteligencia
			+" Codicia:"+this.codicia+ ".";
	}


}
