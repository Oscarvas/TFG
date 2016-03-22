package entorno;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Mapa {

	private ArrayList<Localizacion> localizaciones;

	public Mapa() {
		this.localizaciones = new ArrayList<Localizacion>();
	}

	public Localizacion añadirLocalizacion(String loc) throws Exception {

		if ( !localizaciones.contains(getLocalizacion(loc)) ) {
			Localizacion localizacion = new Localizacion(loc);
			this.localizaciones.add(localizacion);
			return localizacion;
			
		} else
			throw new Exception("Hay localizaciones repetidas");
	}

	public Localizacion getLocalizacion(String loc) {

		Localizacion localizacion = null;

		for (Localizacion localiz : localizaciones) {

			if (loc.equalsIgnoreCase(localiz.getNombre())) {
				localizacion = localiz;
				break;
			}
		}

		return localizacion;
	}
	
	public static Mapa getMapa() {

		Mapa mapa = new Mapa();

		try {
			File fXmlFile = new File("Mapa.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("localizacion");

			Localizacion loc = null;

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					loc = mapa.añadirLocalizacion(eElement.getAttribute("id"));

					String[] cade = eElement
							.getElementsByTagName("conectadoCon").item(0)
							.getTextContent().split(" ");

					for (String conectadoCon : cade) {
						loc.añadirConectado(conectadoCon);
//						estado.añadirAdyacente(loc.getNombre(), conectadoCon);
					}

					if (eElement.getElementsByTagName("esSegura").item(0) != null){}
//						estado.esSegura(loc.getNombre());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return mapa;
	}
	
}
