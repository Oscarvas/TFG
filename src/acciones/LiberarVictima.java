package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class LiberarVictima {

	private Personaje personaje;
	private String victima, dragon;
	private AID agenteMundo;

	public LiberarVictima(Personaje personaje, String victima, String dragon) {

		this.personaje = personaje;
		this.victima = victima;
		this.dragon = dragon;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {

		ACLMessage liberarVictima = new ACLMessage(ACLMessage.REQUEST);
		liberarVictima.addReceiver(agenteMundo);
		liberarVictima.setConversationId("Liberar");
		liberarVictima.setReplyWith("liberar" + System.currentTimeMillis());
		liberarVictima.setContent(personaje.getLocalName() + " " + victima
				+ " " + dragon);
		personaje.send(liberarVictima);

		MessageTemplate mt = MessageTemplate.MatchInReplyTo(liberarVictima
				.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);

		
		Gui.setHistoria( " El aspirante "+ personaje.getLocalName() + " ha liberado a la victima "+ reply.getContent() + ". \n");

	}
}
