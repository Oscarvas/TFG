package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import personajes.Personaje;

public class Agonia {

	private Personaje personaje;
	private String secundario;
	
	
	public Agonia(Personaje personaje, String secundario) {
		
		this.personaje = personaje;
		this.secundario = secundario;
		
	}
	
	
	public void execute() {
		
		ACLMessage agonia = new ACLMessage(ACLMessage.INFORM);
		agonia.addReceiver(new AID ((String) secundario, AID.ISLOCALNAME));
		agonia.setConversationId("Agoniza");
		agonia.setReplyWith("agoniza" + System.currentTimeMillis());
		/*
		 * Enviamos como mensaje la cantidad de vida a perder por el personaje afectado
		 * */
		agonia.setContent(Integer.toString(15));
		
		Gui.setHistoria(personaje.getLocalName()+ ": " + secundario + ", ahora sentir�s la agon�a de una muerte lenta");
		
		personaje.send(agonia);
		
	}
}