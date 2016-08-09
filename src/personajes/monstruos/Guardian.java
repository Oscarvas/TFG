package personajes.monstruos;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import gui.Gui;

@SuppressWarnings("serial")
public class Guardian extends Monstruo {
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		Gui.setHistoria(getLocalizacion()+" se ha sumergido en la penumbra tras el paseo de "+getSexo() +" " + getEspecie()+ " "+getLocalName()+".");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Guardian");
		sd.setName(getLocalName()+"-Guardian");
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
			Gui.setHistoria(getLocalName()+": La amabilidad se traduce en debilidad y esa debilidad retrasará el disparo por un instante... ¿y sabes? Este Mundo no es para nada amable con las personas débiles. \n");
		else
			Gui.setHistoria(getLocalName()+": Devoro las cinco tierras y seco los tres océanos, mientras que el cielo es lo único imposible de alcanzar con este cuerpo sin alas, brazos ni piernas. Soy la Serpiente del Mundo. Soy Jormungander.  \n");
	}

	private class Acecho extends Behaviour{

		boolean ok;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			try {
				
				MessageTemplate mt = MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchConversationId("DespiertaGuardian"));
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
