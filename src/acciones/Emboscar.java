package acciones;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Emboscar extends CyclicBehaviour {
	private String localizacion;
	
	public Emboscar (String localizacion){
		this.localizacion = localizacion;
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

				/*
				 * se puede modificar para que al añadir el comportamiento se especifique que clase
				 * nos interesa emboscar, asi tendriamos personajes que atracan victimas y otros a caballeros por ejemplo
				 * */
				if (msg.getContent().equalsIgnoreCase("caballero")){ 
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(localizacion);
				}
				else reply.setPerformative(ACLMessage.REFUSE); //refuse puuede ser otra opcion

				myAgent.send(reply);

			} else
				block();
	}

}
