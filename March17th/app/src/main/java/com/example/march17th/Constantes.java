package com.example.march17th;

import android.content.Context;

public class Constantes {
    static Context context;
    static int altoPantalla;
    static int anchoPantalla;
    static int FPS=18;//establecer un rango entre 10 y 18 mas o menos (esto parece lo maximo que tira en mi movil)
    static int ticksPerSecond=1000000000;
    static int ticksPerFrame=ticksPerSecond/FPS;
    static int sensibilidadRotacion;
    static int tiempoCombate=75;
    static int widthBarraSalud=anchoPantalla*3/10;
    static int heightBarraSalud=altoPantalla/10;
    static int currentPlayerWins=0;
    static int totalPlayerWins;
    static int currentCPUWins=0;
    static int totalCPUWins;
    static boolean emplearMusicaFondo=false;
    static boolean emplearSFX=false;
    static boolean emplearVibracion=false;
    static int volume;
    static AccionesPersonaje ac;
    static EscenasJuego scn;
    static Mapas map;

    static float valorInicialInclinacionX=0.5f; //valor inicial de la orientacion en x del usuario
    static float valorInicialInclinacionY=0.5f; //valor inicial de la orientacion en y del usuario
    static float umbralSensibilidadX=0.5f; //valor de la sensibilidad con la que el usuario se siente comodo para que se realice la accion
    static float umbralSensibilidadY=0.5f; //lo mismo que arriba

    static float valorDefaultInclinacionX=0.5f;
    static float valorDefaultInclinacionY=0.5f;
    static float umbralDeultEnX=0.5f;
    static float umbralDefaultEnY=0.5f;
}
