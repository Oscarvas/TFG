package pnjs;

import gui.Gui;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Chaman extends Personaje {
	protected void setup(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chamanismo");
		sd.setName(getLocalName()+"-Chamanismo");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		Gui.setHistoria("El chamán "+getLocalName()+" entra en sintonía con los elementos");
	}
}
