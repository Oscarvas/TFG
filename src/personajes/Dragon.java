package personajes;

import java.util.Random;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Dragon extends Personaje {
	public AID princesaSecuestrada;

	protected void setup(){
		iniciarMonstruo();
		Gui.setHistoria("Desde "+getLocalizacion()+ ", el imponente rugido del drag�n "+getLocalName()+" se escucha por todo el reino.");
		addBehaviour(new Secuestro());
	}
	protected void takeDown() {
		
		if ( estaMuerto() )
			Gui.setHistoria("# El drag�n " + getAID().getLocalName() + " ha muerto en batalla. \n");
		
		else
			Gui.setHistoria("# El drag�n " + getAID().getLocalName() + " se retira. \n");
			
	}
	
	
	private class Secuestro extends Behaviour {
			
			boolean ok;
			
			public void action() {
				
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Secuestrable");
				template.addServices(sd);
						
				try{
					DFAgentDescription[] result = DFService.search(myAgent, template);
					//Gui.setHistoria("# Las siguientes princesas son secuestrables:");
					AID[] PrincesasSecuestrables = new AID[result.length];
					for (int i = 0; i < result.length; i++){
						PrincesasSecuestrables[i] = result[i].getName();
						//System.out.println("\t" + PrincesasSecuestrables[i].getLocalName());
					}
					System.out.println();
					
					if (PrincesasSecuestrables.length != 0) {
						princesaSecuestrada = PrincesasSecuestrables[new Random().nextInt(PrincesasSecuestrables.length)];
						planificar();
						addBehaviour(new FalloSecuestro());
						addBehaviour(new FinPlanificacion());
						
						ok = true;
						
					} else {
						Thread.sleep(3000);
						reset();
					}
							
				} catch (Exception e){ 
					e.printStackTrace();
				}
						
				if( !done() )
					reset();
			}
	
			@Override
			public boolean done() {
				return ok;
			}
		}		
	
	private class FinPlanificacion extends Behaviour {
		
		ACLMessage receive;
		
		public void action() {
	
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fin-Plan"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			
			receive = receive(mt);
			
			if ( receive != null ) {
				ACLMessage secuestrar = new ACLMessage(ACLMessage.INFORM);
				secuestrar.setConversationId("Te secuestro");
				secuestrar.addReceiver(princesaSecuestrada);
		
				myAgent.send(secuestrar);
				
				MessageTemplate mt1 = MessageTemplate.MatchConversationId("Te secuestro");
				myAgent.blockingReceive(mt1);
			
				addBehaviour(new Defender());
				
			} else
				block();
		}
	
		@Override
		public boolean done() {
			return receive != null;
		}
	}
	
	
	private class FalloSecuestro extends Behaviour {
		
		ACLMessage receive;
		
		public void action() {
			
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fallo Secuestro"),
					MessageTemplate.MatchPerformative(ACLMessage.FAILURE));
			
			receive = receive(mt);
			
			if ( receive != null ) {
				
				try {
					
					Thread.sleep(3000);
					myAgent.addBehaviour(new Secuestro());
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			} else
				block();
		}
	
		public boolean done() {
			return receive != null;
		}
	}
		
	private class Defender extends CyclicBehaviour {
			
			ACLMessage receive;
			
			@Override
			public void action() {
				
				MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Batalla"),
						MessageTemplate.MatchPerformative(ACLMessage.INFORM));
				receive = myAgent.receive(mt);
				
				if ( receive != null ) {
					ACLMessage reply = receive.createReply();
						
					reply.setContent(Integer.toString(getVida()));
					a�adirVida(-Integer.parseInt(receive.getContent()));				
					
					myAgent.send(reply);
					
					if ( estaMuerto() )
						doDelete();
					
				} else
					block();
			}
		}

}
