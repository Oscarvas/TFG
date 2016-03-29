package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class OfrecerServicios extends CyclicBehaviour {
	private int precio;
	public OfrecerServicios(int precio) {
		// TODO Auto-generated constructor stub
		this.precio = precio;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("SolicitarServicio"),
					MessageTemplate.MatchPerformative(ACLMessage.CFP));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				ACLMessage reply = msg.createReply();

				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(precio));

				myAgent.send(reply);

			} else
				block();
	}

}
