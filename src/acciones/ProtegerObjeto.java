package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import objetos.Clave;
import personajes.Personaje;

public class ProtegerObjeto {

	private Personaje personaje;
	private String objeto;
	private AID agenteMundo;

	public ProtegerObjeto(Personaje personaje, String objeto) {

		this.personaje = personaje;
		this.objeto = objeto;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {

		ACLMessage proteger = new ACLMessage(ACLMessage.REQUEST);
		proteger.addReceiver(agenteMundo);
		proteger.setConversationId("Proteger");
		proteger.setReplyWith("proteger" + System.currentTimeMillis());
		proteger.setContent(objeto);
		personaje.send(proteger);

		MessageTemplate mt = MessageTemplate.MatchInReplyTo(proteger.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);

		personaje.guardarObjeto(new Clave(objeto, reply.getContent(), personaje.getLocalizacion()));
		
		Gui.setHistoria(personaje.getLocalName() + " ha encontrado el objeto "+ objeto + "en "+ personaje.getLocalizacion()+" \n");

	}
}
