package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Secuestro extends CyclicBehaviour{
	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchConversationId("Secuestro"));
		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {

			ACLMessage reply = receive.createReply();
			String princesa = receive.getContent();
			String secuestrador = receive.getSender().getLocalName();

			if (estado.estanMismaLocalizacion(secuestrador, princesa)) {
				estado.añadirPersonajeConPrincesa(secuestrador, princesa);
				estado.secuestrar(princesa);
				estado.estaLlenoPersonaje(secuestrador);

				reply.setContent(estado.nombreCorrecto(princesa));

			} else
				reply.setContent("fallo");

			myAgent.send(reply);

		} else
			block();
	}

}
