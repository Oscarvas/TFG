package personajes.monstruos;

import java.util.Random;

import acciones.Emboscar;
import gui.Gui;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class Emboscador extends Monstruo {
	protected void setup(){
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1], (String) args[2]);
		Gui.setHistoria("Parece que mientras "+getSexo() +" " + getEspecie()+ " "+getLocalName()+" sea guardián de "+getLocalizacion()+", la desgracia caerá sobre cada insensato que pase por ahí.");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Emboscador");
		sd.setName(getLocalName()+"-Emboscador");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new Emboscar(getLocalizacion()));
		addBehaviour(new Acecho());
	}
	
	protected void takeDown (){
		Gui.setHistoria(getLocalName()+": Es un dia fatidico para cualquier "+getEspecie());
		
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	private class Acecho extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
					MessageTemplate.MatchConversationId("Cruzar"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				String hist=getLocalName()+": ¿Es aquello un caballero?... ¡¡¡Día de paga!!!";
				Gui.setHistoria(hist);
				
				ACLMessage impuestos = new ACLMessage(ACLMessage.REQUEST);
				impuestos.setConversationId("Hacienda");
				impuestos.setReplyWith("hacienda" + System.currentTimeMillis());
				impuestos.addReceiver(getAID(receive.getContent()));
				impuestos.setContent(String.valueOf(new Random().nextInt(50)+1));	
				send(impuestos);
				
				MessageTemplate imp = MessageTemplate
						.MatchInReplyTo(impuestos.getReplyWith());
				ACLMessage reply = myAgent.blockingReceive(imp);
				ACLMessage asesinar = reply.createReply();
				
				if (reply.getContent() != null ){
					Gui.setHistoria(getLocalName()+": Un caballero con dinero es un caballero vivo, puedes continuar "+reply.getSender().getLocalName());
				}
				else{
					Gui.setHistoria(getLocalName()+": ¡Tuuuuuuuuuuuu, no llevarte vela !");
					//new Batalla((Personaje) myAgent, reply.getSender().getLocalName()).execute();
					asesinar.setContent("Muere");
				}
				

//				send(receive.createReply());//respuesta al mundo
				send(asesinar);//respuesta al caballero
				

			} else
				block();
			
		}
		
	}
}
