package ontologia;

import java.util.Random;

public interface Vocabulario {
	public static final int STANDBY = -1;
	
	public static final int SALIR = 0;
	public static final int CREAR_AGENTE = 1;
	public static final int INICIAR_HISTORIA = 2;
	public static final String[] RAZAS = {"EGERIAN","ILLIDAR","TRAG","LUCS","TESQ","SCRULL"};
	public static final String[] RAZAS_REY = {"LUCS","TESQ"};
	public static final String[] CLASES = {"Rey","Princesa","Caballero","Mago","Druida","Villano"};
	public static final String[] CASTILLOS = {"Ignis","Segrex"};
	public static final String[] LAGOS = {"Inibi","Echidna"};
	public static final String[] BOSQUES = {"Caligo","Sagren","Nordrassil","Illunis"};
	public static final String[] PUEBLOS = {"Egeria","Turis","Illuc","Tragus","Tesqua","Egestas","Lucta","Scruthor"};
	public static final int SALARIO = 40;
	public static final int SALARIO_REY = 100;
	public static int VIDA_MONSTRUO = new Random().nextInt(100 + 1) + 15;
	public static int NUM_HIJAS = new Random().nextInt(11);
}
