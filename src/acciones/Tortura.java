package acciones;

import gui.Gui;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.logging.ontology.GetAllLoggers;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Tortura extends CyclicBehaviour {
	private Personaje personaje;
	private ThreadedBehaviourFactory tbf = new
			ThreadedBehaviourFactory();
	public Tortura(Agent myAgent){
		this.personaje = (Personaje) myAgent;
	}
	
	ACLMessage tortura;
	
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
		}
		else
			block();
	}
}
