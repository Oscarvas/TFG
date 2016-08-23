package ontologia;

import java.util.HashMap;
import java.util.Random;

import loaders.LoaderRazas;

public interface Vocabulario {
	public static final int STANDBY = -1;
	
	public static final int SALIR = 0;
	public static final int CREAR_AGENTE = 1;
	public static final int INICIAR_HISTORIA = 2;
	public static final String[] RAZAS = {"EGERIAN","ILLIDAR","TRAG","LUCS","TESQ","SCRULL"};
	public static final String[] RAZAS_REY = {"LUCS","TESQ"};
	public static final String[] CLASES = {"Rey","Victima","Aspirante","Ayudante","Druida"};
	public static final String[] TIPOS_LOCALIZACION = {"Pueblo","Bosque","Urbano","Castillo","Guarida","Lago"};
	public static final int SALARIO = 40;
	public static final int SALARIO_REY = 100;
	public static final HashMap<String, Raza> RAZAS2 = LoaderRazas.loaderRazas();
	
	
	public static int VIDA_MONSTRUO (){
		return  new Random().nextInt(100 + 1) + 15;
	}
	public static int NUM_HIJAS(){
		return  new Random().nextInt(11);
	}
	
	public static int LOC_ASESINO(){
		return  new Random().nextInt(TIPOS_LOCALIZACION.length);
	}
	
}
