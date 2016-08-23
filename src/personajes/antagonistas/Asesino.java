package personajes.antagonistas;

import gui.Gui;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Asesino extends Antagonista {
	
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		Gui.setHistoria(getLocalName()+ " " + getSexo() +" " + getEspecie() +" ha decidido que es la hora de la venganza en el castillo "+getLocalizacion()+".");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Asesino");
		sd.setName(getLocalName()+"-Asesino");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new Maldad());
	}
	
	protected void takeDown (){		
		
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		if (estaMuerto())
			Gui.setHistoria(getLocalName()+": Si he de morir,os llevare a todos conmigo,MI DESTINO ES MIO!..\n");
		else
			Gui.setHistoria(getLocalName()+" El Rey Exánime debe caer...\n");
	}
	
	private class Maldad extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Derrocar"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				
				try {
					planificar(receive.getContent());
					addBehaviour(new FinPlanificacion());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				block();
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
