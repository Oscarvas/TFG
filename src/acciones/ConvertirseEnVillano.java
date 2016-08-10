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
		
		ACLMessage serVillano = new ACLMessage(ACLMessage.REQUEST);
		serVillano.addReceiver(agenteMundo);
		serVillano.setConversationId("Ser Villano");
		serVillano.setReplyWith("servillano" + System.currentTimeMillis());
		personaje.send(serVillano);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(serVillano.getReplyWith());
		personaje.blockingReceive(mt);

		Gui.setHistoria(personaje.getLocalName()+": ¡¡¡ Muaaajajajaja, os jodeis insensatos !!!");
		
	}
}
