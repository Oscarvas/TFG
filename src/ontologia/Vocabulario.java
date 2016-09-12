package ontologia;

import java.util.HashMap;
import java.util.Random;

import loaders.LoaderRazas;

public abstract class Vocabulario {
	public static final int STANDBY = -1;
	
	public static final int SALIR = 0;
	public static final int INICIAR_HISTORIA = 1;
	public static final int CREAR_PROTAGONISTA = 2;
	public static final int CREAR_ANTAGONISTA = 3;
	public static final int CREAR_PNJ = 4;
	
	public static final String[] CLASES = {"Allegado","Victima","Aspirante","Ayudante"};
	public static final String[] CLASES_ANT = {"Secuestrador","Emboscador","Ladron","Asesino"};
	public static final String[] TIPOS_LOCALIZACION = {"Pueblo","Bosque","Urbano","Castillo","Guarida","Lago"};
	public static final HashMap<String, Raza> RAZAS = LoaderRazas.loaderRazas();
	
	public static int SALARIO (){
		return  new Random().nextInt(100) + 1;
	}
	public static int SALARIO_REY (){
		return  (new Random().nextInt(100)+ 100) * 100;
	}
	
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
