package acciones;

import entorno.Localizacion;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class LocalizarPersonajes extends CyclicBehaviour{
	public void action() {

		boolean ok = true;

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("Mover"));
		ACLMessage receive = myAgent.receive(mt);

		if (receive != null) {
			ACLMessage reply = receive.createReply();
			String[] mensaje = receive.getContent().split(" ");
			String locDest = mensaje[1];
			AID personaje = receive.getSender();

			Localizacion loc2 = mapa.getLocalizacion(locDest);

			if (mensaje.length == 3) {

				String locOrigen = mensaje[2];
				Localizacion loc1 = mapa.getLocalizacion(locOrigen);

				if (loc1 != null && loc1.existeConexion(locDest))
					ok = loc1.eliminarPersonaje(personaje.getLocalName());

				else
					ok = false;
			}

			if (ok && loc2 != null) {
				loc2.añadirPersonaje(personaje.getLocalName());
				estado.añadirLocalizacion(personaje.getLocalName(), locDest);
				reply.setPerformative(ACLMessage.CONFIRM);
				reply.setContent(loc2.getNombre());

				if (mensaje.length == 2) {
					estado.añadirPersonaje(mensaje[0],
							personaje.getLocalName());
					estado.añadirCasa(personaje.getLocalName(), locDest);
					estado.añadirNombre(personaje.getLocalName());
				}
			} else
				reply.setPerformative(ACLMessage.FAILURE);

			send(reply);

		} else
			block();
	}
}
