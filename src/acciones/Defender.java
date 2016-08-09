package acciones;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Defender extends CyclicBehaviour {
	private Personaje personaje;
	public Defender(Agent myAgent){
		this.personaje = (Personaje) myAgent;
	}
	
	ACLMessage receive;
	
	@Override
	public void action() {
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Batalla"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		receive = myAgent.receive(mt);
		
		if ( receive != null ) {
			ACLMessage reply = receive.createReply();
				
			reply.setContent(Integer.toString(personaje.getVida()));
			System.err.println("En esta esquina, "+personaje.getLocalName()+" va a meter una hostia de "+ personaje.getVida());
			personaje.añadirVida(-Integer.parseInt(receive.getContent()));				
			
			myAgent.send(reply);
			
			if ( personaje.estaMuerto() )
				System.err.println(personaje.getLocalName()+" HA SIDO ANIQUILADO ****");
				myAgent.doDelete();
			
		} else
			block();
	}
}
