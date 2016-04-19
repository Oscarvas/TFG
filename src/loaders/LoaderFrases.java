package loaders;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LoaderFrases {
	@SuppressWarnings("unused")
	public LoaderFrases () {
		try{
			File fXmlFile = new File("Frases.xml");
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
						
					int nFrases = eElement.getElementsByTagName("frase").getLength();
					ArrayList<String> frases = new ArrayList<String>();
					for(int i=0;i<nFrases;i++){
						frases.add(eElement
							.getElementsByTagName("frase")
							.item(i).getTextContent());
					}
					String cadena = frases.get(new Random().nextInt(frases.size()));
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
