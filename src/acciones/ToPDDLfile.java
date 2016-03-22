package acciones;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ToPDDLfile extends CyclicBehaviour{
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
			String nombrePrincesa = mensaje[2];
			String problema = "";

			problema += "(define (problem " + clase + ")" + "\n";
			problema += "(:domain Historia)" + "\n";
			problema += "\n";
			problema += "(:objects" + "\n";

//			problema += estado.nombresToString();

			problema += ")" + "\n";

			problema += "(:init" + "\n";
//			problema += estado.toString();
			problema += ")\n";

			try {
				File fXmlFile = new File("Objetivos.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("personaje");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						if (eElement.getAttribute("tipo").equalsIgnoreCase(
								clase)) {

							String cadena = eElement
									.getElementsByTagName("objetivo")
									.item(0).getTextContent();

							String objetivo = cadena
									.replace("Dragon", nombrePersonaje)
									.replace("Caballero", nombrePersonaje)
									.replace("Princesa", nombrePrincesa);

							problema += "(:goal \n" + objetivo + "\n)"
									+ "\n)";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}

			PrintWriter writer;
			try {
				writer = new PrintWriter(nombrePersonaje + ".pddl", "UTF-8");
				writer.println(problema);
				writer.close();

			} catch (Exception e) {
			}

			myAgent.send(reply);
		} else
			block();
	}

}
