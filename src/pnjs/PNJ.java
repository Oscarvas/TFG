package pnjs;

import gui.Gui;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class PNJ extends Agent {
	private String localizacion;	
	private AID agenteMundo;	
	
	protected void setup(){
		Object[] args = getArguments(); 
		
		//aqui debemos coger los args que hacemos al crear el pnj [0]sexo [1]oficio [2]localizacion
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType((String) args[0]);
		sd.setName(getLocalName()+"-"+(String) args[0]);
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		localizarPnj();
		// Se formaran frases del estilo "El [oficio] [nombre] se levanta en [localizacion] dispuesto a trabajar
		// "La bibliotecaria Luisa se levanta en la biblioteca con ganas de trabajar."
		if (args[0] == "M") 
			Gui.setHistoria("El " + args[1] + " " + getLocalName()+ " se levanta en " +args[2] + " con ganas de trabajar.");
		else
			Gui.setHistoria("La " + args[1] + " " + getLocalName()+ " se levanta en " +args[2] + " con ganas de trabajar.");
	}
	
	public void setAgenteMundo(AID agenteMundo) {
		this.agenteMundo = agenteMundo;
	}
	
	public AID getAgenteMundo() {
		return agenteMundo;
	}
	
	//no entiendo bien el metodo, en principio me parece que sirve para mover pj's, los pnj's no se van a mover pero lo pongo por si acaso
	public void localizarPnj() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Mundo");
		template.addServices(sd);

		try {

			DFAgentDescription[] result = DFService.search(this, template);
			setAgenteMundo(result[0].getName());

			ACLMessage localizar = new ACLMessage(ACLMessage.REQUEST);
			localizar.addReceiver(getAgenteMundo());
			localizar.setConversationId("Mover");
			localizar.setContent(getClass().getName().substring(11) + " "
					+ localizacion);
			localizar.setReplyWith("localizar" + System.currentTimeMillis());
			send(localizar);

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("Mover"),
					MessageTemplate.MatchInReplyTo(localizar.getReplyWith()));
			blockingReceive(mt);

		} catch (FIPAException e) {
			e.printStackTrace();
		}

	}
	
	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
}
