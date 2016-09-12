package personajes.pnjs;

import java.util.Random;

import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import mundo.Mundo;
import personajes.Personaje;

@SuppressWarnings("serial")
public class PNJ extends Personaje {
	private String oficio;
	
	protected void setup(){
		Object[] args = getArguments(); 
		
		//cargamos sus propiedades
		setSexo((String)args[0]); 
		setOficio((String)args[1]); 
		setLocalizacion((String)args[2]);
		setFrases(Mundo.diccionario.getFrasesPersonaje(this.oficio));
		cargarMundo();
		
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

		//localizarPersonaje();
		// Se formaran frases del estilo "El [oficio] [nombre] se levanta en [localizacion] dispuesto a trabajar
		// "La bibliotecaria Luisa se levanta en la biblioteca con ganas de trabajar."
		Gui.setHistoria(getSexo() + " " + getOficio() + " " + getLocalName()+ hablar("Inicio") +getLocalizacion() +".");
		
		addBehaviour(new ModificarEstadisticas());
		
	}

	private class ModificarEstadisticas extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("TrucoTrato"),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				ACLMessage reply = msg.createReply();
				int destino = new Random().nextInt(2);
				
				switch (destino) {
				case 0:
					String bonificacion = buff();
					//Gui.setHistoria(getLocalName()+": Recibe mi bendicion "+msg.getSender().getLocalName()+". "+bonificacion);
					Gui.setHistoria(getLocalName() + hablar("Bendicion") + msg.getSender().getLocalName()+".");
					reply.setContent(bonificacion);
					break;
					
				case 1:
					String deuda = debuff();
					//Gui.setHistoria(getLocalName()+": Recibe mi maldicion "+msg.getSender().getLocalName()+"!!! "+deuda);
					Gui.setHistoria(getLocalName() + hablar("Maldicion") + msg.getSender().getLocalName()+".");
					reply.setContent(deuda);
					break;
				default:
					break;
				}
				
				myAgent.send(reply);

			} else
				block();
			
		}
		
	}
	
	private int num(){
		return new Random().nextInt(11);
	}
	
	private String buff(){
		String f,d,i,c;
		f= String.valueOf(num());
		d= String.valueOf(num());
		i= String.valueOf(num());
		c= String.valueOf(num());
		return f+" "+d+" "+i+" "+c;
	}
	
	private String debuff(){
		String f,d,i,c;
		f= String.valueOf(num()*-1);
		d= String.valueOf(num()*-1);
		i= String.valueOf(num()*-1);
		c= String.valueOf(num()*-1);
		return f+" "+d+" "+i+" "+c;		
	}
	
	public String getOficio(){
		return this.oficio;
	}
	
	public void setOficio(String oficio){
		this.oficio = oficio;
	}
	
}
