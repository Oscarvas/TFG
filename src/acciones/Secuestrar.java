package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class Secuestrar {

	private Personaje personaje;
	private String princesa;
	private AID agenteMundo;
	
	
	public Secuestrar(Personaje personaje, String princesa) {
		
		this.personaje = personaje;
		this.princesa = princesa;
		this.agenteMundo = personaje.getAgenteMundo();
		
	}
	
	
	public boolean execute() {
		ACLMessage secuestro = new ACLMessage(ACLMessage.INFORM);
		secuestro.addReceiver(agenteMundo);
		secuestro.setConversationId("Secuestro");
		secuestro.setReplyWith("secuestro" + System.currentTimeMillis());
		secuestro.setContent(princesa);
		personaje.send(secuestro);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(secuestro.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);
		
		if ( !reply.getContent().equalsIgnoreCase("fallo") ) {
			
			Gui.setHistoria( personaje.getLocalName() + " ha secuestrado a " + reply.getContent() + ". \n");
			return true;
			
		} else
			return false;
	}
}
