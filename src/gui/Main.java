package gui;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

@SuppressWarnings("serial")
public class Main extends GuiAgent{

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	protected void setup(){
		System.out.println("Hello World. ");
		System.out.println("My name local is "+getLocalName());
		System.out.println("“My GUID is "+getAID().getName());
		
		doDelete(); //finaliza al agente y hace la llamada al metodo takedown
	}
	
	protected void takeDown() {
		System.out.println("Bye...");
	}

}
