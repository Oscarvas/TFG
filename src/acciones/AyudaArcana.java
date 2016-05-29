package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class AyudaArcana extends CyclicBehaviour {
	private String localizacion;
	
	public AyudaArcana (String localizacion){
		this.localizacion = localizacion;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("AyudaMado"),
					MessageTemplate.MatchPerformative(ACLMessage.CFP));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				ACLMessage reply = msg.createReply();

				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(localizacion);

				myAgent.send(reply);

			} else
				block();
	}

}