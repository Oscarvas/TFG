package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class DejarEnCasa {

	private Personaje personaje;
	private String victima;
	private AID agenteMundo;

	public DejarEnCasa(Personaje personaje, String victima) {

		this.personaje = personaje;
		this.victima = victima;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {

		ACLMessage dejarCasa = new ACLMessage(ACLMessage.REQUEST);
		dejarCasa.addReceiver(agenteMundo);
		dejarCasa.setConversationId("Dejar en Casa");
		dejarCasa.setReplyWith("dejar-casa" + System.currentTimeMillis());
		dejarCasa.setContent(victima);
		personaje.send(dejarCasa);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Dejar en Casa"),
				MessageTemplate.MatchInReplyTo(dejarCasa.getReplyWith()));

		ACLMessage msg = personaje.blockingReceive(mt);
		
		Gui.setHistoria(" El aspirante " +personaje.getLocalName() + " ha dejado a la victima "+ msg.getContent() + " donde desperto. \n");

	}
}
