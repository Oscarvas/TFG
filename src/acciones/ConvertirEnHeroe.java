package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConvertirEnHeroe extends CyclicBehaviour{
	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("Ser Heroe"));

		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {
			estado.añadirHeroe(receive.getSender().getLocalName());
			myAgent.send(receive.createReply());

		} else
			block();

	}

}
