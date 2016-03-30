package mundo;

import jade.core.Agent;
import javaff.JavaFF;

public class Planificador extends Agent {
	protected void setup(){
		// TODO Auto-generated method stub
		//System.out.println("Soy "+hey+" usurpando a "+myAgent.getLocalName());
	
		String[] args = { "domainOld.pddl","Smaug.pddl" };

		String ff = JavaFF.crearPlan(args);
		String[] cadena = ff.split("\n");

		for (String sigAccion : cadena) {
			String[] accionActual = sigAccion.split(" ");
			String accion = accionActual[0];	
			
			System.out.println(sigAccion);
			
		}
		
		doDelete();
	}
	protected void takeDown(){
		System.out.println("Adios once a mothafkn gain");
	}

}
