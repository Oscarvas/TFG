package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Liberar extends CyclicBehaviour{
	public void action() {

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("Liberar"));

		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {

			String[] contenido = receive.getContent().split(" ");
			estado.estaLlenoPersonaje(contenido[0]);
			estado.añadirPersonajeConPrincesa(contenido[0], contenido[1]);
			estado.borrarPersonajeConPrincesa(contenido[2]);

			ACLMessage reply = receive.createReply();
			reply.setContent(estado.nombreCorrecto(contenido[1]));
			myAgent.send(reply);

		} else
			block();
	}

}
