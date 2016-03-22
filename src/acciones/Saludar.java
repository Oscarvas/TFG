package acciones;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javaff.JavaFF;

@SuppressWarnings("serial")
public class Saludar extends OneShotBehaviour {
	private int hey;
	public Saludar(Agent a, int name) { 
        super(a);
        this.hey = name;
    }
	@Override
	public void action() {
		// TODO Auto-generated method stub
		myAgent.waitUntilStarted();
		System.out.println("\nMi nombre "+this.hey+" es "+myAgent.getLocalName()+"-------------------------------------------------------------------");
		
		String[] args = { "domainOld.pddl", myAgent.getLocalName() + ".pddl" };

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