package personajes.monstruos;

import java.util.Random;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Secuestrador extends Monstruo {
	public AID victimaSecuestrada;

	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		Gui.setHistoria("Desde "+getLocalizacion()+ ", el imponente rugido de "+getSexo() + " " + getEspecie() + " " +getLocalName()+" se escucha por todo el reino.");
		addBehaviour(new Secuestro());
	}
	protected void takeDown() {
		
		if ( estaMuerto() )
			Gui.setHistoria(getLocalName()+": Es la hora del Crepusculo\n");
		
		else
			Gui.setHistoria(getLocalName()+": Todo ardera bajo la sombra de mis alas\n");
		
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
					AID[] VictimasSecuestrables = new AID[result.length];
					for (int i = 0; i < result.length; i++){
						VictimasSecuestrables[i] = result[i].getName();
					}
					
					if (VictimasSecuestrables.length != 0) {
						victimaSecuestrada = VictimasSecuestrables[new Random().nextInt(VictimasSecuestrables.length)];
						
						ACLMessage secuestrar = new ACLMessage(ACLMessage.INFORM);
						secuestrar.setConversationId("ObjetivoSecuestro");
						secuestrar.addReceiver(getAgenteMundo());
						secuestrar.setContent(victimaSecuestrada.getLocalName());
						myAgent.send(secuestrar);
						
						Gui.setHistoria("El dragón "+getLocalName()+" emprende el vuelo desde "+getLocalizacion()+" en busca de la victima "+victimaSecuestrada.getLocalName());
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
				secuestrar.addReceiver(victimaSecuestrada);
		
				myAgent.send(secuestrar);
				
				MessageTemplate mt1 = MessageTemplate.MatchConversationId("Te secuestro");
				myAgent.blockingReceive(mt1);
				
				//Aviso al guardian
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Guardian");
				template.addServices(sd);
				
				DFAgentDescription[] result;
				try {
					result = DFService.search(myAgent, template);
					AID[] guardianes = new AID[result.length];
					for (int i = 0; i < result.length; i++){
						guardianes[i] = result[i].getName();
					}
					
					if (guardianes.length != 0){						
						ACLMessage despiertaGuardian = new ACLMessage(ACLMessage.INFORM);
						despiertaGuardian.setConversationId("DespiertaGuardian");						
						
						for (AID guardian : guardianes) {
							
							despiertaGuardian.addReceiver(guardian);
							despiertaGuardian.setContent(guardian.getLocalName());
							myAgent.send(despiertaGuardian);
							
						}
					}
					
					
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
				addBehaviour(new VigilaTuEspalda());
				
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
		


	private class VigilaTuEspalda extends CyclicBehaviour{

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
