package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class ConvertirseEnSabio {

	private Personaje personaje;
	private AID agenteMundo;
	
	public ConvertirseEnSabio (Personaje personaje) {
		
		this.personaje = personaje;
		this.agenteMundo = personaje.getAgenteMundo();
		
	}
	
	public void execute() {
		
		ACLMessage serSabio = new ACLMessage(ACLMessage.REQUEST);
		serSabio.addReceiver(agenteMundo);
		serSabio.setConversationId("Ser Sabio");
		serSabio.setReplyWith("sersabio" + System.currentTimeMillis());
		personaje.send(serSabio);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(serSabio.getReplyWith());
		personaje.blockingReceive(mt);

		Gui.setHistoria(personaje.getSexo() +" "+ personaje.getClase() +" "+ personaje.getLocalName() + personaje.hablar("Sabio")+ "\n");
		
	}
}
