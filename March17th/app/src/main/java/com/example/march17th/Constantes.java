package com.example.march17th;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * Clase que aglutina a todas las constantes que se emplean en distintas partes del juego, así como la gestión de las shared prefferences
 */
public class Constantes {

    /**
     * Contexto de la aplicaión
     */
    static Context context;
    /**
     * Dimensiones de la pantalla
     */
    static int altoPantalla;
    static int anchoPantalla;
    /**
     * Frames que se dibujan en un segundo y auxiliares para realizar el sleep necesario para conseguir el framerate
     * @apiNote se podría hacer una opción en el menú para ajustarlas como ampliación
     */
    static int FPS=18;//establecer un rango entre 10 y 18 mas o menos (esto parece lo maximo que tira en mi movil)
    static int ticksPerSecond=1000000000;
    static int ticksPerFrame=ticksPerSecond/FPS;

    /**
     * Tiempo máximo que dura una partida
     * @apiNote se podría hacer una opción para cambiarlo en el menú
     */
    static int tiempoCombate=75;

    /**
     * Distintos récords del jugador
     */
    static int currentPlayerWins;
    static int totalPlayerWins;
    static int currentCPUWins;
    static int totalCPUWins;
    static int recordVictoriasConsecutivas;
    static int victoriasConsecutivas;

    /**
     * Ajustes de usuario
     */
    static boolean emplearMusicaFondo=false;
    static boolean emplearSFX=false;
    static boolean emplearVibracion=false; //todo hacer la implementación de esto

    /**
     * Volumen de la música del juego
     */
    static int volume;

    /**
     * Varios enumerados que ayudan a leer mejor el código (de nada)
     * @deprecated android studio me recomienda acceder a los enumerados de forma estática
     */
    static AccionesPersonaje ac;
    static EscenasJuego scn;
    static Mapas map;
    static ListaPersonajes personaje;

    /**
     * Lenguaje del juego
     */
    private static String lang;

    /**
     * Valores del giroscopio configurados por el usuario
     */
    static float valorInicialInclinacionX; //valor inicial de la orientacion en x del usuario
    static float valorInicialInclinacionY; //valor inicial de la orientacion en y del usuario
    static float umbralSensibilidadX; //valor de la sensibilidad con la que el usuario se siente comodo para que se realice la accion
    static float umbralSensibilidadY; //lo mismo que arriba

    /**
     * Valores del giroscopio por defecto
     */
    static float valorDefaultInclinacionX=0.5f;
    static float valorDefaultInclinacionY=0.5f;
    static float umbralDefaultEnX =0.5f;
    static float umbralDefaultEnY=0.5f;


    /**
     * Guarda los valores de las configuraciones de usuario y de los récords
     */
    public static void guardarValores(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("constantes",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("lang",lang);
        editor.putInt("victoriasTotales",totalPlayerWins);
        editor.putInt("derrotasTotales",totalCPUWins);

        if(victoriasConsecutivas>recordVictoriasConsecutivas){
            editor.putInt("recordVictoriasConsecutivas",victoriasConsecutivas);
        }

        editor.putFloat("iniX",valorInicialInclinacionX);
        editor.putFloat("iniY",valorInicialInclinacionY);

        editor.putFloat("senseX",umbralSensibilidadX);
        editor.putFloat("senseY",umbralSensibilidadY);

        editor.putBoolean("musicaFondo",emplearMusicaFondo);
        editor.putBoolean("SFX",emplearSFX);
        editor.putBoolean("vibracion",emplearVibracion);

        editor.commit();
    }

    /**
     * Lee los valores de las configuraciones de usuario y récords en shared preferences
     */
    public static void leerValores(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("constantes",Context.MODE_PRIVATE);
        lang = sharedPreferences.getString("lang","es_ES");
        totalPlayerWins= sharedPreferences.getInt("victoriasTotales",0);
        totalCPUWins = sharedPreferences.getInt("derrotasTotales",0);
        recordVictoriasConsecutivas = sharedPreferences.getInt("recordVictoriasConsecutivas",0);
        valorInicialInclinacionX = sharedPreferences.getFloat("iniX",valorDefaultInclinacionY);
        valorInicialInclinacionY=sharedPreferences.getFloat("iniY",valorDefaultInclinacionY);
        umbralSensibilidadX = sharedPreferences.getFloat("senseX",umbralDefaultEnX);
        umbralSensibilidadY = sharedPreferences.getFloat("senseY",umbralDefaultEnY);
        emplearMusicaFondo = sharedPreferences.getBoolean("musicaFondo",true);
        emplearSFX = sharedPreferences.getBoolean("SFX",true);
        emplearVibracion = sharedPreferences.getBoolean("vibracion",true);
    }

    /**
     * Cambia el idioma
     * @param idioma
     * @author Javier Conde
     */
    public static void setIdioma(String idioma){

        Resources res=context.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        android.content.res.Configuration conf=res.getConfiguration();
        conf.locale=new Locale(idioma.toLowerCase());
        conf.setLocale(new Locale(idioma.toLowerCase()));
        lang=idioma;
        Log.i("idioma", "setIdioma: "+lang);
        res.updateConfiguration(conf, dm);
    }
    public static String getIdioma(){
        return lang;
    }
}
