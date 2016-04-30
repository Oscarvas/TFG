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
public class Druida extends Protagonista {
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
		sd.setType("Cambiaformas");
		sd.setName(getLocalName()+"-Cambiaformas");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		precio = Vocabulario.SALARIO * getCodicia();
		
		localizarPersonaje();
		Gui.setHistoria(getLocalName()+" el druida pensó que era buena idea transformarse conejo en estas fechas.");
		addBehaviour(new OfrecerServicios(precio));
	}
}
