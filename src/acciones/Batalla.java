package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class Batalla {

	private Personaje personaje;
	private String secundario;
	
	
	public Batalla(Personaje personaje, String secundario) {
		
		this.personaje = personaje;
		this.secundario = secundario;
		
	}
	
	
	public void execute() {
		
		ACLMessage batalla = new ACLMessage(ACLMessage.INFORM);
		batalla.addReceiver(new AID ((String) secundario, AID.ISLOCALNAME));
		batalla.setConversationId("Batalla");
		batalla.setReplyWith("batalla" + System.currentTimeMillis());
		batalla.setContent(Integer.toString(personaje.getPrincipal()));
		
		Gui.setHistoria(personaje.getLocalName()+ personaje.hablar("Batalla") + ". \n");
		
		personaje.send(batalla);
		
		MessageTemplate mt = MessageTemplate.MatchInReplyTo(batalla.getReplyWith());
		ACLMessage reply = personaje.blockingReceive(mt);
		System.err.println("\n se recibe respuesta de la batalla entre: "+ personaje.getLocalName()+" y "+ secundario);
		System.err.println(personaje.getLocalName()+" recibe una hostia de "+reply.getContent() + " y la va a devolver de "+
				personaje.getPrincipal()+", su vida actual es "+personaje.getVida());
		personaje.añadirVida( -Integer.parseInt(reply.getContent()) );
		
	}
}
