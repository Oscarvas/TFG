package acciones;

import gui.Gui;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Tortura extends Behaviour {
	private Personaje personaje;
	private ThreadedBehaviourFactory tbf = new
			ThreadedBehaviourFactory();
	public Tortura(Agent myAgent){
		this.personaje = (Personaje) myAgent;
	}
	
	ACLMessage tortura;
	
	boolean ok;
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Agoniza"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		tortura = myAgent.receive(mt);
	
		if (tortura != null){
			Gui.setHistoria(myAgent.getLocalName()+ ": Ah!! Me han herido !!!");
			
			int vida = Integer.parseInt(tortura.getContent());
			myAgent.addBehaviour(tbf.wrap(new TickerBehaviour(myAgent, 600){ //cada 30 segundos se desangrara

				protected void onTick() {
					personaje.añadirVida(-vida);
					System.err.println("-----"+myAgent.getLocalName()+" se desangra, perdiendo "+vida+" de vida.");
					
					
					if ( personaje.estaMuerto() ){
						stop();
						myAgent.doDelete();
					}
				}
			}));
			//Para finalizar el comportamiento TORTURA
			ok = true;
		}
		else
			block();
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return ok;
	}
}
