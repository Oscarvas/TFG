package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class DejarEnCasa {

	private Personaje personaje;
	private String princesa;
	private AID agenteMundo;

	public DejarEnCasa(Personaje personaje, String princesa, AID agenteMundo) {

		this.personaje = personaje;
		this.princesa = princesa;
		this.agenteMundo = agenteMundo;

	}

	public void execute() {

		ACLMessage dejarCasa = new ACLMessage(ACLMessage.REQUEST);
		dejarCasa.addReceiver(agenteMundo);
		dejarCasa.setConversationId("Dejar en Casa");
		dejarCasa.setReplyWith("dejar-casa" + System.currentTimeMillis());
		dejarCasa.setContent(princesa);
		personaje.send(dejarCasa);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Dejar en Casa"),
				MessageTemplate.MatchInReplyTo(dejarCasa.getReplyWith()));

		ACLMessage msg = personaje.blockingReceive(mt);
		
		Gui.setHistoria(" El caballero " +personaje.getLocalName() + " ha dejado a la princesa "+ msg.getContent() + " donde desperto. \n");

	}
}
