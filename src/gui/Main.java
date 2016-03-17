package gui;

import acciones.Saludar;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

@SuppressWarnings("serial")
public class Main extends GuiAgent{

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	protected void setup(){
		addBehaviour( new Saludar(this) );
		//doDelete(); //finaliza al agente y hace la llamada al metodo takedown, si se activa parece que no hace el comportamiento de saludar
	}
	
	protected void takeDown() {
		System.out.println("El agente "+getLocalName()+" ha finalizado");
	}

}
