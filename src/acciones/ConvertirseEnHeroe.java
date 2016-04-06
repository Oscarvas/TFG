package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class ConvertirseEnHeroe {

	private Personaje personaje;
	private AID agenteMundo;
	
	public ConvertirseEnHeroe (Personaje personaje) {
		
		this.personaje = personaje;
		this.agenteMundo = personaje.getAgenteMundo();
		
	}
	
	public void execute() {
		
		ACLMessage serHeroe = new ACLMessage(ACLMessage.REQUEST);
		serHeroe.addReceiver(agenteMundo);
		serHeroe.setConversationId("Ser Heroe");
		serHeroe.setReplyWith("serheroe" + System.currentTimeMillis());
		personaje.send(serHeroe);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(serHeroe.getReplyWith());
		personaje.blockingReceive(mt);

		Gui.setHistoria("+ El caballero " + personaje.getLocalName()+ " se ha convertido en héroe. \n");
		
	}
	
}
