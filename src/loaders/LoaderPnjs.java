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

public class LoaderPnjs {
	public LoaderPnjs(Mundo mundo) throws ControllerException{
		String nombre;
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		
		
		try {
			//indicar el fichero de configuracion y crear el builder
			File fXmlFile = new File("PNJs.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
			//volcar en una lista de nodos los pnjs que se quieren coger
			NodeList nList = doc.getElementsByTagName("pnj");
			//crear un numero n de pnjs a hacer
			for (int n = 0; n < nList.getLength(); n++) {

				Node nNode = nList.item(n);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					nombre = eElement.getAttribute("nombre");
					String[] args = { eElement.getAttribute("sexo"),eElement.getAttribute("oficio"),eElement.getAttribute("localizacion")};
					
					guest = container.createNewAgent(nombre, "personajes.pnjs.PNJ", args);
					guest.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
