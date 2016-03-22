package gui;

import acciones.*;
import entorno.Mapa;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

@SuppressWarnings("serial")
public class Main extends GuiAgent{
	
	private Mapa mapa;
	
	public Main(){
		this.mapa = Mapa.getMapa();
	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	protected void setup(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Mundo");
		sd.setName("JADE-Mundo");
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new LocalizarPersonajes());
		addBehaviour(new ToPDDLfile());
		addBehaviour(new MoverPrincesaSecuestrada());
		addBehaviour(new Secuestro());
		addBehaviour(new Liberar());
		addBehaviour(new PersonajeEnCasa());
		addBehaviour(new ConvertirEnHeroe());
		addBehaviour(new MuertePersonaje());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
			System.out.println("La bonita historia ha finalizado...");

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
