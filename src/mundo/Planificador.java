package mundo;

import jade.core.Agent;
import javaff.JavaFF;

@SuppressWarnings("serial")
public class Planificador extends Agent {
	protected void setup(){
		// TODO Auto-generated method stub
	
		String[] args = { "domain.pddl","Alonso.pddl" };

		String ff = JavaFF.crearPlan(args);
		String[] cadena = ff.split("\n");

		for (String sigAccion : cadena)			
			System.out.println(sigAccion);
		
		doDelete();
	}
	protected void takeDown(){
		System.out.println("Adios once a mothafkn gain");
	}

}
