package personajes.principal;

import gui.Gui;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import mundo.Mundo;

@SuppressWarnings("serial")
public class Mago extends Protagonista {
	protected void setup() {
		setFrases(Mundo.diccionario.getFrasesPersonaje("Mago"));
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
				+ getFrase("Inicio"));

		addBehaviour(new AyudaArcana());
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

	private class AyudaArcana extends Behaviour {
		boolean ok;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ok = false;

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("HoraMagica"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				try {
					ok = true;
					//planificar(null);
					Gui.setHistoria(getLocalName() + " me planifico muy guay madafakas !!! ----------");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public boolean done() {
			return ok;
		}
	}

}
