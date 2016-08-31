package acciones;

import gui.Gui;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import personajes.Personaje;
import personajes.principal.Protagonista;

public class Mover {

	private Personaje personaje;
	private String locOrigen;
	private String locDest;
	private AID agenteMundo;

	public Mover(Personaje personaje, String locOrigen, String locDest) {

		this.personaje = personaje;
		this.locOrigen = locOrigen;
		this.locDest = locDest;
		this.agenteMundo = personaje.getAgenteMundo();

	}

	public void execute() {

		if (locOrigen.equalsIgnoreCase(personaje.getLocalizacion())) {
			ACLMessage mover = new ACLMessage(ACLMessage.REQUEST);
			mover.addReceiver(agenteMundo);
			mover.setConversationId("Mover");
			mover.setReplyWith("mover" + System.currentTimeMillis());
			mover.setContent(personaje.getClase() + " "
					+ locDest + " " + locOrigen);
			personaje.send(mover);

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("Mover"),
					MessageTemplate.MatchInReplyTo(mover.getReplyWith()));

			ACLMessage msg = personaje.blockingReceive(mt);

			if (msg.getPerformative() == ACLMessage.CONFIRM) {

				personaje.setLocalizacion(msg.getContent());
				evento();
				objetoEncontrado();

				Gui.setHistoria(personaje.getLocalName() + personaje.hablar("Mover") + personaje.getLocalizacion()+"!");
				

			} else {
				System.err.println(" No se ha podido cambiar de localizacion. \n");
			}

		} else {
						
			System.err.println(" El personaje "
					+ personaje.getLocalName() + " se quería ir de "
					+ locOrigen + ", pero éste estaba en "
					+ personaje.getLocalizacion() + ". \n");
		}
	}

	private void evento(){
		MessageTemplate mt2 = MessageTemplate.and(
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
				MessageTemplate.MatchConversationId("Hacienda"));
		ACLMessage receive = personaje.receive(mt2);
	
		if (receive != null && receive.getContent()!=null) {
			ACLMessage reply = receive.createReply();
			int balance = personaje.getTesoro()-Integer.parseInt(receive.getContent());
			//aqui condicion de si el caballero puede pagar lo pedido
			if ( balance >= 0){
				personaje.setTesoro(balance);
				Gui.setHistoria(personaje.getLocalName()+": ¡Uff!, menos mal que tenía "+receive.getContent()+" monedas en el bolsillo para pagar");
				reply.setContent(receive.getContent());
			}
			else
				Gui.setHistoria(personaje.getLocalName()+": ¿Puedo darle a necesidad?");
			
			personaje.send(reply);
			MessageTemplate plnt = MessageTemplate
					.MatchInReplyTo(reply.getReplyWith());
			ACLMessage ajusteCuentas = personaje.blockingReceive(plnt);
			
			if (ajusteCuentas.getContent() != null){
				personaje.setVida(0);
			}
	
		} 
		
	}
	
	private void objetoEncontrado(){
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("Consumir"),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msg = personaje.receive(mt);

		if (msg != null && msg.getContent()!=null) {
			
			Gui.setHistoria(((Protagonista)personaje).usarObjeto(msg.getContent()));;

		}
	}
	
}
