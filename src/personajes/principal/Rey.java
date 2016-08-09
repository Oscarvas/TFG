package personajes.principal;

import java.util.Random;

import acciones.Tortura;
import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontologia.Vocabulario;

@SuppressWarnings("serial")
public class Rey extends Protagonista {
	public AID[] AspirantesDisponibles;
	public AID victimaSecuestrada;
	public String dragon;
	public int repliesCnt;
	public AID mejorAspirante;
	public int menosDineroPedido;
	public MessageTemplate mt;
	public int numeroHijas;
	private ThreadedBehaviourFactory tbf = new
			ThreadedBehaviourFactory();
	
	protected void setup(){		
		
		numeroHijas= Vocabulario.NUM_HIJAS();
		Object[] args = getArguments(); 
		if (args != null && args.length > 0 && numeroHijas > 0 ) {
			iniciarPrincipal((String) args[0], (String) args[1], 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), Integer.parseInt((String) args[6]), true);
			localizarPersonaje();
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Rey");
			sd.setName(getLocalName()+"-Rey");
			dfd.addServices(sd);
			
			try {
				DFService.register(this, dfd);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}			
			
			
			Gui.setHistoria("El rey "+getLocalName()+" apenas despierta, y la que se ha liado en su reino es digna de una buena historia.");
						
			FSMBehaviour m = new FSMBehaviour(this);
			m.registerFirstState(new Atento(), "Atento");
			m.registerState(new Rescate(), "Rescate");
			m.registerState(new Ayuda(), "Pedir Ayuda");
			m.registerState(new RecibirOfertas(), "Ofertas");
			m.registerState(new AceptarOferta(), "Aceptar");
			m.registerState(new Salvada(), "Victima Salvada");

			m.registerDefaultTransition("Atento", "Rescate");
			m.registerDefaultTransition("Rescate", "Pedir Ayuda");
			m.registerDefaultTransition("Pedir Ayuda", "Ofertas");
			m.registerTransition("Ofertas", "Rescate", 1);
			m.registerTransition("Ofertas", "Aceptar", 2);
			m.registerTransition("Aceptar", "Victima Salvada", 1);
			m.registerTransition("Aceptar", "Rescate", 2);
			m.registerDefaultTransition("Victima Salvada", "Atento");

			addBehaviour(tbf.wrap(m));
			addBehaviour(tbf.wrap(new Tortura(this)));
		}
		else{
			Gui.setHistoria(getLocalName()+": Como he sido rancio toda la vida, no tengo hijas por las cuales preocuparme");
			doDelete();
		}
		
		
	}
	protected void takeDown() {
		
		try{
			DFService.deregister(this);
		}catch (FIPAException fe){
			fe.printStackTrace();
		}
			
		if (estaMuerto()){
			Gui.setHistoria(getLocalName()+": Solo veo... oscuridad... ante m�...\n");
		}else{
			Gui.setHistoria(getLocalName()+": He tenido otra visi�n del Rey Ex�nime. �Ha restaurado mis poderes! ahora s� lo que tengo que hacer. Es hora de poner fin al juego... de una vez\n");
		}
				
	}
	
	private class Atento extends OneShotBehaviour {

		private MessageTemplate mt;

		public void action() {

			mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("Ayuda"),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage receive = myAgent.blockingReceive(mt);
			victimaSecuestrada = receive.getSender();
			dragon = receive.getContent();	
			
			/*
			 * Una vez hemos recibido el llamado de auxilio de una victima, activamos a los monstruos malignos
			 * que deberan empezar su planificacion
			 * 
			 * Se seleccionara a un fantasma aleatorio
			 * */
			try {
				
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Maligno");
				template.addServices(sd);
				
				DFAgentDescription[] result;
				result = DFService.search(myAgent, template);
				AID[] malignos = new AID[result.length];
				for (int i = 0; i < result.length; i++){
					malignos[i] = result[i].getName();
				}
				System.err.println("ahora vamos en la buscqueda de asesinos---------");
				
				AID fantasma;
				if (malignos.length != 0){
					fantasma = malignos[new Random().nextInt(malignos.length)];
					
					ACLMessage herejia = new ACLMessage(ACLMessage.INFORM);
					herejia.setConversationId("Derrocar");
					herejia.addReceiver(fantasma);
					herejia.setContent(getLocalName());
					myAgent.send(herejia);
					System.err.println("encontrado asesino "+fantasma.getLocalName());
				}
				
				
			} catch (FIPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private class Rescate extends Behaviour {

		public void action() {
			
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Matadragones");
			template.addServices(sd);

			try {
				DFAgentDescription[] result = DFService.search(myAgent,
						template);
				AspirantesDisponibles = new AID[result.length];
				
				if (done()) {					
					Gui.setHistoria("- Intentando pedir rescate para la victima "+ victimaSecuestrada.getLocalName() + ".");
					Gui.setHistoria("Encontrados los siguientes aspirantes:");
										
					for (int i = 0; i < result.length; i++) {
						AspirantesDisponibles[i] = result[i].getName();
						Gui.setHistoria("\t" + AspirantesDisponibles[i]
								.getLocalName());
					}
					
				} else {
					//Gui.setHistoria("\n - Esperando 10 segundos... \n");
					Thread.sleep(500);
				}
				
			} catch (Exception fe) {
				fe.printStackTrace();
			}

		}

		@Override
		public boolean done() {

			return AspirantesDisponibles.length != 0;

		}
	}

	private class Ayuda extends OneShotBehaviour {

		public void action() {

			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			cfp.setContent(victimaSecuestrada.getLocalName());
			cfp.setConversationId("SolicitarServicio");

			for (int i = 0; i < AspirantesDisponibles.length; i++) {
				cfp.addReceiver(AspirantesDisponibles[i]);
			}

			repliesCnt = AspirantesDisponibles.length;
			mejorAspirante = null;
			menosDineroPedido = Integer.MAX_VALUE;

			cfp.setReplyWith("cfp" + System.currentTimeMillis());
			myAgent.send(cfp);
			mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("SolicitarServicio"),
					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
		}
	}

	private class RecibirOfertas extends Behaviour {

		public void action() {

			ACLMessage msg = myAgent.blockingReceive(mt);

			int dineroPedido = Integer.parseInt(msg.getContent());

			if ((mejorAspirante == null || dineroPedido < menosDineroPedido)
					&& (dineroPedido <= getTesoro())) {
				
				menosDineroPedido = dineroPedido;
				mejorAspirante = msg.getSender();
			}

			repliesCnt--;

			if (!done())
				reset();
		}

		@Override
		public boolean done() {
			return repliesCnt == 0;
		}

		public int onEnd() {

			if (mejorAspirante == null)
				return 1;
			else
				return 2;
		}
	}

	private class AceptarOferta extends OneShotBehaviour {

		public void action() {

			ACLMessage aceptar = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			aceptar.addReceiver(mejorAspirante);
			aceptar.setContent(victimaSecuestrada.getLocalName() + " "
					+ dragon);
			aceptar.setConversationId("TratoHecho");
			myAgent.send(aceptar);
			
		}

		public int onEnd() {

			mt = MessageTemplate.MatchConversationId("Rescate");
			ACLMessage reply = myAgent.blockingReceive(mt);

			if (reply.getPerformative() == ACLMessage.INFORM)
				return 1;

			else {
				Gui.setHistoria("- El Rey recibe la noticia de la muerte del aspirante, as� que busca a otro. \n");

				return 2;

			}

		}
	}

	private class Salvada extends OneShotBehaviour {

		public void action() {
			
			Gui.setHistoria("- La Victima "
					+ victimaSecuestrada.getLocalName() + " fue liberada.");
			Gui.setHistoria("- El Rey entrega " + menosDineroPedido
					+ " monedas al aspirante " + mejorAspirante.getLocalName() + ". \n");
			setTesoro(getTesoro()-menosDineroPedido);

			ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			inform.setConversationId("Rescatada");
			inform.setReplyWith("inform" + System.currentTimeMillis());
			inform.setContent(mejorAspirante.getLocalName());
			inform.addReceiver(victimaSecuestrada);

			myAgent.send(inform);
			numeroHijas--;

			if (numeroHijas == 0) {
				Gui.setHistoria("- Todas las hijas del Rey est�n a salvo, puede morir tranquilo. \n");
				doDelete();
			}

			else if (getTesoro() == 0) {
				Gui.setHistoria("- El Rey ha perdido todo su dinero con el rescate, asi que deja de ser Rey. \n");
				doDelete();
			}
		}
	}

}