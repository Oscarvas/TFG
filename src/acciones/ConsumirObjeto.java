package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.principal.Protagonista;

@SuppressWarnings("serial")
public class ObjetoConsumible extends CyclicBehaviour {
	
	private Protagonista per;
	
	public ObjetoConsumible(Protagonista per) {
		// TODO Auto-generated constructor stub
		this.per = per;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Consumir"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			
			per.usarObjeto(msg.getContent());

		} else
			block();

	}

}
