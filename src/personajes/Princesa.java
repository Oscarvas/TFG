package personajes;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontologia.Mitologia;

@SuppressWarnings("serial")
public class Princesa extends Personaje {
	private AID padre;
	public AID dragon;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
		}
		localizarPersonaje();
		Gui.setHistoria("En un arrebato de rebeldía, la princesa "+getLocalName()+" ha llegado hasta "+getLocalizacion()+".");
		
		padre = getAID("Felipe");

		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Secuestrable");
		sd.setName(getLocalName()+"-Secuestrable");
		dfd.addServices(sd);
		
		try{
			DFService.register(this, dfd);
		} catch (FIPAException fe){
			fe.printStackTrace();
		}
		
		addBehaviour(new MoverSecuestrada());
		addBehaviour(new AvisaAPadre());
		addBehaviour(new Rescatada());
	}
	
	protected void takeDown() {
		Gui.setHistoria("* La Princesa " + getLocalName() + " pone fin a su aventura. \n");
	}
	
	private class MoverSecuestrada extends CyclicBehaviour {
		
		public void action() {
			
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Mover-Princesa"));
			ACLMessage receive = myAgent.receive(mt);
			
			if ( receive != null ) {

				moverSecuestrado(receive.getContent());
				ACLMessage reply = receive.createReply();
				myAgent.send(reply);	
				
			} else
				block();
			
		}
	}
	
	private class AvisaAPadre extends Behaviour {

		private ACLMessage receive;	
		
		public void action() {
			
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Te secuestro"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			receive = receive(mt);
			
			if ( receive != null ) {
				
				try{
					DFService.deregister(myAgent);
				}catch (FIPAException fe){
					fe.printStackTrace();
				}
				
				dragon = receive.getSender();
				send(receive.createReply());	
			
				Gui.setHistoria("* La Princesa " + myAgent.getLocalName() + " ha sido secuestrada. \n");
				
				ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);
				inform.setConversationId("Ayuda");
				inform.setReplyWith("request" + System.currentTimeMillis());
				inform.addReceiver(padre);
				inform.setContent(dragon.getLocalName());
				myAgent.send(inform);
				
				myAgent.addBehaviour(new TickerBehaviour(myAgent, 500){
						
						@Override
						protected void onTick() {
							ACLMessage escapar = new ACLMessage(ACLMessage.REQUEST);
							escapar.addReceiver(dragon);
							escapar.setConversationId("mujerIndependiente");
							escapar.setReplyWith("mujerIndependiente" + System.currentTimeMillis());
							escapar.setContent(String.valueOf(getFuerza()));							
							send(escapar);
							
							MessageTemplate imp = MessageTemplate.MatchInReplyTo(escapar.getReplyWith());
							ACLMessage reply = myAgent.blockingReceive(imp);
							
							if (reply.getPerformative() == ACLMessage.CONFIRM) {

								Gui.setHistoria("La princesa "+getLocalName()+" se ha escapado de las zarpas de "+dragon.getLocalName());
								stop();
								try {
									//planificar();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							setFuerza(getFuerza()+1); //la princesa se entrena mazo
						}
					});

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}
	
	private class Rescatada extends Behaviour {
		
		ACLMessage receive;
		
		public void action() {
			
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Rescatada"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			receive = receive(mt);
			
			if ( receive != null ) {
				
				myAgent.doDelete();
				
			} else
				block();
			
		}
		
		public boolean done() {
			return receive != null;
		}
	}


}
