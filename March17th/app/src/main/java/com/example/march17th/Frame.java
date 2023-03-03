package com.example.march17th;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Representa la unidad mínima de movimiento de un personaje, con las propiedades intrínsecas a cada frame
 */
public class Frame {

    /**
     * representación visual del movimiento
     */
    Bitmap frameMov;

    /**
     * indica si esta posición concreta es una hitbox activa, en la que hace daño al enemigo
     */
    boolean esGolpeo;

    /**
     * @deprecated dimensiones del frame del personaje (se puede ampliar para hacer que cada movimiento tenga una hitbox especifica)
     */
    Rect hitbox;

    /**
     * daño que produciría el ataque de haber una colisión
     */
    int damage;


    /**
     * @deprecated en teoría era una propiedad que te consumía un recurso, pero no se emplea
     */
    int energy;

    /**
     * Crea una imagen con la orientación deseada
     * @param frameMov bitmap de la imagen original
     * @param isPlayer si la imagen representa al personaje que va a usar el jugador le da la vuelta
     * @return la imagen dada la vuelta si es jugador, la imagen sin darle la vuelta si es el enemigo
     */
    public Bitmap volteaImagen(Bitmap frameMov,boolean isPlayer){
        Matrix matrix = new Matrix();
        if(isPlayer) matrix.preScale(-1,1);
        else return  frameMov;
        return Bitmap.createBitmap(frameMov,0,0,frameMov.getWidth(),frameMov.getHeight(), matrix,true);
    }

    /**
     * Crea el frame
     * @param frameMov imagen del movimiento
     * @param esGolpeo es una parte del movimiento que hace daño
     * @param isPlayer la imagen pertenece al jugador
     * @param damage daño que realiza el movimiento
     * @param energy coste de energía que usa el movimiento
     */
    public Frame(Bitmap frameMov, boolean esGolpeo, boolean isPlayer, int damage, int energy) {
        this.frameMov = volteaImagen(frameMov, isPlayer);
        this.esGolpeo = esGolpeo;
        this.damage = damage;
        //this.energy = energy;
    }

    public Bitmap getFrameMov() {
        return frameMov;
    }

    public int getDamage() {
        return damage;
    }
}
