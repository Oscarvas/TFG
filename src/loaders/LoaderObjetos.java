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
		Objeto o = null;
		
			File fXmlFile = new File("Objetos.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nListTipo = doc.getElementsByTagName("tipo");

			for (int i = 0; i < nListTipo.getLength(); i++) {

				Node nNodeTipo = nListTipo.item(i);
				if (nNodeTipo.getNodeType() == Node.ELEMENT_NODE) {

					Element eElementTipo = (Element) nNodeTipo;
					System.out.println();

					NodeList nListObjeto = eElementTipo.getElementsByTagName("objeto");

					for (int j = 0; j < nListObjeto.getLength(); j++) {

						Node nNodeObjeto = nListObjeto.item(j);
						if (nNodeObjeto.getNodeType() == Node.ELEMENT_NODE) {

							Element eElementObjeto = (Element) nNodeObjeto;
							nombre = eElementObjeto.getAttribute("nombre");
							descripcion = eElementObjeto.getAttribute("descripcion");
							switch(eElementTipo.getAttribute("tipo")){
							case "consumible":
								atributos[Objeto.CANTIDAD] = eElementObjeto.getAttribute("cantidad");
								atributos[Objeto.VIDA] = eElementObjeto.getAttribute("vida");
								atributos[Objeto.FUERZA] = eElementObjeto.getAttribute("fuerza");
								atributos[Objeto.DESTREZA] = eElementObjeto.getAttribute("destreza");
								atributos[Objeto.INTELIGENCIA] = eElementObjeto.getAttribute("inteligencia");
								atributos[Objeto.CODICIA] = eElementObjeto.getAttribute("codicia");
								o = new Consumible(nombre, descripcion, atributos);
								break;
							case "clave":
								o = new Clave(nombre, descripcion);
								break;
							}
							
							objetos.añadirObjeto(eElementTipo.getAttribute("tipo"), o);
							
							
						}
					}
				}
			}			
		
		return objetos;
	}
}
