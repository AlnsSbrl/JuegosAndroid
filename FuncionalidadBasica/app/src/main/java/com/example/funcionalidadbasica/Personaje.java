package com.example.funcionalidadbasica;

import android.graphics.Rect;

public class Personaje {
    static int[] acciones= new int[5];
    int posX,posY;
    int altoPersonaje, anchoPersonaje;
    Rect hurtboxPersonaje;
    Rect hitboxMovimiento;
    Rect hitboxMovimientoCritico;

    public Personaje(int anchoPantalla, int altoPantalla) {

        this.posX = anchoPantalla/2;
        this.posY = altoPantalla/2;
        this.altoPersonaje=altoPantalla/2;
        this.anchoPersonaje=altoPersonaje*2/5;
        hurtboxPersonaje=new Rect(this.posX,this.posY,this.posX+this.anchoPersonaje,this.posY+this.altoPersonaje);

    }

    public void Moverse(int movimiento){
        posX+=movimiento;
        ActualizaHitbox();
    }

    public void ActualizaHitbox(){
        hurtboxPersonaje=new Rect(this.posX,this.posY,this.posX+this.anchoPersonaje,this.posY+this.altoPersonaje);
    }


    public void Punch(){
        do{

        }while(true); //condicion de finalizacion de hilo o terminar el numero de frames del mov
    }
    public void Teep(){

    }
    public void Kick(){

    }
}
