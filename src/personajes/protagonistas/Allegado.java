package personajes.protagonistas;

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
public class Allegado extends Protagonista {
	public AID[] AspirantesDisponibles;
	public AID victimaSecuestrada;
	public String dragon;
	public int repliesCnt;
	public AID mejorAspirante;
	public int menosDineroPedido;
	public MessageTemplate mt;
	public int numeroHijas;
	private String casoParticular;
	private ThreadedBehaviourFactory tbf = new
			ThreadedBehaviourFactory();
	
	
	protected void setup(){		
		
		numeroHijas= Vocabulario.NUM_HIJAS();
		Object[] args = getArguments(); 
		if (args != null && args.length > 0 && numeroHijas > 0 ) {
			iniciarPrincipal((String) args[0], (String) args[1], 
					(String) args[2], Integer.parseInt((String) args[3]), 
					Integer.parseInt((String) args[4]), Integer.parseInt((String) args[5]), Integer.parseInt((String) args[6]),
					Integer.parseInt((String) args[7]), true);
			localizarPersonaje();
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Allegado");
			sd.setName(getLocalName()+"-Allegado");
			dfd.addServices(sd);
			
			try {
				DFService.register(this, dfd);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}			
			
			
			Gui.setHistoria(getSexo() + " " + getRol() + " " + getLocalName()+ hablar("Inicio") + getLocalizacion() +".");
						
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
			Gui.setHistoria(getLocalName()+ hablar("Solitario"));
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
			Gui.setHistoria(getLocalName()+ hablar("Muerte")+ "\n");
		}else{
			Gui.setHistoria(getLocalName()+ hablar("Fin")+ "\n");
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
			 * Una vez hemos recibido el llamado de auxilio de una victima, activamos a los monstruos asesinos
			 * que deberan empezar su planificacion
			 * 
			 * Se seleccionara a un fantasma aleatorio
			 * */
			try {
				
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Asesino");
				template.addServices(sd);
				
				DFAgentDescription[] result;
				result = DFService.search(myAgent, template);
				AID[] asesinos = new AID[result.length];
				for (int i = 0; i < result.length; i++){
					asesinos[i] = result[i].getName();
				}
				
				AID fantasma;
				if (asesinos.length != 0){
					fantasma = asesinos[new Random().nextInt(asesinos.length)];
					
					ACLMessage herejia = new ACLMessage(ACLMessage.INFORM);
					herejia.setConversationId("Derrocar");
					herejia.addReceiver(fantasma);
					herejia.setContent(getLocalName());
					myAgent.send(herejia);
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
					
					String mensaje = myAgent.getLocalName()+ hablar("Rescate")+
							victimaSecuestrada.getLocalName()+"\n";
					mensaje += "Parece que se puede contar con :\n";										
					for (int i = 0; i < result.length; i++) {
						AspirantesDisponibles[i] = result[i].getName();
						mensaje+="\t" + AspirantesDisponibles[i].getLocalName();
					}
					
					Gui.setHistoria(mensaje);
					
				} else {
					//Gui.setHistoria("\n - Esperando 10 segundos... \n");
					Thread.sleep(10000);
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
			
			//Se da a conocer al mundo que aspirante va a rescatar a la victima
			ACLMessage contratado = new ACLMessage(ACLMessage.INFORM);
			contratado.addReceiver(getAgenteMundo());
			contratado.setContent(victimaSecuestrada.getLocalName()+ " "+ mejorAspirante.getLocalName());
			contratado.setConversationId("Contratado");
			myAgent.send(contratado);
			
		}

		public int onEnd() {

			mt = MessageTemplate.MatchConversationId("Rescate");
			ACLMessage reply = myAgent.blockingReceive(mt);
			
			casoParticular = reply.getContent();

			if (reply.getPerformative() == ACLMessage.INFORM)
				return 1;

			else {
				Gui.setHistoria(hablar("OtroAspirante"));

				return 2;

			}

		}
	}

	private class Salvada extends OneShotBehaviour {

		public void action() {
			
			switch (casoParticular) {
			case "heroe":
				Gui.setHistoria(myAgent.getLocalName()+ hablar("SalvadaHeroe") + victimaSecuestrada.getLocalName() + ".");
				Gui.setHistoria(mejorAspirante.getLocalName() + hablar("SalvadaHeroeRecompensa") + menosDineroPedido + " monedas.");
				setTesoro(getTesoro()-menosDineroPedido);
				
				break;
			case "villano":
				Gui.setHistoria(myAgent.getLocalName() + hablar("SalvadaVillano") + mejorAspirante.getLocalName()+ ".");
				
				break;
			case "escape":
				Gui.setHistoria(myAgent.getLocalName()+ hablar("SalvadaEscape") +victimaSecuestrada.getLocalName()+ ".");
				
				break;
			default:
				Gui.setHistoria(myAgent.getLocalName()+ hablar("SalvadaDefault") +victimaSecuestrada.getLocalName()+ ".");
				break;
			}
			

			ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			inform.setConversationId("Rescatada");
			inform.setReplyWith("inform" + System.currentTimeMillis());
			inform.setContent(mejorAspirante.getLocalName());
			inform.addReceiver(victimaSecuestrada);

			myAgent.send(inform);
			numeroHijas--;

			if (numeroHijas == 0) {
				Gui.setHistoria("- Todas las hijas del Allegado est�n a salvo, puede morir tranquilo. \n");
				doDelete();
			}

			else if (getTesoro() == 0) {
				Gui.setHistoria("- El Allegado ha perdido todo su dinero con el rescate, asi que deja de ser Allegado. \n");
				doDelete();
			}
		}
	}

}