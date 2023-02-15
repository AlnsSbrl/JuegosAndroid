package com.example.tutorial;

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
    static boolean emplearMusicaFondo=true;
    static boolean emplearSFX=true;

    static float valorInicialInclinacionX; //valor inicial de la orientacion en x del usuario
    static float valorInicialInclinacionY; //valor inicial de la orientacion en y del usuario
    static float umbralSensibilidadX; //valor de la sensibilidad con la que el usuario se siente comodo para que se realice la accion
    static float umbralSensibilidadY; //lo mismo que arriba

}
