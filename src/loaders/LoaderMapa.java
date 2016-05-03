//package loaders;
//
//import java.io.File;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import mundo.Estado;
//import mundo.Localizacion;
//import mundo.Mapa;
//
//public class LoaderMapa {
//	private Mapa mapa;
//	private Estado estado;
//	private String nombre;
//
//	public LoaderMapa() {
//		this.estado = new Estado();
//		this.mapa = cargarMapa();//Mapa.getMapa(this.estado);
//	}
//	
//	public Mapa cargarMapa() {
//
//		mapa = new Mapa();
//
//		try {
//			File fXmlFile = new File("Mapa.xml");
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(fXmlFile);
//
//			doc.getDocumentElement().normalize();
//
//			NodeList nList = doc.getElementsByTagName("localizacion");
//
//			Localizacion loc = null;
//
//			for (int temp = 0; temp < nList.getLength(); temp++) {
//
//				Node nNode = nList.item(temp);
//
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//
//					Element eElement = (Element) nNode;
//
//					loc = mapa.añadirLocalizacion(eElement.getAttribute("id"), eElement.getAttribute("tipo"));
//					estado.añadirNombre(eElement.getAttribute("nombre"));
//
//					String[] cade = eElement
//							.getElementsByTagName("conectadoCon").item(0)
//							.getTextContent().split(" ");
//
//					for (String conectadoCon : cade) {
//						loc.añadirConectado(conectadoCon);
//						estado.añadirAdyacente(loc.getNombre(), conectadoCon);
//						estado.añadirNombre(conectadoCon);
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//
//		return mapa;
//	}
//}
