package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MuertePersonaje extends CyclicBehaviour{
	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("Muerto"));
		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {
			estado.mata(receive.getContent());
			myAgent.send(receive.createReply());

		} else
			block();
	}

}
