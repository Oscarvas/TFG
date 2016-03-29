package personajes;

import java.util.Random;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ontologia.Mitologia;

@SuppressWarnings("serial")
public class Villano extends Personaje {
	public AID caballero;

	protected void setup(){
		Object[] args = getArguments(); 
		if (args != null && args.length > 0) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
		}
		localizarPersonaje();
		Gui.setHistoria("Hay quienes llaman villano a "+getLocalName()+", él solo piensa que todo estaría mejor cuando tome el control del reino.");
		addBehaviour(new DiadelaMaldad());
	}
	
	private class DiadelaMaldad extends Behaviour{
		
		boolean ok;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Matadragones"); //en busca de algun caballero, son mi motivacion
			template.addServices(sd);
			
			try {

				DFAgentDescription[] result = DFService.search(myAgent,template);
				AID[] CaballerosDisponibles = new AID[result.length];

				for (int i = 0; i < result.length; i++)
					CaballerosDisponibles[i] = result[i].getName();				
			
				if (CaballerosDisponibles.length != 0) {
					caballero = CaballerosDisponibles[new Random().nextInt(CaballerosDisponibles.length)];
					planificar();
					
					ok = true;
					
				} else {
					Thread.sleep(3000);
					reset();
				}
			} catch (Exception fe) {
				fe.printStackTrace();
			}
			
			if( !done() )
				reset();
			
		}

		@Override
		public boolean done() {
			return ok;
		}
		
	}
}
