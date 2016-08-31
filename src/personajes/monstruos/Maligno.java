package personajes.monstruos;

import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import mundo.Mundo;

@SuppressWarnings("serial")
public class Maligno extends Monstruo {
	
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		setFrases(Mundo.diccionario.getFrasesPersonaje(getEspecie()));
		Gui.setHistoria(getLocalName()+ " " + getSexo() +" " + getEspecie() + hablar("Inicio") +getLocalizacion()+".");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Maligno");
		sd.setName(getLocalName()+"-Maligno");
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				block();
		}
		
	}
}
