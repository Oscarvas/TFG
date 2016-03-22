package acciones;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class MoverPrincesaSecuestrada extends CyclicBehaviour {
	MessageTemplate mt;

	public void action() {

		mt = MessageTemplate
				.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
						MessageTemplate
								.MatchConversationId("Mundo-Mover-Princesa"));
		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {

			String[] contenido = receive.getContent().split(" ");
			ACLMessage moverPrincesa = new ACLMessage(ACLMessage.REQUEST);
			moverPrincesa.setReplyWith("mover-princesa"
					+ System.currentTimeMillis());
			moverPrincesa.setConversationId("Mover-Princesa");
			moverPrincesa.setContent(contenido[0] + " "
					+ receive.getSender().getLocalName());
			AID princesa = new AID(
					(String) estado.nombreCorrecto(contenido[1]),
					AID.ISLOCALNAME);
			moverPrincesa.addReceiver(princesa);
			myAgent.send(moverPrincesa);

			mt = MessageTemplate.MatchInReplyTo(moverPrincesa
					.getReplyWith());
			ACLMessage rec1 = myAgent.receive(mt);
			while (rec1 == null)
				block();

			myAgent.send(receive.createReply());

		} else
			block();
	}
}
