package personajes.monstruos;

import java.util.Random;

import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Troll extends Monstruo {
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1], (String) args[2]);
		Gui.setHistoria("Parece que mientras "+getSexo() +" " + getEspecie()+ " "+getLocalName()+" sea guardián de "+getLocalizacion()+", la desgracia caerá sobre cada insensato que pase por ahí.");
		addBehaviour(new Guardian());
	}
	
	protected void takeDown (){
		Gui.setHistoria(getLocalName()+" se retira");
	}
	
	private class Guardian extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Cruzar"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				String hist=getLocalName()+": ¿Es aquello un caballero?... ¡¡¡Día de paga!!!";
				Gui.setHistoria(hist);
				
				ACLMessage impuestos = new ACLMessage(ACLMessage.REQUEST);
				impuestos.setConversationId("Hacienda");
				impuestos.setReplyWith("hacienda" + System.currentTimeMillis());
				impuestos.addReceiver(getAID(receive.getContent()));
				impuestos.setContent(String.valueOf(new Random().nextInt(50)+1));	
				send(impuestos);
				
				MessageTemplate imp = MessageTemplate
						.MatchInReplyTo(impuestos.getReplyWith());
				ACLMessage reply = myAgent.blockingReceive(imp);				
				Gui.setHistoria(getLocalName()+": Un caballero con dinero es un caballero vivo, puedes continuar "+reply.getSender().getLocalName());				

//				send(receive.createReply());//respuesta al mundo
				send(reply.createReply());//respuesta al caballero
				

			} else
				block();
			
		}
		
	}
}
