package personajes.protagonistas;

import java.util.Random;

import acciones.DondeEsta;
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
import ontologia.Vocabulario;

@SuppressWarnings("serial")
public class Victima extends Protagonista {
	private AID padre;
	public AID secuestrador;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal((String) args[0], (String) args[1], 
					(String) args[2], Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), Integer.parseInt((String) args[6]),
					Integer.parseInt((String) args[7]), false);
			cargaPrincipal(getFuerza());//la victima da como atributo principal la fuerza
		}
		
		addBehaviour(new Huerfana());
	}
	
	protected void takeDown() {
		if (estaMuerto()){
			Gui.setHistoria(getLocalName()+ hablar("Muerte")+ "\n");
		}else{
			Gui.setHistoria(getLocalName()+ hablar("Fin")+ "\n");
		}
	}
	private class Huerfana extends Behaviour{

		/**
		 * Asigna un padre aleatorio a la victima
		 */
		boolean ok;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Allegado");
			template.addServices(sd);
			
			try{
				DFAgentDescription[] result = DFService.search(myAgent, template);
				AID[] allegadoes = new AID[result.length];
				for (int i = 0; i < result.length; i++){
					allegadoes[i] = result[i].getName();
				}
				
				if (allegadoes.length != 0) {
					padre = allegadoes[new Random().nextInt(allegadoes.length)];
					//Hacemos que la victima se posicione en el mismo lugar que su padre
					setLocalizacion(new DondeEsta(myAgent, padre.getLocalName()).execute());
					
					localizarPersonaje();
										
					Gui.setHistoria(getSexo() + " " + getRol() + " " +  myAgent.getLocalName() + hablar("Inicio") + getLocalizacion() +".");
					
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
					MessageTemplate.MatchConversationId("Mover-Victima"));
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
				
				secuestrador = receive.getSender();
				send(receive.createReply());	
			
				Gui.setHistoria(getSexo() + " " + getRol() + " "  + myAgent.getLocalName() + hablar("Secuestro") + "\n");
				
				ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);
				inform.setConversationId("Ayuda");
				inform.setReplyWith("request" + System.currentTimeMillis());
				inform.addReceiver(padre);
				inform.setContent(secuestrador.getLocalName());
				myAgent.send(inform);
				
				Gui.setHistoria(getLocalName()+ hablar("Secuestrador") +secuestrador.getLocalName());
				
				ACLMessage escapar = new ACLMessage(ACLMessage.REQUEST);
				escapar.addReceiver(secuestrador);
				escapar.setConversationId("mujerIndependiente");
				
				
				myAgent.addBehaviour(new TickerBehaviour(myAgent, 5000){
						
						@SuppressWarnings("unused")
						@Override
						protected void onTick() {
							escapar.setReplyWith("mujerIndependiente" + System.currentTimeMillis());
							escapar.setContent(String.valueOf(Vocabulario.VIDA_MONSTRUO()));							
							send(escapar);
												
							MessageTemplate imp = MessageTemplate.MatchInReplyTo(escapar.getReplyWith());
							ACLMessage reply = myAgent.blockingReceive(imp);
							
							if (reply.getPerformative() == ACLMessage.FAILURE){
								stop();
							}
							else if (reply.getPerformative() == ACLMessage.CONFIRM) {

								Gui.setHistoria(getLocalName()+ hablar("Liberacion") + secuestrador.getLocalName());
								stop();
								try {
									
									ACLMessage liberarVictima = new ACLMessage(ACLMessage.REQUEST);
									liberarVictima.addReceiver(getAgenteMundo());
									liberarVictima.setConversationId("Liberar");
									liberarVictima.setReplyWith("liberar" + System.currentTimeMillis());
									liberarVictima.setContent(getLocalName()+ " " + secuestrador.getLocalName());
									myAgent.send(liberarVictima);

									MessageTemplate mt = MessageTemplate.MatchInReplyTo(liberarVictima.getReplyWith());
									ACLMessage reply2 = myAgent.blockingReceive(mt);
									
									
									planificar(null);
									addBehaviour(new FinPlanificacion());
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

	
	private class FinPlanificacion extends Behaviour {
		ACLMessage receive;

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fin-Plan"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));

			receive = receive(mt);

			if (receive != null) {

				doDelete();

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}
	
}
