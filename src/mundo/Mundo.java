package mundo;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gui.Gui;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;
import loaders.LoaderMontruos;
import loaders.LoaderPersonajes;
import loaders.LoaderPnjs;
import ontologia.Vocabulario;

@SuppressWarnings("serial")
public class Mundo extends GuiAgent{
	
	private Mapa mapa;
	private Estado estado;
	private int comando = Vocabulario.STANDBY;
	transient protected Gui myGui;
	private ArrayList<AgentController> agentes;
	
	public Mundo(){
		this.estado = new Estado();
		this.mapa = cargarMapa();//Mapa.getMapa(this.estado);
		agentes = new ArrayList<AgentController>();
		
	}
	public Mapa cargarMapa() {

		mapa = new Mapa();

		try {
			File fXmlFile = new File("Mapa.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("localizacion");

			Localizacion loc = null;

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					loc = mapa.añadirLocalizacion(eElement.getAttribute("id"));
					estado.añadirNombre(eElement.getAttribute("id"));

					String[] cade = eElement
							.getElementsByTagName("conectadoCon").item(0)
							.getTextContent().split(" ");

					for (String conectadoCon : cade) {
						loc.añadirConectado(conectadoCon);
						estado.añadirAdyacente(loc.getNombre(), conectadoCon);
						estado.añadirNombre(conectadoCon);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return mapa;
	}

	protected void setup(){
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Mundo");
		sd.setName("JADE-Mundo");
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		myGui = new Gui(this);
	    myGui.setVisible(true);
	    
	    try {
			new LoaderPnjs(this);
			new LoaderMontruos(this);
			new LoaderPersonajes(this);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    addBehaviour(new Objetivos());
	    addBehaviour(new PersonajeSecuestrado());
		addBehaviour(new ObjetivoSecuestro());
		addBehaviour(new LocalizarPersonajes());
		addBehaviour(new ToPDDLfile());
		addBehaviour(new MoverPrincesaSecuestrada());
		addBehaviour(new Secuestro());
		addBehaviour(new Liberar());
		addBehaviour(new PersonajeEnCasa());
		addBehaviour(new ConvertirEnHeroe());
		addBehaviour(new MuertePersonaje());
		addBehaviour(new DondeEstaPersonaje());
	}
	
	protected void takeDown() {
		try {
			DFService.deregister(this);
			System.out.println("Mientras tu estas aquí, la Legión Ardiente se dirige hacia Azeroth con un descomunal ejército...");

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	@Override
	protected void onGuiEvent(GuiEvent evento) {
		// TODO Auto-generated method stub
		comando = evento.getType();
		if (comando == Vocabulario.SALIR) {
	         doDelete();
	         System.exit(0);
	    }
		if (comando == Vocabulario.CREAR_AGENTE){
			try {
				PlatformController container = getContainerController();
				
				AgentController guest;
				Iterator param = evento.getAllParameter();
				String nomb= (String)param.next();
				String clas=(String)param.next();
				String[] args = {(String)param.next(),String.valueOf(param.next()),String.valueOf(param.next()),String.valueOf(param.next()),String.valueOf(param.next()),String.valueOf(param.next())};
				guest = container.createNewAgent(nomb, clas,args);
				agentes.add(guest);
				
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		if (comando == Vocabulario.INICIAR_HISTORIA){
			for (AgentController agen : agentes){
				try {
					agen.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			agentes.clear();
		}
		
	}
	
	private class Objetivos extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("guardaObjetivos"));
			ACLMessage receive = myAgent.receive(mt);
			
			if (receive != null) {
				ACLMessage reply = receive.createReply();

				estado.setObjetivos(receive.getSender().getLocalName(), receive.getContent());

				send(reply);
			} else
				block();
			
		}
		
	}
	
	private class PersonajeSecuestrado extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("quienSecuestro"));
			ACLMessage receive = myAgent.receive(mt);
			
			if (receive != null) {
				ACLMessage reply = receive.createReply();

				reply.setContent(estado.getPrincesaObjetivo(receive.getSender().getLocalName()));

				send(reply);
			} else
				block();
		}
		
	}
	
	private class ToPDDLfile extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("toPDDL"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				ACLMessage reply = receive.createReply();

				String[] mensaje = receive.getContent().split(" ");
				String clase = mensaje[0];
				String nombrePersonaje = mensaje[1];
				String problema = "";

				problema += "(define (problem " + clase + ")" + "\n";
				problema += "(:domain Historia)" + "\n";
				problema += "\n";
				problema += "(:objects" + "\n";

				problema += estado.nombresToString();

				problema += ")" + "\n";

				problema += "(:init" + "\n";
				problema += estado.toString(nombrePersonaje);
				problema += ")\n";
				
				problema += "(:goal " + estado.getObjetivos(nombrePersonaje) + " )"
						+ "\n)";

				PrintWriter writer;
				try {
					writer = new PrintWriter(nombrePersonaje + ".pddl", "UTF-8");
					writer.println(problema);
					writer.close();

				} catch (Exception e) {
				}

				send(reply);
			} else
				block();
		}
	}

	private class LocalizarPersonajes extends CyclicBehaviour {

		public void action() {
			
			boolean ok = true;

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Mover"));
			ACLMessage receive = myAgent.receive(mt);
			
			//espera que un agente pida moverse
			if (receive != null) {
				ACLMessage reply = receive.createReply();
				String[] mensaje = receive.getContent().split(" ");
				String locDest = mensaje[1];
				AID personaje = receive.getSender();

				Localizacion loc2 = mapa.getLocalizacion(locDest);
				
				//si el mensaje tiene un personaje[0], una locDestino[1] y una loc origen[2]
				if (mensaje.length == 3) {

					String locOrigen = mensaje[2];
					Localizacion loc1 = mapa.getLocalizacion(locOrigen);
					
					//comprueba que los nombres del mapa son correctos y que estan conectados
					if (loc1 != null && loc1.existeConexion(locDest))
						ok = loc1.eliminarPersonaje(personaje.getLocalName());

					else
						ok = false;
				}
				
				//cuando todo va bien
				if (ok && loc2 != null) {
					loc2.añadirPersonaje(personaje.getLocalName());
					estado.añadirLocalizacion(personaje.getLocalName(), locDest);
					
					//si un caballero va al cruce
					if (locDest.equalsIgnoreCase("cruce") && mensaje[0].equals("Caballero") ){
						ACLMessage mover = new ACLMessage(ACLMessage.INFORM);
						mover.addReceiver(getAID("Trundle"));
						mover.setConversationId("Cruzar");
						mover.setReplyWith("cruzar" + System.currentTimeMillis());
						mover.setContent(personaje.getLocalName());
						send(mover);
//						MessageTemplate espera = MessageTemplate
//								.MatchInReplyTo(mover.getReplyWith());
//						blockingReceive(espera);
					}
					
					
					reply.setPerformative(ACLMessage.CONFIRM);
					reply.setContent(loc2.getNombre());

					if (mensaje.length == 2) { //cuando se crean los personajes, la primera localizacion
						estado.añadirPersonaje(mensaje[0],
								personaje.getLocalName());
						estado.añadirCasa(personaje.getLocalName(), locDest);
						estado.añadirNombre(personaje.getLocalName());
					}
				} else
					reply.setPerformative(ACLMessage.FAILURE);

				send(reply);

			} else
				block();
		}
	}

	private class MoverPrincesaSecuestrada extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Mundo-Mover-Princesa"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");
				ACLMessage moverPrincesa = new ACLMessage(ACLMessage.REQUEST);
				moverPrincesa.setReplyWith("mover-princesa"+ System.currentTimeMillis());
				moverPrincesa.setConversationId("Mover-Princesa");
				moverPrincesa.setContent(contenido[0] + " "+ receive.getSender().getLocalName());
				
				AID princesa = new AID(
						(String) estado.nombreCorrecto(contenido[1]),
						AID.ISLOCALNAME);
				moverPrincesa.addReceiver(princesa);
				send(moverPrincesa);

				mt = MessageTemplate.MatchInReplyTo(moverPrincesa
						.getReplyWith());
				ACLMessage rec1 = receive(mt);
				while (rec1 == null)
					block();

				send(receive.createReply());

			} else
				block();
		}
	}

	private class Secuestro extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Secuestro"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				ACLMessage reply = receive.createReply();
				String princesa = receive.getContent();
				String secuestrador = receive.getSender().getLocalName();

				if (estado.estanMismaLocalizacion(secuestrador, princesa)) {
					estado.añadirPersonajeConPrincesa(secuestrador, princesa);
					estado.secuestrar(princesa);
					estado.estaLlenoPersonaje(secuestrador);
					estado.estaCansado(secuestrador);

					reply.setContent(estado.nombreCorrecto(princesa));

				} else
					reply.setContent("fallo");

				myAgent.send(reply);

			} else
				block();
		}

	}

	private class Liberar extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Liberar"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");
				
				if(contenido.length == 3){ //cuando el caballero rescata a la princesa
					estado.estaLlenoPersonaje(contenido[0]);
					estado.añadirPersonajeConPrincesa(contenido[0], contenido[1]);
					estado.borrarPersonajeConPrincesa(contenido[2]);
				}
				else if (contenido.length == 2) { //cuando la princesa se escapa
					estado.borrarPersonajeConPrincesa(contenido[1]);
					estado.liberar(contenido[0]);
					estado.estaLibrePersonaje(contenido[0]);
				}
				

				ACLMessage reply = receive.createReply();
				reply.setContent(estado.nombreCorrecto(contenido[1]));
				send(reply);

			} else
				block();
		}
	}

	private class PersonajeEnCasa extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Dejar en Casa"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String princesa = receive.getContent();
				estado.liberar(princesa);
				estado.borrarPersonajeConPrincesa(receive.getSender()
						.getLocalName());
				estado.añadirPrincesaSalvada(princesa);
				estado.estaLibrePersonaje(receive.getSender().getLocalName());

				ACLMessage reply = receive.createReply();
				reply.setContent(estado.nombreCorrecto(princesa));
				send(reply);

			} else
				block();
		}
	}

	private class ConvertirEnHeroe extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Ser Heroe"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				estado.añadirHeroe(receive.getSender().getLocalName());
				send(receive.createReply());

			} else
				block();

		}
	}

	private class MuertePersonaje extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Muerto"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				estado.mata(receive.getContent());
				send(receive.createReply());

			} else
				block();
		}
	}

	private class ObjetivoSecuestro extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("ObjetivoSecuestro"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			ACLMessage receive = myAgent.receive(mt);
			if (receive != null) {

				estado.setPrincesaObjetivo(receive.getSender().getLocalName(),receive.getContent());
				ACLMessage reply = receive.createReply();
				send(reply);

			} else
				block();
			
		}
		
	}

	private class DondeEstaPersonaje extends CyclicBehaviour{
		/*
		 * Atendera las llamadas de otros personajes que preguntan por la locaclizacion de 
		 * otro personaje
		 * */

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Localizar"),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			ACLMessage receive = receive(mt);
			if (receive != null) {

				ACLMessage reply = receive.createReply();
				reply.setContent(estado.getLocalizacion(receive.getContent()));
				send(reply);

			} else
				block();
			
		}
		
	}
}
