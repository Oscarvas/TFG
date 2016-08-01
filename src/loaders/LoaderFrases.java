package loaders;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ontologia.Diccionario;

public class LoaderFrases {

	public static Diccionario loaderFrases() {
		Diccionario frases = new Diccionario();

		File fXmlFile = new File("Frases.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nListPersonajes = doc.getElementsByTagName("personaje");
			for (int i = 0; i < nListPersonajes.getLength(); i++) {

				Node nNodePersonajes = nListPersonajes.item(i);
				if (nNodePersonajes.getNodeType() == Node.ELEMENT_NODE) {

					Element eElementPersonaje = (Element) nNodePersonajes;
					NodeList nListAcciones = eElementPersonaje.getElementsByTagName("accion");

					for (int j = 0; j < nListAcciones.getLength(); j++) {
						Node nNodeAcciones = nListAcciones.item(j);
						if (nNodeAcciones.getNodeType() == Node.ELEMENT_NODE) {
							Element eElementAccion = (Element) nNodeAcciones;
							NodeList nListFrases = eElementAccion.getElementsByTagName("frase");
							for (int k = 0; k < nListFrases.getLength(); k++) {
								Node nNodeFrases = nListFrases.item(k);
								if (nNodeFrases.getNodeType() == Node.ELEMENT_NODE) {
									Element eElementFrase = (Element) nNodeFrases;
									frases.addFraseConPersonaje(eElementPersonaje.getAttribute("tipo"),
											eElementAccion.getAttribute("id"), 
											eElementFrase.getTextContent());
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return frases;
	}
}
