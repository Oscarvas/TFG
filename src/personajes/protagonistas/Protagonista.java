package personajes.protagonistas;

import gui.Gui;
import mundo.Mundo;
import objetos.Objeto;
import ontologia.Raza;
import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Protagonista extends Personaje {
	
	private String rol;
	private int fuerza;
	private int destreza;
	private int inteligencia;
	private int codicia;
	protected String atr;
	private int principal;
	
	
	
	public Protagonista() {
		super();
	}
	
	public void iniciarPrincipal(String rol, String nombreRaza, String sexo, int vida,int fuerza, int destreza, int inteligencia, int codicia, boolean allegado){
		cargarMundo();
		setRol(rol);
		setFrases(Mundo.diccionario.getFrasesPersonaje(this.rol));
		Raza raza = Vocabulario.RAZAS2.get(nombreRaza);
		setSexo(sexo);
		setVida(vida * raza.getVidaModificador());
		setFuerza(fuerza * raza.getFuerzaModificador());
		setDestreza(destreza * raza.getDestrezaModificador());
		setInteligencia(inteligencia * raza.getDestrezaModificador());
		setCodicia(codicia * raza.getCodiciaModificador());
		if (!allegado){
			setTesoro(Vocabulario.SALARIO * getCodicia());
		}			
		else{
			setTesoro(Vocabulario.SALARIO_REY * getCodicia());
		}
	}
	
	public String getRol() {
		return rol;
	}
	
	public String setRol(String rol) {
		return this.rol = rol;
		
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
	
	public void cargaPrincipal(int atributo){
		this.principal = atributo;
		setPrincipal();
	}
	
	private void setPrincipal(){
		this.principal = this.getPrincipal() + this.getVida();//el atributo de pelea es su atributo principal + la vida
		super.setPrincipal(this.principal);
	}
	
	public String usarObjeto(String atributos){
		
		String [] args = atributos.split(" ");	
				
		this.añadirVida(Integer.parseInt((String) args[Objeto.VIDA]));
		this.fuerza += Integer.parseInt((String) args[Objeto.FUERZA]);
		this.destreza += Integer.parseInt((String) args[Objeto.DESTREZA]);
		this.codicia += Integer.parseInt((String) args[Objeto.CODICIA]);
		this.inteligencia += Integer.parseInt((String) args[Objeto.INTELIGENCIA]);
		
		return getLocalName() +	hablar("Coger");
		
	}
	
	public void bendicionPnj(String atributos){
		String [] attr = atributos.split(" ");
		
		this.fuerza += Integer.parseInt((String) attr[0]);
		this.destreza += Integer.parseInt((String) attr[1]);
		this.inteligencia += Integer.parseInt((String) attr[2]);
		this.codicia += Integer.parseInt((String) attr[3]);
		
		/*Gui.setHistoria(getLocalName()+ ": Vida:" + getVida()
			+" Fuerza:"+this.fuerza
			+" Destreza:"+this.destreza
			+" Inteligencia"+this.inteligencia
			+" Codicia:"+this.codicia+ ".");
		*/
	}


}
