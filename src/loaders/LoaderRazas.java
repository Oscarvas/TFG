package loaders;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mundo.Mundo;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;

public class LoaderRazas {
	public LoaderRazas(Mundo mundo) throws ControllerException {
		String nombre;
		PlatformController container = mundo.getContainerController();

		AgentController guest;

		try {
			// indicar el fichero de configuracion y crear el builder
			File fXmlFile = new File("PNJs.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			// volcar en una lista de nodos los pnjs que se quieren coger
			NodeList nList = doc.getElementsByTagName("pnj");
			// crear un numero n de pnjs a hacer
			for (int n = 0; n < nList.getLength(); n++) {

				Node nNode = nList.item(n);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					nombre = eElement.getAttribute("nombre");
					String[] args = { eElement.getAttribute("vida"),
							eElement.getAttribute("fuerza"),
							eElement.getAttribute("destreza"),
							eElement.getAttribute("inteligencia"),
							eElement.getAttribute("codicia") };

					guest = container.createNewAgent(nombre,
							"personajes.pnjs.PNJ", args);
					guest.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
