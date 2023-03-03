package com.example.march17th;

import android.graphics.Canvas;

/**
 * Personaje que se emplea en el menú de calibración para ayudar a visualizar los movimientos que se cambian con el giroscopio
 */
public class PersonajeCalibracion extends Ryu{

    /**
     * Inica los valores según los parámetros
     * @param posX posición en X
     * @param posY posición en Y
     * @param vida vida actual
     * @param isPlayer si lo controla el jugador
     */
    public PersonajeCalibracion(int posX, int posY, int vida, boolean isPlayer) {
        super(posX, posY, vida, isPlayer);
    }

    /**
     * Ahora sólo se gestiona que cuando termine una animación se vuelva al iddle
     * @param action acción que se realiza
     * @apiNote damn parece un método innecesario
     */
    @Override
    public void actualizaFisica(int action) {
        if(currentAnimationFrame>=currentMoveAnimation.length && currentAction!= AccionesPersonaje.IDDLE.getAction()){
            setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
        }
    }

    /**
     * dibuja solamente al personaje
     * @param canvas canvas
     */
    @Override
    public void dibuja(Canvas canvas) {
        canvas.drawBitmap(currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov(),posX,posY,null);
    }
}
