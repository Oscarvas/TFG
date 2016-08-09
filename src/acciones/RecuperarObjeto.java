package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import objetos.Clave;
import personajes.Personaje;

public class RecuperarObjeto {

	private Personaje personaje;
	private String objeto,guardian;
	private AID agenteMundo;

	public RecuperarObjeto(Personaje personaje, String guardian, String objeto) {

		this.personaje = personaje;
		this.guardian = guardian;
		this.objeto = objeto;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {

		ACLMessage proteger = new ACLMessage(ACLMessage.REQUEST);
		proteger.addReceiver(agenteMundo);
		proteger.setConversationId("RecuperarObjeto");
		proteger.setReplyWith("RecuperarObjeto" + System.currentTimeMillis());
		proteger.setContent(guardian + " " +objeto);
		personaje.send(proteger);

		MessageTemplate mt = MessageTemplate.MatchInReplyTo(proteger.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);
		
		String [] info = reply.getContent().split(" ");
		
		personaje.guardarObjeto(new Clave(info[0], info[1], personaje.getLocalizacion()));
		Gui.setHistoria(personaje.getLocalName() + " ha recuperado el objeto "+ info[0] + " en "+ personaje.getLocalizacion()+" \n");

	}
}
