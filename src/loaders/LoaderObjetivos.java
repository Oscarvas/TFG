package loaders;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import personajes.Personaje;

public class LoaderObjetivos {
	
	private String clase;
	private AID agenteMundo;
	private Agent myAgent;
	private String objetivo;
	
	public LoaderObjetivos (Agent myAgent){
		this.myAgent = myAgent;
		this.clase = ((Personaje) myAgent).getClase();
		this.agenteMundo = ((Personaje) myAgent).getAgenteMundo();
		this.objetivo="(enLoc "+myAgent.getLocalName()+" "+((Personaje) myAgent).getCasa()+")";
	}
	
	public LoaderObjetivos (Agent myAgent,String objetivo){
		this.myAgent = myAgent;
		this.clase = ((Personaje) myAgent).getClase();
		this.agenteMundo = ((Personaje) myAgent).getAgenteMundo();
		this.objetivo=objetivo;
	}
	
	public void dismiss(){
		
		ACLMessage miObjetivo = new ACLMessage(ACLMessage.REQUEST);
		miObjetivo.addReceiver(agenteMundo);
		miObjetivo.setConversationId("guardaObjetivos");
		miObjetivo.setReplyWith("guardaObjetivos" + System.currentTimeMillis());
		miObjetivo.setContent(objetivo);
		myAgent.send(miObjetivo);

		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchConversationId("guardaObjetivos"),
				MessageTemplate.MatchInReplyTo(miObjetivo.getReplyWith()));
		myAgent.blockingReceive(mt);
		
	}

	public void execute() {
		// TODO Auto-generated method stub
		
		
		try {
			File fXmlFile = new File("Objetivos.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("personaje");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if (eElement.getAttribute("tipo").equalsIgnoreCase(clase)) {

						int numObjetivos = eElement.getElementsByTagName("objetivo").getLength();
						ArrayList<String> objetivos = new ArrayList<String>();
						for(int i=0;i<numObjetivos;i++){
							objetivos.add(eElement
								.getElementsByTagName("objetivo")
								.item(i).getTextContent());
						}
						String cadena = objetivos.get(new Random().nextInt(objetivos.size()));

						String polivalente = myAgent.getLocalName();
						
						//Si mi clase no es princesa, entonces estoy secuestrada
						if(clase.equalsIgnoreCase("Secuestrador") || clase.equalsIgnoreCase("Caballero")){
							
							ACLMessage secuestrada = new ACLMessage(ACLMessage.REQUEST);
							secuestrada.addReceiver(agenteMundo);
							secuestrada.setConversationId("quienSecuestro");
							secuestrada.setReplyWith("quienSecuestro" + System.currentTimeMillis());
							secuestrada.setContent(clase);
							myAgent.send(secuestrada);

							MessageTemplate mt = MessageTemplate.and(
									MessageTemplate.MatchConversationId("quienSecuestro"),
									MessageTemplate.MatchInReplyTo(secuestrada.getReplyWith()));
							ACLMessage reply = myAgent.blockingReceive(mt);
							
							polivalente = reply.getContent();
							
						}
							
						
						String objetivo = cadena
								.replace("Rey", this.objetivo)
								.replace("Princesa", polivalente) 
								.replace("Caballero", myAgent.getLocalName())
								.replace("Druida", myAgent.getLocalName())
								.replace("Mago", myAgent.getLocalName())
								.replace("Emboscador", myAgent.getLocalName())
								.replace("Guardian", myAgent.getLocalName())
								.replace("Secuestrador", myAgent.getLocalName())
								.replace("Maligno", myAgent.getLocalName());
						
						ACLMessage miObjetivo = new ACLMessage(ACLMessage.REQUEST);
						miObjetivo.addReceiver(agenteMundo);
						miObjetivo.setConversationId("guardaObjetivos");
						miObjetivo.setReplyWith("guardaObjetivos" + System.currentTimeMillis());
						miObjetivo.setContent(objetivo);
						myAgent.send(miObjetivo);

						MessageTemplate mt2 = MessageTemplate.and(
								MessageTemplate.MatchConversationId("guardaObjetivos"),
								MessageTemplate.MatchInReplyTo(miObjetivo.getReplyWith()));
						myAgent.blockingReceive(mt2);

					}
				}				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}

}
