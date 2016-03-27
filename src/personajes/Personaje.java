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

import javaff.JavaFF;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import objetos.Item;
import acciones.*;


public class Personaje extends Agent {

	private int vida;
	private ArrayList<Par> objetos;

	private String localizacion;

	private AID agenteMundo;

	private Logger logger;

	class Par {

		private Item objeto;
		private int numero;

		public Par(Item objeto, int numero) {
			this.objeto = objeto;
			this.numero = numero;
		}

	}
	
//	public Personaje(int vida) {
//		this.vida = vida;
//	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public int getVida() {
		return this.vida;
	}

	public void añadirVida(int vida) {
		this.vida += vida;

		if (this.vida <= 0) {
			this.vida = 0;

			ACLMessage muerto = new ACLMessage(ACLMessage.INFORM);
			muerto.addReceiver(agenteMundo);
			muerto.setConversationId("Muerto");
			muerto.setContent(getLocalName());
			send(muerto);
			MessageTemplate mt = MessageTemplate.MatchConversationId("Muerto");
			blockingReceive(mt);

		}
	}
}
	
	/*
	public Personaje(int vida, String localizacion) {

		this.vida = vida;
		this.objetos = new ArrayList<Par>();
		this.localizacion = localizacion;
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(getClass().getName().substring(11));
		//logger.addAppender(new FileAppender());
	}

	public String marcaDeClase() {

		String clase = getClass().getName().substring(11);

		if (clase.equalsIgnoreCase("Dragon"))
			clase = "#";

		else if (clase.equalsIgnoreCase("Rey"))
			clase = "-";

		else if (clase.equalsIgnoreCase("Princesa"))
			clase = "*";

		else if (clase.equalsIgnoreCase("Caballero"))
			clase = "+";

		return clase;
	}

	public void avisarInicio() {

		ACLMessage confirmacion = new ACLMessage(ACLMessage.CONFIRM);
		confirmacion.setContent(AgenteDirector.INICIO);
		confirmacion.addReceiver(new AID("director", AID.ISLOCALNAME));
		send(confirmacion);

	}

	public void localizarPersonaje() {

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Mundo");
		template.addServices(sd);

		try {

			DFAgentDescription[] result = DFService.search(this, template);

			agenteMundo = new AID();
			agenteMundo = result[0].getName();

			ACLMessage localizar = new ACLMessage(ACLMessage.REQUEST);
			localizar.addReceiver(agenteMundo);
			localizar.setConversationId("Mover");
			localizar.setContent(getClass().getName().substring(11) + " "
					+ localizacion);
			localizar.setReplyWith("localizar" + System.currentTimeMillis());
			send(localizar);

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("Mover"),
					MessageTemplate.MatchInReplyTo(localizar.getReplyWith()));
			blockingReceive(mt);

		} catch (FIPAException e) {
			e.printStackTrace();
		}

	}

	public int getVida() {
		return this.vida;
	}

	public void añadirVida(int vida) {
		this.vida += vida;

		if (this.vida <= 0) {
			this.vida = 0;

			ACLMessage muerto = new ACLMessage(ACLMessage.INFORM);
			muerto.addReceiver(agenteMundo);
			muerto.setConversationId("Muerto");
			muerto.setContent(getLocalName());
			send(muerto);
			MessageTemplate mt = MessageTemplate.MatchConversationId("Muerto");
			blockingReceive(mt);

		}
	}

	public boolean estaMuerto() {
		return this.vida == 0;
	}

	public void añadirObjeto(Item objeto) {

		for (int i = 0; i < this.objetos.size(); i++) {

			if (objeto.getId().equalsIgnoreCase(
					this.objetos.get(i).objeto.getId()))
				this.objetos.get(i).numero++;

			else {
				Par par = new Par(objeto, 1);
				this.objetos.add(par);
			}
		}
	}

	public boolean elimObjeto(String id) {

		boolean ok = false;

		for (int i = 0; i < this.objetos.size(); i++) {
			if (this.objetos.get(i).objeto.getId().equalsIgnoreCase(id)) {
				if (this.objetos.get(i).numero == 0)
					this.objetos.remove(i);
				else
					this.objetos.get(i).numero--;

				ok = true;
				break;
			}
		}

		return ok;
	}

	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public Logger getLogger() {
		return logger;
	}

	public void planificar(String princesa) throws Exception {

		boolean ok;
		boolean falloSecuestro = false;

		do {
			ok = true;

			mandarCrearArchivo(princesa);
			String[] args = { "domain.pddl", getLocalName() + ".pddl" };

			String ff = JavaFF.crearPlan(args);
			String[] cadena = ff.split("\n");

			for (String sigAccion : cadena) {
				String[] accionActual = sigAccion.split(" ");
				String accion = accionActual[0];

				if (estaMuerto())
					break;

				if (!accionActual[1].equalsIgnoreCase(getLocalName())) {
					ok = false;
					break;
				}

				else if (accion.equalsIgnoreCase("moverprincipal")
						|| accion.equalsIgnoreCase("moversecundario")) {
					new Mover(this, accionActual[2], accionActual[3],
							agenteMundo).execute();
				}

				else if (accion.equalsIgnoreCase("secuestrar")) {
					if ( ! new Secuestrar(this, accionActual[2], agenteMundo).execute() ) {
						falloSecuestro = true;
						break;
					}
				}
				
				else if (accion.equalsIgnoreCase("moverpersonajeconprincesa")) {
					ACLMessage moverPrincesa = new ACLMessage(
							ACLMessage.REQUEST);
					moverPrincesa.setConversationId("Mover-Princesa");
					moverPrincesa.setReplyWith("mover-princesa"
							+ System.currentTimeMillis());
					moverPrincesa.addReceiver(new AID((String) princesa,
							AID.ISLOCALNAME));
					moverPrincesa.setContent(accionActual[4]);
					send(moverPrincesa);

					MessageTemplate mt = MessageTemplate
							.MatchInReplyTo(moverPrincesa.getReplyWith());
					blockingReceive(mt);
					new Mover(this, accionActual[3], accionActual[4],
							agenteMundo).execute();

				} else if (accion.equalsIgnoreCase("batalla"))
				{
					new Batalla(this, accionActual[2], agenteMundo).execute();
				}
				else if (accion.equalsIgnoreCase("liberarprincesa"))
					new LiberarPrincesa(this, accionActual[2], accionActual[3],
							agenteMundo).execute();

				else if (accion.equalsIgnoreCase("dejarencasa"))
					new DejarEnCasa(this, accionActual[2], agenteMundo)
							.execute();

				else if (accion.equalsIgnoreCase("convertirseenheroe"))
					new ConvertirseEnHeroe(this, agenteMundo).execute();

				else {
					System.out.println(sigAccion);
					throw new Exception("Accion no reconocible");
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

	public void mandarCrearArchivo(String princesa) {

		ACLMessage toPDDL = new ACLMessage(ACLMessage.REQUEST);
		toPDDL.addReceiver(agenteMundo);
		toPDDL.setConversationId("toPDDL");
		toPDDL.setReplyWith("toPDDL" + System.currentTimeMillis());
		toPDDL.setContent(getClass().getName().substring(11) + " "
				+ getLocalName() + " " + princesa);
		send(toPDDL);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("toPDDL"),
				MessageTemplate.MatchInReplyTo(toPDDL.getReplyWith()));
		blockingReceive(mt);
	}

	public void moverSecuestrado(String locDest) {
		new Mover(this, localizacion, locDest, agenteMundo).execute();
	}
}
*/
