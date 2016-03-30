package personajes;

import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Troll extends Personaje {
	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria("Parece que mientras "+getLocalName()+" sea el guardián del "+getLocalizacion()+", la desgracia caerá sobre cada insensato que pase por ahí.");
	}
	
	protected void takeDown (){
		Gui.setHistoria(getLocalName()+" se retira");
	}
	
	private class Guardian extends CyclicBehaviour {

		private MessageTemplate mt,imp;

		@Override
		public void action() {
			// TODO Auto-generated method stub

			mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
							MessageTemplate.MatchConversationId("Cruzar"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				ACLMessage impuestos = new ACLMessage(ACLMessage.INFORM);
				impuestos.addReceiver(getAID(receive.getContent()));
				impuestos.setConversationId("Hacienda");
				impuestos.setReplyWith("hacienda" + System.currentTimeMillis());
				impuestos.setContent("17");
				send(impuestos);
				
				imp = MessageTemplate.MatchInReplyTo(impuestos.getReplyWith());
				ACLMessage reply = myAgent.blockingReceive(imp);
				
				Gui.setHistoria(myAgent.getLocalName()+" ha recibido el pago de "+reply.getContent()+" del caballero "+reply.getSender().getLocalName());				

				send(receive.createReply());

			} else
				block();
			
		}
		
	}
}
