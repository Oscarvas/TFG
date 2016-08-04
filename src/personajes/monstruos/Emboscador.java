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
import mundo.Mundo;

@SuppressWarnings("serial")
public class Emboscador extends Monstruo {
	protected void setup(){
		
		Object[] args = getArguments();		
		iniciarMonstruo((String) args[0], (String) args[1]);
		setFrases(Mundo.diccionario.getFrasesPersonaje(getEspecie()));
		Gui.setHistoria("Parece que mientras "+getSexo() +" " + getEspecie()+ " "+getLocalName()+" sea guardián de "+getLocalizacion()+ getFrase("Inicio"));
		
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
		
		
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		if(estaMuerto())
			Gui.setHistoria(getLocalName()+": ¿Quereis quedaros?... ¡Que sea pa' siempre!... Os enterraremo' aquí\n");
		else
			Gui.setHistoria(getLocalName()+": Loh amanih nunca olvidamoh!!!\n");
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
				try {
					planificar(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				block();
			
		}
		
	}
}
