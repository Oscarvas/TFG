package acciones;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

@SuppressWarnings("serial")
public class Saludar extends OneShotBehaviour {
	
	public Saludar(Agent a) { 
        super(a);  
    }
	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("Hello World. ");
		System.out.println("My name local is "+myAgent.getLocalName());
		System.out.println("“My GUID is "+myAgent.getAID().getName());
		
		myAgent.doDelete();
	}

}