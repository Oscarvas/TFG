package personajes.principal;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontologia.Mitologia;
import ontologia.Vocabulario;
import personajes.Personaje;

@SuppressWarnings("serial")
public class Rey extends Personaje {
	public AID[] CaballerosDisponibles;
	public AID princesaSecuestrada;
	public String dragon;
	public int repliesCnt;
	public AID mejorCaballero;
	public int menosDineroPedido;
	public MessageTemplate mt;
	public int numeroHijas;
	
	protected void setup(){		
		
		numeroHijas=Vocabulario.NUM_HIJAS;
		Object[] args = getArguments(); 
		if (args != null && args.length > 0 && numeroHijas > 0 ) {
			iniciarPrincipal(Mitologia.valueOf((String) args[0]), Integer.parseInt((String) args[1]), 
					Integer.parseInt((String) args[2]), Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), true);
			localizarPersonaje();
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Padre");
			sd.setName(getLocalName()+"-Padre");
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
			m.registerState(new Salvada(), "Princesa Salvada");

			m.registerDefaultTransition("Atento", "Rescate");
			m.registerDefaultTransition("Rescate", "Pedir Ayuda");
			m.registerDefaultTransition("Pedir Ayuda", "Ofertas");
			m.registerTransition("Ofertas", "Rescate", 1);
			m.registerTransition("Ofertas", "Aceptar", 2);
			m.registerTransition("Aceptar", "Princesa Salvada", 1);
			m.registerTransition("Aceptar", "Rescate", 2);
			m.registerDefaultTransition("Princesa Salvada", "Atento");

			addBehaviour(m);
		}
		else{
			Gui.setHistoria(getLocalName()+": Como he sido rancio toda la vida, no tengo hijas por las cuales preocuparme");
		}
		
	}
	protected void takeDown() {
		
		try{
			DFService.deregister(this);
		}catch (FIPAException fe){
			fe.printStackTrace();
		}
		
		Gui.setHistoria(getLocalName()+ ": Parece que mi trabajo ha terminado por hoy");
	}
	
	private class Atento extends OneShotBehaviour {

		private MessageTemplate mt;

		public void action() {

			mt = MessageTemplate.and(
					MessageTemplate.MatchConversationId("Ayuda"),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage receive = myAgent.blockingReceive(mt);
			princesaSecuestrada = receive.getSender();
			dragon = receive.getContent();
		}

	}

	private class Rescate extends Behaviour {

		public void action() {

			Gui.setHistoria("- Intentando pedir rescate para la princesa "+ princesaSecuestrada.getLocalName() + ".");
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Matadragones");
			template.addServices(sd);

			try {

				DFAgentDescription[] result = DFService.search(myAgent,
						template);
				Gui.setHistoria("Encontrados los siguientes caballeros:");
				CaballerosDisponibles = new AID[result.length];

				if (done()) {
					for (int i = 0; i < result.length; i++) {
						CaballerosDisponibles[i] = result[i].getName();
						Gui.setHistoria("\t" + CaballerosDisponibles[i]
								.getLocalName());
					}
					
				} else {
					Gui.setHistoria("\n - Esperando 10 segundos... \n");
					Thread.sleep(10000);
				}
				
			} catch (Exception fe) {
				fe.printStackTrace();
			}

		}

		@Override
		public boolean done() {

			return CaballerosDisponibles.length != 0;

		}
	}

	private class Ayuda extends OneShotBehaviour {

		public void action() {

			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			cfp.setContent(princesaSecuestrada.getLocalName());
			cfp.setConversationId("SolicitarServicio");

			for (int i = 0; i < CaballerosDisponibles.length; i++) {
				cfp.addReceiver(CaballerosDisponibles[i]);
			}

			repliesCnt = CaballerosDisponibles.length;
			mejorCaballero = null;
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

			if ((mejorCaballero == null || dineroPedido < menosDineroPedido)
					&& (dineroPedido <= getTesoro())) {
				
				menosDineroPedido = dineroPedido;
				mejorCaballero = msg.getSender();
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

			if (mejorCaballero == null)
				return 1;
			else
				return 2;
		}
	}

	private class AceptarOferta extends OneShotBehaviour {

		public void action() {

			ACLMessage aceptar = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			aceptar.addReceiver(mejorCaballero);
			aceptar.setContent(princesaSecuestrada.getLocalName() + " "
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
				Gui.setHistoria("- El Rey recibe la noticia de la muerte del caballero, así que busca a otro. \n");

				return 2;

			}

		}
	}

	private class Salvada extends OneShotBehaviour {

		public void action() {
			
			Gui.setHistoria("- La Princesa "
					+ princesaSecuestrada.getLocalName() + " fue liberada.");
			Gui.setHistoria("- El Rey entrega " + menosDineroPedido
					+ " monedas al caballero " + mejorCaballero.getLocalName() + ". \n");
			setTesoro(getTesoro()-menosDineroPedido);

			ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			inform.setConversationId("Rescatada");
			inform.setReplyWith("inform" + System.currentTimeMillis());
			inform.setContent(mejorCaballero.getLocalName());
			inform.addReceiver(princesaSecuestrada);

			myAgent.send(inform);
			numeroHijas--;

			if (numeroHijas == 0) {
				Gui.setHistoria("- Todas las hijas del Rey están a salvo, puede morir tranquilo. \n");
				doDelete();
			}

			else if (getTesoro() == 0) {
				Gui.setHistoria("- El Rey ha perdido todo su dinero con el rescate, asi que deja de ser Rey. \n");
				doDelete();
			}
		}
	}

}
