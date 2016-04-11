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

public class LoaderObjetos {
	public LoaderObjetos(Mundo mundo) throws ControllerException{
		String nombre;
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		
		
		try {
			File fXmlFile = new File("Objetos.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nListCons = doc.getElementsByTagName("consumible");
			
			NodeList nListEquip = doc.getElementsByTagName("equipo");

			for (int temp = 0; temp < nListCons.getLength(); temp++) {

				Node nNode = nListCons.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					nombre = eElement.getAttribute("nombre");
					String[] args = {eElement.getAttribute("localizacion"), 
							eElement.getAttribute("vida"),
							eElement.getAttribute("fuerza"),
							eElement.getAttribute("destreza"),
							eElement.getAttribute("inteligencia"),
							eElement.getAttribute("codicia")};
					
				
				}
			}
			
			for (int temp = 0; temp < nListEquip.getLength(); temp++) {

				Node nNode = nListEquip.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					nombre = eElement.getAttribute("nombre");
					String[] args = {eElement.getAttribute("cantidad"), 
							eElement.getAttribute("vida"),
							eElement.getAttribute("fuerza"),
							eElement.getAttribute("destreza"), 
							eElement.getAttribute("inteligencia"),
							eElement.getAttribute("codicia")};
					
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
