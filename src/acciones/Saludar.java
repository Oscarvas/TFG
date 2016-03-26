package acciones;

import gui.Gui;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javaff.JavaFF;

@SuppressWarnings("serial")
public class Saludar extends OneShotBehaviour {
	private String hey;
	public Saludar(Agent a, String name) { 
        super(a); // se le paso como parametro a la clase saludar
        this.hey = name;
    }
	@Override
	public void action() {
		// TODO Auto-generated method stub
		myAgent.waitUntilStarted(); // myAgent sera el agente que "salude"
		//System.out.println("Soy "+hey+" usurpando a "+myAgent.getLocalName());
		Gui.setHistoria("Soy "+hey+" usurpando a "+myAgent.getLocalName()+"\n");
	
//		String[] args = { "domainOld.pddl", myAgent.getLocalName() + ".pddl" };
//
//		String ff = JavaFF.crearPlan(args);
//		String[] cadena = ff.split("\n");
//
//		for (String sigAccion : cadena) {
//			String[] accionActual = sigAccion.split(" ");
//			String accion = accionActual[0];	
//			
//			System.out.println(sigAccion);
//			
//		}
		
		myAgent.doDelete();
	}

}