package mundo;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gui.Gui;
import jade.core.AID;
import jade.core.Agent;
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
import loaders.LoaderFrases;
import loaders.LoaderAntagonistas;
import loaders.LoaderProtagonistas;
import loaders.LoaderPnjs;
import objetos.Objeto;
import ontologia.Diccionario;
import ontologia.Mitologia;
import ontologia.Vocabulario;
import ontologia.Raza;

@SuppressWarnings("serial")
public class Mundo extends GuiAgent {

	private Mapa mapa;
	private Estado estado;
	private int comando = Vocabulario.STANDBY;
	transient protected Gui myGui;
	private ArrayList<AgentController> agentes;
	public HashMap<String, ArrayList<Localizacion>> regiones; // <tipoLoc,
																// localizaciones>
	private String id;
	private String tipo;
	public ArrayList<Raza> razas;
	public static Diccionario diccionario;

	public Mundo() {
		Mundo.diccionario = LoaderFrases.loaderFrases();
		this.estado = new Estado();
		this.regiones = new HashMap<String, ArrayList<Localizacion>>();
		this.mapa = cargarMapa();// Mapa.getMapa(this.estado);
		this.agentes = new ArrayList<AgentController>();

	}

	public Mapa cargarMapa() {

		this.mapa = new Mapa();

		try {
			File fXmlFile = new File("MapaReducido.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			// el mapa se carga en dos arrays, el arraylist de localizaciones, que ayuda ademas para ver las adyacencias del mapa
			// y el hashmap de tipoLocalizacion-localizacion, que sirve para saber que es cada localización, y ayuda a que los pesonajes
			// se creen en el lugar que deben

			NodeList nList = doc.getElementsByTagName("localizacion");

			Localizacion loc = null;

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					this.id = eElement.getAttribute("id");
					this.tipo = eElement.getAttribute("tipo");
					
					int i = this.estado.getAlmacen().hayObjetoClave(this.id );
					Objeto obj = null;
					String tipoObjeto = "";
					if (i == -1) {
						if (this.estado.getAlmacen().hayObjetos("consumible")) {
							obj = this.estado.getAlmacen().extraerObjeto("consumible", this.estado.getAlmacen().consumibleAleatorio());
							tipoObjeto = "consumible";
						}
					} else {
						obj = this.estado.getAlmacen().extraerObjeto("clave", i);
						tipoObjeto = "clave";
						this.estado.setObjetoEnLoc(obj.getId(), this.id);
						this.estado.añadirNombre(obj.getId());
						this.estado.guardaClave(obj);
					}
					
					
					
					loc = mapa.añadirLocalizacion(this.id, this.tipo, obj,tipoObjeto);
					estado.añadirNombre(this.id);

					String[] cade = eElement.getElementsByTagName("conectadoCon").item(0).getTextContent().split(" ");

					for (String conectadoCon : cade) {
						loc.añadirConectado(conectadoCon);
						estado.añadirAdyacente(loc.getNombre(), conectadoCon);
						estado.añadirNombre(conectadoCon);
					}

					// cargamos el mapa en el hash map para tener sus
					// localizaciones
					ArrayList<Localizacion> aux = new ArrayList<Localizacion>();

					if (this.regiones.containsKey(this.tipo)) {
						aux = this.regiones.get(this.tipo);
					}

					Localizacion locAux = new Localizacion(this.id, this.tipo);
					aux.add(locAux);
					this.regiones.put(this.tipo, aux);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return mapa;
	}
	
	public ArrayList<Raza> cargarRazas(){
		
		return this.razas;
	}

	protected void setup() {
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
			new LoaderAntagonistas(this);
			new LoaderProtagonistas(this);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addBehaviour(new Objetivos());
		addBehaviour(new PersonajeSecuestrado());
		addBehaviour(new ObjetivoSecuestro());
		addBehaviour(new LocalizarPersonajes());
		addBehaviour(new ToPDDLfile());
		addBehaviour(new MoverVictimaSecuestrada());
		addBehaviour(new Secuestro());
		addBehaviour(new Contratado());
		addBehaviour(new Liberar());
		addBehaviour(new PersonajeEnCasa());
		addBehaviour(new ConvertirEnHeroe());
		addBehaviour(new ConvertirEnSabio());
		addBehaviour(new ConvertirEnVillano());
		addBehaviour(new MuertePersonaje());
		addBehaviour(new DondeEstaPersonaje());
		addBehaviour(new Proteger());
		addBehaviour(new RecuperarObjeto());
		addBehaviour(new RestaurarObjeto());
		addBehaviour(new MoverObjetos());
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
			System.out.println(
					"Mientras tu estas aquí, la Legión Ardiente se dirige hacia Azeroth con un descomunal ejército...");

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
		if (comando == Vocabulario.CREAR_AGENTE) {
			try {
				PlatformController container = getContainerController();

				AgentController guest;
				Iterator param = evento.getAllParameter();
				String nomb = (String) param.next();
				String clas = (String) param.next();
				String[] args = { (String) param.next(), String.valueOf(param.next()), String.valueOf(param.next()),
						String.valueOf(param.next()), String.valueOf(param.next()), String.valueOf(param.next()) };
				guest = container.createNewAgent(nomb, clas, args);
				agentes.add(guest);

			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (comando == Vocabulario.INICIAR_HISTORIA) {
			for (AgentController agen : agentes) {
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

	private class Objetivos extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
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

	private class PersonajeSecuestrado extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("quienSecuestro"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				ACLMessage reply = receive.createReply();

				reply.setContent(estado.getVictimaObjetivo(receive.getSender().getLocalName()));

				send(reply);
			} else
				block();
		}

	}

	private class ToPDDLfile extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
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

				problema += "(:goal " + estado.getObjetivos(nombrePersonaje) + " )" + "\n)";

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

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Mover"));
			ACLMessage receive = myAgent.receive(mt);

			// espera que un agente pida moverse
			if (receive != null) {
				ACLMessage reply = receive.createReply();
				String[] mensaje = receive.getContent().split(" ");
				
				//comprobamos la locaclizacion del personaje ligada a su clase
				if (mensaje.length == 1) {

					Mitologia aux = Mitologia.valueOf(mensaje[0].toUpperCase());
					String tipoLoc = aux.getZona();
					ArrayList<Localizacion> localizacionesAux = regiones.get(tipoLoc);
					Localizacion locAux = localizacionesAux.get(new Random().nextInt(localizacionesAux.size()));

					reply.setContent(locAux.getNombre());

				} else {
					String locDest = mensaje[1];
					AID personaje = receive.getSender();

					Localizacion loc2 = mapa.getLocalizacion(locDest);

					// si el mensaje tiene un personaje[0], una locDestino[1] y
					// una
					// loc origen[2]
					if (mensaje.length == 3) {

						String locOrigen = mensaje[2];
						Localizacion loc1 = mapa.getLocalizacion(locOrigen);

						// comprueba que los nombres del mapa son correctos y
						// que
						// estan conectados
						if (loc1 != null && loc1.existeConexion(locDest))
							ok = loc1.eliminarPersonaje(personaje.getLocalName());

						else
							ok = false;
					}

					// cuando todo va bien
					if (ok && loc2 != null) {
						loc2.añadirPersonaje(personaje.getLocalName());
						estado.añadirLocalizacion(personaje.getLocalName(), locDest);

						// si un aspirante coincide con un emboscador
						Emboscadores(myAgent, mensaje[0], personaje.getLocalName());
						
						// si un aspirante coincide con un ladron
						EncuentroLadron(myAgent, mensaje[0], personaje.getLocalName());

						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent(loc2.getNombre());

						if (mensaje.length == 2) { // cuando se crean los
													// personajes, la primera
													// localizacion
							estado.añadirPersonaje(mensaje[0], personaje.getLocalName());
							estado.añadirCasa(personaje.getLocalName(), locDest);
							estado.añadirNombre(personaje.getLocalName());
							
							if (mensaje[0].equalsIgnoreCase("PNJ"))
								estado.setPnjEnLoc(loc2.getNombre(), personaje.getLocalName());
							
						}
						else{
							if (estado.esConsumidor(mensaje[0]) && !loc2.cofreVacio("consumible")) {
								ACLMessage consume = new ACLMessage(ACLMessage.INFORM);
								consume.addReceiver(personaje);
								consume.setConversationId("Consumir");
								Objeto bocado = loc2.abrirCofre("consumible");
								consume.setContent(bocado.mensaje());
								send(consume);
								
								Gui.setHistoria(personaje.getLocalName()+" ha encontrado "+bocado.toString());
								
								//evaluamos la existencia de un pnj en dicha localizacion
								if (estado.getPnjEnLoc(loc2.getNombre())!=null){
									ACLMessage bendicion = new ACLMessage(ACLMessage.INFORM);
									bendicion.addReceiver(personaje);
									bendicion.setConversationId("Bendicion");
									bendicion.setContent(estado.getPnjEnLoc(loc2.getNombre()));
									send(bendicion);
								}
							}
						}
						
					} else
						reply.setPerformative(ACLMessage.FAILURE);
				}
				
				send(reply);

			} else
				block();
		}
	}

	/*
	 * Avisamos a todos los emboscadores por si les interesa/pueden actuar
	 */
	private void Emboscadores(Agent myAgent, String clase, String nombre) {

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Emboscador");
		template.addServices(sd);

		try {

			DFAgentDescription[] result = DFService.search(myAgent, template);
			AID[] emboscadores = new AID[result.length];

			if (emboscadores.length > 0) {
				for (int i = 0; i < result.length; i++) {
					emboscadores[i] = result[i].getName();
				}

				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				cfp.setContent(clase);
				cfp.setConversationId("SolicitarServicio");

				for (AID emboscador : emboscadores) {
					cfp.addReceiver(emboscador);
				}
//				for (int i = 0; i < emboscadores.length; i++) {
//					cfp.addReceiver(emboscadores[i]);
//				}

				cfp.setReplyWith("cfp" + System.currentTimeMillis());
				myAgent.send(cfp);
				MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("SolicitarServicio"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));

				myAgent.addBehaviour(new RecibirOfertas(mt, emboscadores.length, nombre));
			}

		} catch (Exception fe) {
			fe.printStackTrace();
		}

	}

	private class RecibirOfertas extends CyclicBehaviour {

		private MessageTemplate mt;
		private int interesados;
		private AID maton;
		private String nombreEmboscado;

		public RecibirOfertas(MessageTemplate mt, int length, String emboscado) {
			this.mt = mt;
			this.interesados = length;
			this.maton = null;
			this.nombreEmboscado = emboscado;
		}

		public void action() {

			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				if (msg.getPerformative() == ACLMessage.PROPOSE) {
					// String loc = msg.getContent();
					if (estado.estanMismaLocalizacion(msg.getSender().getLocalName(), nombreEmboscado)) {
						maton = msg.getSender();

						ACLMessage mover = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
						mover.addReceiver(maton);
						mover.setConversationId("Cruzar");
						mover.setReplyWith("cruzar" + System.currentTimeMillis());
						mover.setContent(nombreEmboscado);
						myAgent.send(mover);
					}
				}
				interesados--;

				if (!listo())
					reset();
			} else
				block();
		}

		private boolean listo() {
			return interesados == 0 || maton != null;
		}
	}

	/*
	 * Avisamos a todos los ladrones por si les interesa/pueden actuar
	 */
	private void EncuentroLadron(Agent myAgent, String clase, String nombre) {
		
		if(estado.esAtracable(clase)){
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Ladron");
			template.addServices(sd);

			try {

				DFAgentDescription[] result = DFService.search(myAgent, template);
				AID[] ladrones = new AID[result.length];

				if (ladrones.length > 0) {
					boolean hayLadron = false;
					int i = 0;
					while (i < result.length && !hayLadron) {
						ladrones[i] = result[i].getName();
						if(estado.estanMismaLocalizacion(ladrones[i].getLocalName(), nombre)) //si el aspirante y el ladron estan en la misma loc
							hayLadron = true;
						i++;
					}
					
					if (hayLadron)
						AcudeAyudante(myAgent,ladrones[i-1].getLocalName());
				}

			} catch (Exception fe) {
				fe.printStackTrace();
			}
		}
	}
	
	private void AcudeAyudante(Agent myAgent, String ladron){

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Cazamagia");
		template.addServices(sd);

		try {

			DFAgentDescription[] result = DFService.search(myAgent, template);
			AID[] hechiceros = new AID[result.length];

			if (hechiceros.length > 0) {
				for (int i = 0; i < result.length; i++) {
					hechiceros[i] = result[i].getName();
				}
				AID ayudante = hechiceros[new Random().nextInt(hechiceros.length)];
				
				ACLMessage fairytail = new ACLMessage(ACLMessage.INFORM);
				fairytail.setConversationId("HoraMagica");
				fairytail.addReceiver(ayudante);				
				fairytail.setContent(estado.tieneObjeto(ladron));
				myAgent.send(fairytail);

			}

		} catch (Exception fe) {
			fe.printStackTrace();
		}
	}
	
	private class MoverVictimaSecuestrada extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Mundo-Mover-Victima"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");
				ACLMessage moverVictima = new ACLMessage(ACLMessage.REQUEST);
				moverVictima.setReplyWith("mover-victima" + System.currentTimeMillis());
				moverVictima.setConversationId("Mover-Victima");
				moverVictima.setContent(contenido[0] + " " + receive.getSender().getLocalName());

				AID victima = new AID((String) estado.nombreCorrecto(contenido[1]), AID.ISLOCALNAME);
				moverVictima.addReceiver(victima);
				send(moverVictima);

				mt = MessageTemplate.MatchInReplyTo(moverVictima.getReplyWith());
				ACLMessage rec1 = receive(mt);
				while (rec1 == null)
					block();

				send(receive.createReply());

			} else
				block();
		}
	}
	
	private class MoverObjetos extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("MoverObjetos"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");

				for (int i = 1 ; i < contenido.length; i++)
					estado.setObjetoEnLoc(contenido[i], contenido[0]);

				send(receive.createReply());

			} else
				block();
		}
	}

	private class Proteger extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Proteger"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				ACLMessage reply = receive.createReply();
				String nombre = estado.nombreCorrecto(receive.getContent());
				Objeto obj = estado.extraerObjeto(nombre);
				
				reply.setContent(nombre +" "+obj.getDesc());
				estado.estaLlenoPersonaje(receive.getSender().getLocalName());
				estado.estaCansado(receive.getSender().getLocalName());
				estado.guardaObjeto(receive.getSender().getLocalName(), nombre);
				myAgent.send(reply);

			} else
				block();
			
		}
		
	}
	
	private class RecuperarObjeto extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("RecuperarObjeto"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				ACLMessage reply = receive.createReply();
				String contenido [] = receive.getContent().split(" ");
				
				String ladron = estado.nombreCorrecto(contenido[0]);
				String objeto = estado.nombreCorrecto(contenido[1]);
				
				reply.setContent(objeto);
				estado.estaLlenoPersonaje(receive.getSender().getLocalName());
				estado.pierdeObjeto(ladron);
				estado.guardaObjeto(receive.getSender().getLocalName(), objeto);
				myAgent.send(reply);

			} else
				block();
			
		}
		
	}
	
	private class RestaurarObjeto extends CyclicBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Restaurar"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				ACLMessage reply = receive.createReply();
				String contenido = receive.getContent();
				
				String objeto = estado.nombreCorrecto(contenido);		
				reply.setContent(objeto);
				estado.estaLibrePersonaje(receive.getSender().getLocalName());
				estado.pierdeObjeto(receive.getSender().getLocalName());
				myAgent.send(reply);

			} else
				block();
			
		}
		
	}
	
	private class Secuestro extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Secuestro"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				ACLMessage reply = receive.createReply();
				String victima = receive.getContent();
				String secuestrador = receive.getSender().getLocalName();

				if (estado.estanMismaLocalizacion(secuestrador, victima)) {
					estado.añadirPersonajeConVictima(secuestrador, victima);
					estado.secuestrar(victima);
					estado.estaLlenoPersonaje(secuestrador);
					estado.estaCansado(secuestrador);

					reply.setContent(estado.nombreCorrecto(victima));

				} else
					reply.setContent("fallo");

				myAgent.send(reply);

			} else
				block();
		}

	}

	private class Liberar extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Liberar"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");

				if (contenido.length == 3) { // cuando el aspirante rescata a la
												// victima
					estado.estaLlenoPersonaje(contenido[0]);
					estado.añadirPersonajeConVictima(contenido[0], contenido[1]);
					estado.borrarPersonajeConVictima(contenido[2]);
				} else if (contenido.length == 2) { // cuando la victima se
													// escapa
					estado.borrarPersonajeConVictima(contenido[1]);
					estado.liberar(contenido[0]);
					estado.estaLibrePersonaje(contenido[0]);
					
					//aviso al aspirante
					String aspirante = estado.getAspirante(contenido[0]);
					
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("Matadragones");
					template.addServices(sd);
					
					DFAgentDescription[] result;
					try {
						result = DFService.search(myAgent, template);
						AID[] aspirantes = new AID[result.length];
						for (int i = 0; i < result.length; i++)
							aspirantes[i] = result[i].getName();
						
						for (AID aid : aspirantes) {
							if (aid.getLocalName().equalsIgnoreCase(aspirante)){
								ACLMessage retirada = new ACLMessage(ACLMessage.INFORM);
								retirada.addReceiver(aid);
								retirada.setConversationId("Retirada");
								retirada.setContent("La victima ha escapado por su cuenta");
								myAgent.send(retirada);
							}
						}
						
					} catch (FIPAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}

				ACLMessage reply = receive.createReply();
				reply.setContent(estado.nombreCorrecto(contenido[1]));
				send(reply);

			} else
				block();
		}
	}
	
	private class Contratado extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Contratado"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String[] contenido = receive.getContent().split(" ");
				estado.setAspirante(estado.nombreCorrecto(contenido[0]), estado.nombreCorrecto(contenido[1]));
				
			} else
				block();
		}
	}

	private class PersonajeEnCasa extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Dejar en Casa"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				String victima = receive.getContent();
				estado.liberar(victima);
				estado.borrarPersonajeConVictima(receive.getSender().getLocalName());
				estado.añadirVictimaSalvada(victima);
				estado.estaLibrePersonaje(receive.getSender().getLocalName());

				ACLMessage reply = receive.createReply();
				reply.setContent(estado.nombreCorrecto(victima));
				send(reply);

			} else
				block();
		}
	}

	private class ConvertirEnHeroe extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Ser Heroe"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				estado.añadirHeroe(receive.getSender().getLocalName());
				send(receive.createReply());

			} else
				block();

		}
	}
	
	private class ConvertirEnSabio extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Ser Sabio"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				estado.añadirSabio(receive.getSender().getLocalName());
				send(receive.createReply());

			} else
				block();

		}
	}
	private class ConvertirEnVillano extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchConversationId("Ser Villano"));

			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {

				estado.añadirVillano(receive.getSender().getLocalName());
				send(receive.createReply());

			} else
				block();

		}
	}

	private class MuertePersonaje extends CyclicBehaviour {

		public void action() {

			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
					MessageTemplate.MatchConversationId("Muerto"));
			ACLMessage receive = myAgent.receive(mt);

			if (receive != null) {
				estado.mata(receive.getContent());
				send(receive.createReply());

			} else
				block();
		}
	}

	private class ObjetivoSecuestro extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub
			MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("ObjetivoSecuestro"),
					MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			ACLMessage receive = myAgent.receive(mt);
			if (receive != null) {

				estado.setVictimaObjetivo(receive.getSender().getLocalName(), receive.getContent());
				ACLMessage reply = receive.createReply();
				send(reply);

			} else
				block();

		}

	}

	private class DondeEstaPersonaje extends CyclicBehaviour {
		/*
		 * Atendera las llamadas de otros personajes que preguntan por la
		 * locaclizacion de otro personaje
		 */

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
