package personajes.principal;

import acciones.*;
import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Caballero extends Protagonista {

	private String princesa;
	private String dragon;

	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			iniciarPrincipal((String) args[0], Integer.parseInt((String) args[1]), Integer.parseInt((String) args[2]),
					Integer.parseInt((String) args[3]), Integer.parseInt((String) args[4]),
					Integer.parseInt((String) args[5]), false);
		}

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Matadragones");
		sd.setName(getLocalName() + "-Matadragones");
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		localizarPersonaje();
		Gui.setHistoria("El caballero " + getLocalName() + " se ha despertado en " + getLocalizacion()
				+ " con su armadura hecha polvo.");

		addBehaviour(new OfrecerServicios(getTesoro()));
		addBehaviour(new AceptarOfertaRescate());
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		if (estaMuerto()){
			Gui.setHistoria(getLocalName()+": Ve con honor...");
		}else{
			Gui.setHistoria(getLocalName()+": Que la luz te guíe...");
		}
	}

	public String getPrincesa() {
		return princesa;
	}

	public void setPrincesa(String princesa) {
		this.princesa = princesa;
	}

	public String getDragon() {
		return dragon;
	}

	public void setDragon(String dragon) {
		this.dragon = dragon;
	}

	private class AceptarOfertaRescate extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("TratoHecho"),
					MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {

				String[] contrato = msg.getContent().split(" ");
				setPrincesa(contrato[0]);
				setDragon(contrato[1]);

				ACLMessage salvar = new ACLMessage(ACLMessage.INFORM);
				salvar.setConversationId("ObjetivoSecuestro");
				salvar.addReceiver(getAgenteMundo());
				salvar.setContent(getPrincesa());
				myAgent.send(salvar);

				try {
					planificar(null);
					addBehaviour(new FinPlanificacion(msg.getSender()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else
				block();

		}
	}

	private class FinPlanificacion extends Behaviour {

		AID rey;
		ACLMessage receive;

		public FinPlanificacion(AID rey) {
			this.rey = rey;
		}

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fin-Plan"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));

			receive = receive(mt);

			if (receive != null) {

				ACLMessage rescate = new ACLMessage(ACLMessage.INFORM);
				rescate.setConversationId("Rescate");
				rescate.addReceiver(rey);

				if (estaMuerto()) {

					Gui.setHistoria("+ El caballero " + getLocalName() + " ha muerto en combate. \n");

					rescate.setPerformative(ACLMessage.FAILURE);
				}

				doDelete();
				send(rescate);

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}

}
