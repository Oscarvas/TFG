package personajes.antagonistas;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import gui.Gui;

@SuppressWarnings("serial")
public class Ladron extends Antagonista {
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		Gui.setHistoria("Desde "+getLocalizacion()+ " " + getSexo() + " " + getEspecie() + " " + getLocalName()+ hablar("Inicio"));
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Ladron");
		sd.setName(getLocalName()+"-Ladron");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new Acecho());
		
	}
	
	protected void takeDown (){
		
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		if(estaMuerto())
			Gui.setHistoria(getLocalName()+ hablar("Muerte")+ "\n");
		else
			Gui.setHistoria(getLocalName()+ hablar("Fin")+ "\n");
	}

	private class Acecho extends Behaviour{

		boolean ok;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			try {
				
				MessageTemplate mt = MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchConversationId("DespiertaLadron"));
				ACLMessage receive = myAgent.receive(mt);
				
				if (receive != null) {
					
					Gui.setHistoria(getLocalName()+": Va en busca de algun tesoro desprotegido");
					planificar(null);
					addBehaviour(new FinPlanificacion());
					//Igual que el secuestrador, la ultima localizacion se convierte en guarida
					localizarPersonaje();
					ok = true;
					
				}
				else {
					Thread.sleep(3000);
					reset();
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return ok;
		}
		
	}
	
	private class FinPlanificacion extends Behaviour {
		ACLMessage receive;

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Fin-Plan"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));

			receive = receive(mt);

			if (receive != null) {

				Gui.setHistoria(getLocalName()+": Hora de apreciar el botin conseguido");

			} else
				block();
		}

		@Override
		public boolean done() {
			return receive != null;
		}
	}
	
}
