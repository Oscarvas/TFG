package personajes.principal;

import acciones.*;
import gui.Gui;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ontologia.Mitologia;
import ontologia.Vocabulario;

@SuppressWarnings("serial")
public class Mago extends Protagonista {
	private int precio;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
		}

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Cazamagia");
		sd.setName(getLocalName()+"-Cazamagia");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		precio = Vocabulario.SALARIO * getCodicia();
		
		localizarPersonaje();
		Gui.setHistoria("Aquel al que llaman mago, "+getLocalName()+", realmente sólo tiene muchísimas cartas bajo la túnica.");
		addBehaviour(new OfrecerServicios(precio));
	}
}
