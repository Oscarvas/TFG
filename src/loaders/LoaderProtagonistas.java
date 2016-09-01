package loaders;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import mundo.Mundo;

public class LoaderProtagonistas {
	public LoaderProtagonistas(Mundo mundo) throws ControllerException{
		String nombre;
		String clase;
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		
		
		try {
			File fXmlFile = new File("Protagonistas.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("protagonista");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					nombre = eElement.getAttribute("nombre");
					clase = eElement.getAttribute("clase");
					String[] args = {eElement.getAttribute("rol"),eElement.getAttribute("raza"),eElement.getAttribute("sexo"),eElement.getAttribute("vida"),eElement.getAttribute("fuerza"),
							eElement.getAttribute("destreza"), eElement.getAttribute("inteligencia"),eElement.getAttribute("codicia")};
					
					guest = container.createNewAgent(nombre, "personajes.protagonistas."+clase, args);
					guest.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
