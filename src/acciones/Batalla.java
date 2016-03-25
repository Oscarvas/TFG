package acciones;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

public class Batalla extends CyclicBehaviour{
	private AID contrincante;
	Personaje contendiente;

	public Batalla(Personaje contendiente, AID contrincante) {
		super(contendiente);
		this.contendiente = contendiente;
		this.contrincante = contrincante;
	}

	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("batalla"));
		ACLMessage batalla = myAgent.receive(mt);
		
		if (batalla != null) {			
			batalla.addReceiver(this.contrincante);
			batalla.setConversationId("Batalla");
			batalla.setReplyWith("batalla" + System.currentTimeMillis());
			//batalla.setContentObject(contendiente.getVida());

			
			contendiente.getLogger().info(contendiente.getLocalName() + " entabla batalla con " + contendiente);
			System.out.println(" " + contendiente.getLocalName() 
					+ " entabla batalla con " + contendiente + ". \n");
			/*
			 * System.out.println(contendiente.marcaDeClase() + " " + contendiente.getLocalName() 
					+ " entabla batalla con " + contendiente + ". \n");
			 * */
			
			contendiente.send(batalla);
			
			MessageTemplate mt2 = MessageTemplate.MatchInReplyTo(batalla.getReplyWith());
			ACLMessage reply = contendiente.blockingReceive(mt2);
			contendiente.a�adirVida( -Integer.parseInt(reply.getContent()) );
		} else
			block();
		
	}

}