package loaders;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ontologia.Raza;


public class LoaderRazas {

	public static HashMap<String, Raza> loaderRazas() {
		String nombre;
		HashMap<String, Raza> razas = new HashMap<String, Raza>();

		try {
			// indicar el fichero de configuracion y crear el builder
			File fXmlFile = new File("Razas.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			// volcar en una lista de nodos los pnjs que se quieren coger
			NodeList nList = doc.getElementsByTagName("raza");
			// crear un numero n de pnjs a hacer
			for (int n = 0; n < nList.getLength(); n++) {

				Node nNode = nList.item(n);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					nombre = eElement.getAttribute("nombre");
					String[] args = { eElement.getAttribute("vida"), eElement.getAttribute("fuerza"),
							eElement.getAttribute("destreza"), eElement.getAttribute("inteligencia"),
							eElement.getAttribute("codicia") };
					Raza razaAux = new Raza((String) nombre, Integer.parseInt((String) args[0]),
							Integer.parseInt((String) args[1]), Integer.parseInt((String) args[2]),
							Integer.parseInt((String) args[3]), Integer.parseInt((String) args[4]));

					razas.put(nombre, razaAux);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return razas;
	}
}