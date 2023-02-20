package com.example.march17th;

import static com.example.march17th.Constantes.ac;

import android.graphics.Canvas;

public class PersonajeCalibracion extends Ryu{

    public PersonajeCalibracion(int posX, int posY, int vida, boolean isPlayer) {
        super(posX, posY, vida, isPlayer);
    }

    @Override
    public void actualizaFisica(int action) {
        if(currentAnimationFrame>=currentMoveAnimation.length && currentAction!=ac.IDDLE.getAction()){
            setCurrentAnimation(ac.IDDLE.getAction());
        }
    }

    @Override
    public void dibuja(Canvas canvas) {
        canvas.drawBitmap(currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov(),posX,posY,null);
    }
}
