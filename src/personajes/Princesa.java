package personajes;

import java.util.Random;

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
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import ontologia.Mitologia;
import ontologia.Vocabulario;

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
		
		addBehaviour(new Huerfana());
	}
	
	protected void takeDown() {
		Gui.setHistoria("* La Princesa " + getLocalName() + " pone fin a su aventura. \n");
	}
	private class Huerfana extends Behaviour{

		/**
		 * Asigna un padre aleatorio a la princesa
		 */
		boolean ok;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Padre");
			template.addServices(sd);
			
			try{
				DFAgentDescription[] result = DFService.search(myAgent, template);
				AID[] reyes = new AID[result.length];
				for (int i = 0; i < result.length; i++){
					reyes[i] = result[i].getName();
				}
				
				if (reyes.length != 0) {
					padre = reyes[new Random().nextInt(reyes.length)];
					localizarPersonaje();
										
					Gui.setHistoria(myAgent.getLocalName()+": Alguien debe avisar a mi padre "+padre.getLocalName()+" que me he perdido en "+getLocalizacion());
					
					DFAgentDescription dfd = new DFAgentDescription();
					dfd.setName(myAgent.getAID());
					ServiceDescription sd2 = new ServiceDescription();
					sd2.setType("Secuestrable");
					sd2.setName(myAgent.getLocalName()+"-Secuestrable");
					dfd.addServices(sd2);
					
					try{
						DFService.register(myAgent, dfd);
					} catch (FIPAException fe){
						fe.printStackTrace();
					}
					
					addBehaviour(new MoverSecuestrada());
					addBehaviour(new AvisaAPadre());
					addBehaviour(new Rescatada());
					
					ok = true;
					
				} else {
					Thread.sleep(5000);
					reset();
				}
						
			} catch (Exception e){ 
				e.printStackTrace();
			}
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return ok;
		}
		
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
							escapar.setContent(String.valueOf(Vocabulario.VIDA_MONSTRUO));							
							send(escapar);
					
							Gui.setHistoria(getLocalName()+": ¡Libérame "+dragon.getLocalName()+"!");
							
							MessageTemplate imp = MessageTemplate.MatchInReplyTo(escapar.getReplyWith());
							ACLMessage reply = myAgent.receive(imp);
							
							if (reply.getPerformative() == ACLMessage.CONFIRM) {

								Gui.setHistoria("La princesa "+getLocalName()+" se ha escapado de las zarpas de "+dragon.getLocalName());
								stop();
								try {
									planificar();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
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
