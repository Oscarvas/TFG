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
public class Druida extends Protagonista {
	private String victima;
	private String dragon;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal((String) args[0], (String) args[1], 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), Integer.parseInt((String) args[6]), true);
		}

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Cambiaformas");
		sd.setName(getLocalName()+"-Cambiaformas");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		localizarPersonaje();
		Gui.setHistoria(getLocalName()+" el druida pens� que era buena idea transformarse conejo en estas fechas.");
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
			Gui.setHistoria(getLocalName()+": Saludo a la noche...\n");
		}else{
			Gui.setHistoria(getLocalName()+": Que Elune nos conceda fuerza....\n");
		}
	}

	public String getVictima() {
		return victima;
	}

	public void setVictima(String victima) {
		this.victima = victima;
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
				setVictima(contrato[0]);
				setDragon(contrato[1]);

				ACLMessage salvar = new ACLMessage(ACLMessage.INFORM);
				salvar.setConversationId("ObjetivoSecuestro");
				salvar.addReceiver(getAgenteMundo());
				salvar.setContent(getVictima());
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
