package loaders;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import mundo.Mundo;

public class LoaderMontruos {

	public LoaderMontruos(Mundo mundo) throws ControllerException{
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		guest = container.createNewAgent("Smaug", "personajes.Dragon", null);
		guest.start();
		guest = container.createNewAgent("Casper", "personajes.Fantasma", null);
		guest.start();
		guest = container.createNewAgent("Jormundgander", "personajes.Serpiente", null);
		guest.start();
		guest = container.createNewAgent("Trundle", "personajes.Troll", null);
		guest.start();
	}
}
