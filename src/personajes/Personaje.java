package personajes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javaff.JavaFF;
import loaders.LoaderObjetivos;
import ontologia.Mitologia;
import ontologia.Vocabulario;
import acciones.*;
import gui.Gui;


@SuppressWarnings({ "serial", "unused" })
public class Personaje extends Agent {
	private int vida;
	private HashMap<String, ArrayList<String>> frases;
	private String localizacion;	
	private AID agenteMundo;
	private int tesoro;
	private String sexo;
	private String clase;
	private String casa;
	
	public Personaje(){
		this.frases = new HashMap<String, ArrayList<String>>();
	}

	public String getCasa() {
		return this.casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	/*
	 * Cargamos el AID del agente que tenga publicado
	 * el servicio Mundo en el DF
	 * */
	protected void cargarMundo(){
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Mundo");
		template.addServices(sd);
		
		DFAgentDescription[] result;
		try {
			result = DFService.search(this, template);
			setAgenteMundo(result[0].getName());
			setClase(getClass().getName().substring(21));  //ignora los primeros 21 caracteres que se pasan de un string -> personajes.monstruo. = 21
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Provisionalmente aqui para no repetir codigo
		 * */
		addBehaviour(new Defender(this));
		
	}

	public HashMap<String, ArrayList<String>> getFrases() {
		return frases;
	}

	public void setFrases(HashMap<String, ArrayList<String>> frases) {
		this.frases = frases;
	}

	public void aņadirVida(int vida) {
		this.vida += vida;

		if (this.vida <= 0) {
			this.vida = 0;

			ACLMessage muerto = new ACLMessage(ACLMessage.INFORM);
			muerto.addReceiver(getAgenteMundo());
			muerto.setConversationId("Muerto");
			muerto.setContent(getLocalName());
			send(muerto);
			MessageTemplate mt = MessageTemplate.MatchConversationId("Muerto");
			blockingReceive(mt);

		}
	}
	
	public AID getAgenteMundo() {
		return agenteMundo;
	}

	public void setAgenteMundo(AID agenteMundo) {
		this.agenteMundo = agenteMundo;
	}

	public void localizarPersonaje() {

		ACLMessage localizar = new ACLMessage(ACLMessage.REQUEST);
		localizar.addReceiver(getAgenteMundo());
		localizar.setConversationId("Mover");
		localizar.setContent(getClase() + " "+ getLocalizacion());
		localizar.setReplyWith("localizar" + System.currentTimeMillis());
		send(localizar);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Mover"),
				MessageTemplate.MatchInReplyTo(localizar.getReplyWith()));
		blockingReceive(mt);
		
		setCasa(getLocalizacion());

	}
	
	public boolean estaMuerto() {
		return this.vida <= 0;
	}
	
	public int getVida() {
		return this.vida;
	}
	
	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public String getSexo(){
		if (this.sexo.equals("M"))
			return "El";
		else
			return "La";
	}
	
	public void setSexo(String sexo){
		this.sexo = sexo;
	}
	
	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	
	public int getTesoro() {
		return tesoro;
	}

	public void setTesoro(int tesoro) {
		this.tesoro = tesoro;
	}
	
	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}
	
	public void planificar(String objetivoEspecifico) throws Exception{
		if (objetivoEspecifico.equals(null))
			new LoaderObjetivos(this).execute();
		else
			new LoaderObjetivos(this,objetivoEspecifico).execute();
		
		planificar();
	}
	
	private void planificar() throws Exception {

		boolean ok;
		boolean falloSecuestro = false;

		do {
			ok = true;

			mandarCrearArchivo();
			String[] args = { "domain.pddl", getLocalName() + ".pddl" };

			String ff = JavaFF.crearPlan(args);
			String[] cadena = ff.split("\n");
			//para hacer un seguimiento desde la interfaz sobre la planificacion que se ha enviado a cada personaje
			System.out.println(ff);//en consola
			for (String sigAccion : cadena) {
				String[] accionActual = sigAccion.split(" ");
				String accion = accionActual[0];

				try {
					if (estaMuerto())
						break;

					if (!accionActual[1].equalsIgnoreCase(getLocalName())) {
						ok = false;
						break;
					}

					else if (accion.equalsIgnoreCase("moverprincipal")
							|| accion.equalsIgnoreCase("moversecundario")) {
						
						new Mover(this, accionActual[2], accionActual[3]).execute();
					}

					else if (accion.equalsIgnoreCase("secuestrar")) {
						if ( ! new Secuestrar(this, accionActual[2]).execute() ) {
							falloSecuestro = true;
							break;
						}
					}
					
					else if (accion.equalsIgnoreCase("moverpersonajeconprincesa")) {
						new Mover(this, accionActual[3], accionActual[4]).execute();
						
						ACLMessage moverPrincesa = new ACLMessage(
								ACLMessage.REQUEST);
						moverPrincesa.setConversationId("Mover-Princesa");
						moverPrincesa.setReplyWith("mover-princesa"
								+ System.currentTimeMillis());
						moverPrincesa.addReceiver(getAID(accionActual[2]));
						moverPrincesa.setContent(accionActual[4]);
						send(moverPrincesa);

						MessageTemplate mt = MessageTemplate
								.MatchInReplyTo(moverPrincesa.getReplyWith());
						blockingReceive(mt);
						

					} else if (accion.equalsIgnoreCase("batalla"))
					{
						new Batalla(this, accionActual[2]).execute();
					}
					else if (accion.equalsIgnoreCase("liberarprincesa"))
						new LiberarPrincesa(this, accionActual[2], accionActual[3]).execute();

					else if (accion.equalsIgnoreCase("dejarencasa"))
						new DejarEnCasa(this, accionActual[2]).execute();

					else if (accion.equalsIgnoreCase("convertirseenheroe"))
						new ConvertirseEnHeroe(this).execute();

					else {
						System.out.println(sigAccion);
						throw new Exception("Accion no reconocible");
					}
				} catch (Exception e) {
					// TODO: handle exception
					/*
					 * Este bloque de control captura el error que produce el planificador
					 * sobre un objetivo que no se puede cumplir. En cuyo caso el personaje debera volver a su casa
					 * */
					new LoaderObjetivos(this).dismiss();
				}
				
			}

		} while (!ok);

		if ( falloSecuestro ) {
			
			ACLMessage fallo = new ACLMessage(ACLMessage.FAILURE);
			fallo.setConversationId("Fallo Secuestro");
			fallo.addReceiver(getAID());
			send(fallo);
			
		} else {
			ACLMessage finPlan = new ACLMessage(ACLMessage.INFORM);
			finPlan.setConversationId("Fin-Plan");
			finPlan.addReceiver(getAID());
			send(finPlan);
		}
	}

	public void mandarCrearArchivo() {		
		ACLMessage toPDDL = new ACLMessage(ACLMessage.REQUEST);
		toPDDL.addReceiver(getAgenteMundo());
		toPDDL.setConversationId("toPDDL");
		toPDDL.setReplyWith("toPDDL" + System.currentTimeMillis());
		toPDDL.setContent(getClase() + " "+ getLocalName());
		send(toPDDL);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("toPDDL"),
				MessageTemplate.MatchInReplyTo(toPDDL.getReplyWith()));
		blockingReceive(mt);
	}
	
	public void moverSecuestrado(String locDest) {
		new Mover(this, localizacion, locDest).execute();
	}
	
}