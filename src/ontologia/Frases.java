package ontologia;

import java.util.ArrayList;
import java.util.HashMap;

public class Frases {
	//cada map <k,v> = <idFrase , frases>
	public HashMap<String, ArrayList<String>> bibliotecario;
	public HashMap<String, ArrayList<String>> chaman;
	public HashMap<String, ArrayList<String>> chef;
	public HashMap<String, ArrayList<String>> granjero;
	public HashMap<String, ArrayList<String>> herrero;
	public HashMap<String, ArrayList<String>> sastre;
	public HashMap<String, ArrayList<String>> tabernero;
	public HashMap<String, ArrayList<String>> caballero;
	public HashMap<String, ArrayList<String>> druida;
	public HashMap<String, ArrayList<String>> mago;
	public HashMap<String, ArrayList<String>> princesa;
	public HashMap<String, ArrayList<String>> rey;
	public HashMap<String, ArrayList<String>> troll;
	public HashMap<String, ArrayList<String>> dragon;
	public HashMap<String, ArrayList<String>> serpiente;
	public HashMap<String, ArrayList<String>> fantasma;
	
	public HashMap<String, ArrayList<String>> getBibliotecario() {
		return bibliotecario;
	}
	public void setBibliotecario(String id,ArrayList<String> frases) {
		this.bibliotecario.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getChaman() {
		return chaman;
	}
	public void setChaman(String id,ArrayList<String> frases) {
		this.chaman.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getChef() {
		return chef;
	}
	public void setChef(String id,ArrayList<String> frases) {
		this.chef.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getGranjero() {
		return granjero;
	}
	public void setGranjero(String id,ArrayList<String> frases) {
		this.granjero.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getHerrero() {
		return herrero;
	}
	public void setHerrero(String id,ArrayList<String> frases) {
		this.herrero.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getSastre() {
		return sastre;
	}
	public void setSastre(String id,ArrayList<String> frases) {
		this.sastre.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getTabernero() {
		return tabernero;
	}
	public void setTabernero(String id,ArrayList<String> frases) {
		this.tabernero.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getCaballero() {
		return caballero;
	}
	public void setCaballero(String id,ArrayList<String> frases) {
		this.caballero.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getDruida() {
		return druida;
	}
	public void setDruida(String id,ArrayList<String> frases) {
		this.druida.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getMago() {
		return mago;
	}
	public void setMago(String id,ArrayList<String> frases) {
		this.mago.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getPrincesa() {
		return princesa;
	}
	public void setPrincesa(String id,ArrayList<String> frases) {
		this.princesa.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getRey() {
		return rey;
	}
	public void setRey(String id,ArrayList<String> frases) {
		this.rey.put(id, frases);
	}
	public HashMap<String, ArrayList<String>> getTroll() {
		return troll;
	}
	public void setTroll(String id,ArrayList<String> frases) {
		this.troll = troll;
	}
	public HashMap<String, ArrayList<String>> getDragon() {
		return dragon;
	}
	public void setDragon(String id,ArrayList<String> frases) {
		this.dragon = dragon;
	}
	public HashMap<String, ArrayList<String>> getSerpiente() {
		return serpiente;
	}
	public void setSerpiente(String id,ArrayList<String> frases) {
		this.serpiente = serpiente;
	}
	public HashMap<String, ArrayList<String>> getFantasma() {
		return fantasma;
	}
	public void setFantasma(String id,ArrayList<String> frases) {
		this.fantasma = fantasma;
	}
	
}
