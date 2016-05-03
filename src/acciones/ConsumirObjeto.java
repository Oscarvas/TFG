package acciones;

import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.principal.Protagonista;

@SuppressWarnings("serial")
public class ConsumirObjeto extends CyclicBehaviour {
	
	private Protagonista per;
	
	public ConsumirObjeto(Protagonista per) {
		this.per = per;
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Consumir"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			
			Gui.setHistoria(per.usarObjeto(msg.getContent()));;

		} else
			block();

	}

}
