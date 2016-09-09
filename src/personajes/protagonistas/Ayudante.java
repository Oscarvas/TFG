package personajes.protagonistas;

import gui.Gui;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Ayudante extends Protagonista {
	protected void setup() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			iniciarPrincipal((String) args[0], (String) args[1], 
					(String) args[2], Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), Integer.parseInt((String) args[6]),
					Integer.parseInt((String) args[7]), false);
			cargaPrincipal(getInteligencia()*2);// el ayudante da
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
		Gui.setHistoria(getSexo() + " " + getRol() + " " + getLocalName() + hablar("Inicio") + getLocalizacion() +".");

		addBehaviour(new AyudaArcana());
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		if (estaMuerto()) {
			Gui.setHistoria(getLocalName() + hablar("Muerte")+ "\n");
		} else {
			Gui.setHistoria(getLocalName() + hablar("Fin")+ "\n");
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
					planificar(msg.getContent());
					addBehaviour(new FinPlanificacion());
					
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

	private class FinPlanificacion extends Behaviour {
		ACLMessage receive;

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fin-Plan"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));

			receive = receive(mt);

			if (receive != null) {

				doDelete();

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}
}