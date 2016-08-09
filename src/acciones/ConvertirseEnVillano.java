package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class ConvertirseEnVillano {

	private Personaje personaje;
	private AID agenteMundo;
	
	public ConvertirseEnVillano (Personaje personaje) {
		
		this.personaje = personaje;
		this.agenteMundo = personaje.getAgenteMundo();
		
	}
	
	public void execute() {
		
		ACLMessage serSabio = new ACLMessage(ACLMessage.REQUEST);
		serSabio.addReceiver(agenteMundo);
		serSabio.setConversationId("Ser Villano");
		serSabio.setReplyWith("servillano" + System.currentTimeMillis());
		personaje.send(serSabio);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(serSabio.getReplyWith());
		personaje.blockingReceive(mt);

		Gui.setHistoria(personaje.getLocalName()+": ¡¡¡ Muaaajajajaja, os jodeis insensatos !!!");
		
	}
}
