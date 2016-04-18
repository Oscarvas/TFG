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

public class LoaderMontruos {
	public LoaderMontruos(Mundo mundo) throws ControllerException{
		String nombre;
		String clase;
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		
		try {
			//indicar el fichero de configuracion y crear el builder
			File fXmlFile = new File("Monstruos.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
			//volcar en una lista de nodos los pnjs que se quieren coger
			NodeList nList = doc.getElementsByTagName("monstruo");
			//crear un numero n de pnjs a hacer
			for (int n = 0; n < nList.getLength(); n++) {

				Node nNode = nList.item(n);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					nombre = eElement.getAttribute("nombre");
					clase = eElement.getAttribute("clase");
					String[] args = {eElement.getAttribute("localizacion")};
					//String[] args = { eElement.getAttribute("tipo"),eElement.getAttribute("localizacion")};
//					if (args [0].equals("secuestrador")){
//						
//					}
//					else if(args [0].equals("guardian")){
//
//					}
//					else if(args [0].equals("emboscador")){
//			
//					}
//					else if(args [0].equals("puromal")){
//
//					}
//					else
//						//throwexception;
//						clase = "";
//					
//					
					guest = container.createNewAgent(nombre, "personajes.monstruos."+clase, args);
					guest.start();
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
//		guest = container.createNewAgent("Smaug", "personajes.monstruos.Dragon", null);
//		guest.start();
//		guest = container.createNewAgent("Casper", "personajes.monstruos.Fantasma", null);
//		guest.start();
//		guest = container.createNewAgent("Jormundgander", "personajes.monstruos.Serpiente", null);
//		guest.start();
//		guest = container.createNewAgent("Trundle", "personajes.monstruos.Troll", null);
//		guest.start();
	}
}
