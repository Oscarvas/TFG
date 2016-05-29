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
public class Mago extends Protagonista {
	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			iniciarPrincipal((String) args[0], Integer.parseInt((String) args[1]), Integer.parseInt((String) args[2]),
					Integer.parseInt((String) args[3]), Integer.parseInt((String) args[4]),
					Integer.parseInt((String) args[5]), false);
			super.principal = Integer.parseInt((String) args[4]);// el mago da
																	// como
																	// atributo
																	// principal
																	// la
																	// inteligencia
		}

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Cazamagia");
		sd.setName(getLocalName() + "-Cazamagia");
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		localizarPersonaje();
		Gui.setHistoria("Aquel al que llaman mago, " + getLocalName()
				+ ", realmente sólo tiene muchísimas cartas bajo la túnica.");
		
		addBehaviour(new NuncaLlegoTarde());
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		if (estaMuerto()) {
			Gui.setHistoria(getLocalName() + ": An'u belore delen'na...\n");
		} else {
			Gui.setHistoria(getLocalName() + ": El conocimiento es poder...\n");
		}
	}

	private class NuncaLlegoTarde extends Behaviour {
		boolean ok;

		@Override
		public void action() {
			try {
				Thread.sleep(7000);
				planificar(null);
				ok = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean done() {
			return ok;
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

				ACLMessage restaurar = new ACLMessage(ACLMessage.INFORM);
				restaurar.setConversationId("Restaurar");
				restaurar.addReceiver(rey);

				if (estaMuerto()) {

					Gui.setHistoria("+ El mago " + getLocalName() + " se evaporo quedando solo su túnica. \n");

					restaurar.setPerformative(ACLMessage.FAILURE);
				}

				doDelete();
				send(restaurar);

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}
}
