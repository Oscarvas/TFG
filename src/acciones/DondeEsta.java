package acciones;

import personajes.Personaje;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DondeEsta {
	private Personaje personaje;
	private String buscado;
	private AID agenteMundo;
	
	public DondeEsta(Personaje myAgent, String buscado, AID mundo){
		this.personaje = myAgent;
		this.buscado = buscado;
		this.agenteMundo = myAgent.getAgenteMundo();
	}
	public String execute() {
		
		ACLMessage buscar = new ACLMessage(ACLMessage.REQUEST);
		buscar.addReceiver(new AID ((String) "Azeroth", AID.ISLOCALNAME));
		buscar.setConversationId("Localizar");
		buscar.setReplyWith("localizar" + System.currentTimeMillis());
		buscar.setContent(buscado);
				
		personaje.send(buscar);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(buscar.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);
		
		return reply.getContent();
		
	}	
	
}
