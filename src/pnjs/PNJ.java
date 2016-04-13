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
	private String sexo;
	private String oficio;
	private String localizacion;	
	private AID agenteMundo;	
	
	protected void setup(){
		Object[] args = getArguments(); 
		
		this.sexo = (String)args[0];
		this.oficio = (String)args[1];
		this.localizacion = (String)args[2];
		
		//aqui debemos coger los args que hacemos al crear el pnj [0]sexo [1]oficio [2]localizacion
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(this.oficio);
		sd.setName(getLocalName()+"-"+this.oficio);
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		localizarPnj();
		// Se formaran frases del estilo "El [oficio] [nombre] se levanta en [localizacion] dispuesto a trabajar
		// "La bibliotecaria Luisa se levanta en la biblioteca con ganas de trabajar."
		if (this.sexo.equals("M")) 
			Gui.setHistoria("El " + this.oficio + " " + getLocalName()+ " se levanta en " +this.localizacion + " con ganas de trabajar.");
		else
			Gui.setHistoria("La " + this.oficio + " " + getLocalName()+ " se levanta en " +this.localizacion + " con ganas de trabajar.");
	}
	
	public void setAgenteMundo(AID agenteMundo) {
		this.agenteMundo = agenteMundo;
	}
	
	public AID getAgenteMundo() {
		return agenteMundo;
	}
	
	//sitúa al pnj en el lugar correcto a la hora de su creación
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
			localizar.setContent(this.oficio + " " + this.localizacion);
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
