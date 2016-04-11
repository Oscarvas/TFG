package pnjs;

import gui.Gui;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class PNJ extends Agent {
	protected void setup(){
		Object[] args = getArguments(); 
//		if (args != null && args.length > 0) {
//			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
//					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
//					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), false);
//		}
		
		//aqui debemos coger los args que hacemos al crear el pnj [0]oficio [1]localizacion
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
		Gui.setHistoria(getLocalName()+" es "+args[0]+" y esta en "+args[1]);
		
//		localizarPnj();
//		Gui.setHistoria("El caballero "+getLocalName()+" se ha despertado en "+getLocalizacion()+" con su armadura hecha polvo.");
//
//		addBehaviour(new OfrecerServicios(getTesoro()));		
//		addBehaviour(new AceptarOfertaRescate());	
//		
//		
//		
//		
//		DFAgentDescription dfd = new DFAgentDescription();
//		dfd.setName(getAID());
//		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Chamanismo");
//		sd.setName(getLocalName()+"-Chamanismo");
//		dfd.addServices(sd);
//		
//		try {
//			DFService.register(this, dfd);
//		} catch (FIPAException fe) {
//			fe.printStackTrace();
//		}
//		Gui.setHistoria("El chamán "+getLocalName()+" entra en sintonía con los elementos");
	}
	
//	public void localizarPnj() {
//
//		DFAgentDescription template = new DFAgentDescription();
//		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Mundo");
//		template.addServices(sd);
//
//		try {
//
////			DFAgentDescription[] result = DFService.search(this, template);
////			setAgenteMundo(result[0].getName());
////
////			ACLMessage localizar = new ACLMessage(ACLMessage.REQUEST);
////			localizar.addReceiver(getAgenteMundo());
////			localizar.setConversationId("Mover");
////			localizar.setContent(getClass().getName().substring(11) + " "
////					+ localizacion);
////			localizar.setReplyWith("localizar" + System.currentTimeMillis());
////			send(localizar);
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
	
//	public String getLocalizacion() {
//		return this.localizacion;
//	}
//
//	public void setLocalizacion(String localizacion) {
//		this.localizacion = localizacion;
//	}
}
