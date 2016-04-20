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

import objetos.*;

public class LoaderObjetos {
	
	public static Almacen loaderObjetos() throws ParserConfigurationException, SAXException, IOException{
		String nombre;		
		String descripcion;
		Almacen objetos = new Almacen();
		String[] atributos = new String[Objeto.ATRIBUTOS];
		
			File fXmlFile = new File("Objetos.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nListCons = doc.getElementsByTagName("tipo");			

			for (int temp = 0; temp < nListCons.getLength(); temp++) {

				Node nNode = nListCons.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					nombre = eElement.getAttribute("nombre");
					descripcion = eElement.getAttribute("descripcion");
					atributos[Objeto.CANTIDAD] = eElement.getAttribute("cantidad");
					atributos[Objeto.VIDA] = eElement.getAttribute("vida");
					atributos[Objeto.FUERZA] = eElement.getAttribute("fuerza");
					atributos[Objeto.DESTREZA] = eElement.getAttribute("destreza");
					atributos[Objeto.INTELIGENCIA] = eElement.getAttribute("inteligencia");
					atributos[Objeto.CODICIA] = eElement.getAttribute("codicia");
					
					objetos.añadirObjeto("consumible", new Consumible(nombre, descripcion, atributos));
				
				}
			}			
			
			NodeList nListKeys = doc.getElementsByTagName("clave");
			for (int temp = 0; temp < nListKeys.getLength(); temp++) {

				Node nNode = nListKeys.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					nombre = eElement.getAttribute("nombre");
					descripcion = eElement.getAttribute("descripcion");
					
					objetos.añadirObjeto("clave", new Clave(nombre, descripcion));
				}
			}			
		
		return objetos;
	}
}
