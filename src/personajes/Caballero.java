package personajes;

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
import ontologia.Mitologia;
import ontologia.Vocabulario;

@SuppressWarnings("serial")
public class Caballero extends Personaje {
	
	private int precio;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
		}
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Matadragones");
		sd.setName(getLocalName()+"-Matadragones");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		precio = Vocabulario.SALARIO * getCodicia();
		
		localizarPersonaje();
		Gui.setHistoria("El caballero "+getLocalName()+" se ha despertado en "+getLocalizacion()+" con su armadura hecha polvo.");
		
		addBehaviour(new OfrecerServicios(precio));
		addBehaviour(new AceptarOfertaRescate());
		addBehaviour(new PagoImpuestos());
	}
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	private class AceptarOfertaRescate extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate
					.MatchConversationId("TratoHecho"), MessageTemplate
					.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				//String princesa = msg.getContent().split(" ")[0];
				//String dragon = msg.getContent().split(" ")[1];

				try {
					planificar();
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
			
			if ( receive != null ) {
				
				ACLMessage rescate = new ACLMessage(ACLMessage.INFORM);
				rescate.setConversationId("Rescate");
				rescate.addReceiver(rey);
				
				if ( estaMuerto() ) {
					
					Gui.setHistoria("+ El caballero " + getLocalName()+ " ha muerto en combate. \n");
					
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
	
	private class PagoImpuestos extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Hacienda"));
			ACLMessage receive = myAgent.receive(mt);
		
			if (receive != null) {
				ACLMessage reply = receive.createReply();
				
				reply.setContent(receive.getContent());
				precio -= Integer.parseInt(receive.getContent());
				Gui.setHistoria(myAgent.getLocalName()+" se ha visto obligado a pagar "+receive.getContent()+" si queria ser capaz de cruzar con vida");
				
				myAgent.send(reply);
		
			} else
				block();
			
		}
		
	}
	
}
