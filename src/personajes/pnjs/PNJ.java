package personajes.pnjs;

import gui.Gui;
//import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import mundo.Mundo;
import personajes.Personaje;

@SuppressWarnings("serial")
public class PNJ extends Personaje {
	//private String sexo;
	private String oficio;
	//private String localizacion;	
	//private AID agenteMundo;	
	
	protected void setup(){
		
		Object[] args = getArguments(); 		
		//cargamos sus propiedades
		setSexo((String)args[0]); //this.sexo = (String)args[0];
		setoficio((String)args[1]); //this.oficio = (String)args[1];
		setLocalizacion((String)args[2]); //this.localizacion = (String)args[2];		
		
		//aqui debemos coger los args que hacemos al crear el pnj [0]sexo [1]oficio [2]localizacion
		setFrases(Mundo.diccionario.getFrasesPersonaje(getOficio()));
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
		
		//localizarPnj();
		
		// Se formaran frases del estilo "El [oficio] [nombre] se levanta en [localizacion] dispuesto a trabajar
		// "La bibliotecaria Luisa se levanta en la biblioteca con ganas de trabajar."
		Gui.setHistoria(getSexo() + " " + getOficio() + " " + getLocalName()+ getFrase("Inicio") +getLocalizacion() + " con ganas de trabajar.");
		
	}
	
	
	public String getOficio(){
		return this.oficio;
	}
	
	public void setoficio(String oficio){
		this.oficio = oficio;
	}
	
//	//sitúa al pnj en el lugar correcto a la hora de su creación
//	public void localizarPnj() {
//		DFAgentDescription template = new DFAgentDescription();
//		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Mundo");
//		template.addServices(sd);
//
//		try {
//
//			DFAgentDescription[] result = DFService.search(this, template);
//			setAgenteMundo(result[0].getName());
//
//			ACLMessage localizar = new ACLMessage(ACLMessage.REQUEST);
//			localizar.addReceiver(getAgenteMundo());
//			localizar.setConversationId("Mover");
//			localizar.setContent(this.oficio + " " + this.localizacion);
//			localizar.setReplyWith("localizar" + System.currentTimeMillis());
//			send(localizar);
//
//			MessageTemplate mt = MessageTemplate.and(
//					MessageTemplate.MatchConversationId("Mover"),
//					MessageTemplate.MatchInReplyTo(localizar.getReplyWith()));
//			blockingReceive(mt);
//
//		} catch (FIPAException e) {
//			e.printStackTrace();
//		}
//
//	}
}
