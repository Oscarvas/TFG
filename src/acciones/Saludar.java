package acciones;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javaff.JavaFF;

@SuppressWarnings("serial")
public class Saludar extends OneShotBehaviour {
	
	public Saludar(Agent a) { 
        super(a);  
    }
	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("\nMy name local is "+myAgent.getLocalName()+"-------------------------------------------------------------------");
		
		String[] args = { "domain.pddl", myAgent.getLocalName() + ".pddl" };

		String ff = JavaFF.crearPlan(args);
		String[] cadena = ff.split("\n");

		for (String sigAccion : cadena) {
//			String[] accionActual = sigAccion.split(" ");
//			String accion = accionActual[0];	
			
			System.out.println(sigAccion);
			
		}
		
		myAgent.doDelete();
	}

}