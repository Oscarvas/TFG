package loaders;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import mundo.Mundo;

public class LoaderPnjs {
	public LoaderPnjs(Mundo mundo) throws ControllerException{
		PlatformController container = mundo.getContainerController();
		
		AgentController guest;
		guest = container.createNewAgent("Thrall", "pnjs.Chaman", null);
		guest.start();
		guest = container.createNewAgent("Kvothe", "pnjs.Tabernero", null);
		guest.start();
		guest = container.createNewAgent("Egidio", "pnjs.Granjero", null);
		guest.start();
		guest = container.createNewAgent("Chicote", "pnjs.Chef", null);
		guest.start();
		guest = container.createNewAgent("Bella", "pnjs.Bibliotecario", null);
		guest.start();
		guest = container.createNewAgent("Felurian", "pnjs.Sastre", null);
		guest.start();
		guest = container.createNewAgent("Garrosh", "pnjs.Herrero", null);
		guest.start();
	}
}
