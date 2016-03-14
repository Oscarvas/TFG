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
		
		Object[] args = myAgent.getArguments();
		if (args != null) {
			System.out.println("My arguments are:");
			for (int i = 0; i < args.length; ++i) {
			System.out.println("- "+args[i]);
		}
	}
	}

}