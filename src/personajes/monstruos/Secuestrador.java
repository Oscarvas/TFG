package personajes.monstruos;

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
public class Secuestrador extends Monstruo {
	public AID princesaSecuestrada;

	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1], (String) args[2]);
		Gui.setHistoria("Desde "+getLocalizacion()+ ", el imponente rugido de "+getSexo() + " " + getEspecie() + " " +getLocalName()+" se escucha por todo el reino.");
		addBehaviour(new Secuestro());
	}
	protected void takeDown() {
		
		if ( estaMuerto() )
			Gui.setHistoria("# El dragón " + getAID().getLocalName() + " ha muerto en batalla. \n");
		
		else
			Gui.setHistoria("# El dragón " + getAID().getLocalName() + " se retira. \n");
		
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("mujerIndependiente"),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		ACLMessage receive = receive(mt);
		
		if ( receive != null ) {
			ACLMessage reply = receive.createReply();
			
			reply.setPerformative(ACLMessage.FAILURE);
			send(reply);
					
		}
			
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
					AID[] PrincesasSecuestrables = new AID[result.length];
					for (int i = 0; i < result.length; i++){
						PrincesasSecuestrables[i] = result[i].getName();
					}
					
					if (PrincesasSecuestrables.length != 0) {
						princesaSecuestrada = PrincesasSecuestrables[new Random().nextInt(PrincesasSecuestrables.length)];
						
						ACLMessage secuestrar = new ACLMessage(ACLMessage.INFORM);
						secuestrar.setConversationId("ObjetivoSecuestro");
						secuestrar.addReceiver(getAgenteMundo());
						secuestrar.setContent(princesaSecuestrada.getLocalName());
						myAgent.send(secuestrar);
						
						Gui.setHistoria("El dragón "+getLocalName()+" emprende el vuelo desde "+getLocalizacion()+" en busca de la princesa "+princesaSecuestrada.getLocalName());
						planificar(null);
						
						/*
						 * El motivo de volver a llamar a localizar personaje  aqui
						 * es para que asigne la ultima localizacion del dragon como guarida 
						 * */
						localizarPersonaje();
						addBehaviour(new FalloSecuestro());
						addBehaviour(new FinPlanificacion());
						
						ok = true;
						
					} else {
						Thread.sleep(1000);
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
			
				addBehaviour(new HayQueJoderseConLaPrincesa());
				
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
		


	private class HayQueJoderseConLaPrincesa extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("mujerIndependiente"),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage receive = myAgent.receive(mt);
			
			if ( receive != null ) {
				ACLMessage reply = receive.createReply();
				
				if(Integer.parseInt(receive.getContent()) > getVida()){
					reply.setPerformative(ACLMessage.CONFIRM);
				}				
				myAgent.send(reply);
						
			} else
				block();
			
		}
		
	}
}
