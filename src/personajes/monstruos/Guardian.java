package personajes.monstruos;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
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
		Gui.setHistoria(getLocalName()+": Es un dia fatidico para cualquier "+getEspecie());
		
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	private class Acecho extends Behaviour{

		boolean ok;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(7000);
				planificar(null);
				ok = true;
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
	
}
