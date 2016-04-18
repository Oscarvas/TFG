package acciones;

import personajes.Personaje;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/*
 * Esto nos permitira que un personaje obtenga
 * informacion sobre la localizacion de otro personaje
 * */
public class DondeEsta {
	private Personaje personaje;
	private String buscado;
	private AID agenteMundo;
	
	public DondeEsta(Agent myAgent, String buscado){
		this.personaje = (Personaje) myAgent;
		this.buscado = buscado;
		this.agenteMundo = ((Personaje) myAgent).getAgenteMundo();
	}
	public String execute() {
		
		ACLMessage buscar = new ACLMessage(ACLMessage.REQUEST);
		buscar.addReceiver(agenteMundo);
		buscar.setConversationId("Localizar");
		buscar.setReplyWith("localizar" + System.currentTimeMillis());
		buscar.setContent(buscado);
				
		personaje.send(buscar);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(buscar.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);
		
		return reply.getContent();
		
	}	
	
}
