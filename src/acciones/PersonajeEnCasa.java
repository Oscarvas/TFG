package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PersonajeEnCasa extends CyclicBehaviour{
	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("Dejar en Casa"));

		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {

			String princesa = receive.getContent();
			estado.liberar(princesa);
			estado.borrarPersonajeConPrincesa(receive.getSender()
					.getLocalName());
			estado.añadirPrincesaSalvada(princesa);
			estado.estaLibrePersonaje(receive.getSender().getLocalName());

			ACLMessage reply = receive.createReply();
			reply.setContent(estado.nombreCorrecto(princesa));
			myAgent.send(reply);

		} else
			block();
	}

}
