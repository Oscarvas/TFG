package acciones;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import objetos.Clave;
import personajes.Personaje;

public class MoverObjetos {

	private Personaje personaje;
	private AID agenteMundo;

	public MoverObjetos(Personaje personaje) {

		this.personaje = personaje;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {
		String objetos = "";
		
		//creo un array con los nombres de los objetos
		for (Clave obj : personaje.getMochila())
			objetos+= " " + obj.getId();

		ACLMessage mover = new ACLMessage(ACLMessage.REQUEST);
		mover.addReceiver(agenteMundo);
		mover.setConversationId("MoverObjetos");
		mover.setReplyWith("mover" + System.currentTimeMillis());
		mover.setContent(personaje.getLocalizacion() + objetos);
		personaje.send(mover);

		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("MoverObjetos"),
				MessageTemplate.MatchInReplyTo(mover.getReplyWith()));

		@SuppressWarnings("unused")
		ACLMessage msg = personaje.blockingReceive(mt);

		personaje.actualizarObjetos();

	}
}
