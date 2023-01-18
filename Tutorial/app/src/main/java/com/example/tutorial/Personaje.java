package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Personaje {
    Frame[] iddleAnimation;
    Frame[] moveForward;
    Frame[] moveBackwards;
    Frame[] punchAnimation;
    boolean isDoingAMove;
    int height = altoPantalla*2/5;
    int width= height*2/3;
    int posX,posY;
    int vida;
    int currentAnimationFrame=0;
    private int currentAction;
    Rect hurtbox;
    boolean invierteAnimacion; //no se si ponerlo aqui

    public Personaje(int posX, int posY,int vida) {
        currentAction=1;
        this.posX = posX;
        this.posY = posY;
        this.vida=vida;
        isDoingAMove=false;
    }

    public void dibuja(Canvas canvas){

        switch (currentAction){
            case 1:
                Log.i("frame", "dibuja: punc"+(currentAnimationFrame));
               // canvas.drawBitmap(punchAnimation[currentAnimationFrame% punchAnimation.length].getFrameMov(),posX,posY,null);
                canvas.drawBitmap(punchAnimation[currentAnimationFrame].getFrameMov(),posX,posY,null);
                currentAnimationFrame++;
                if(currentAnimationFrame>= 10){
                    currentAnimationFrame=0;
                }
                break;
            case 2:
                Log.i("frame", "dibuja forw: "+(currentAnimationFrame% moveForward.length));
                //todo poner aqui el cambio de valor de x(que dependerá de la orientacion, de hacia donde mira el personaje)
                canvas.drawBitmap(moveForward[currentAnimationFrame% moveForward.length].getFrameMov(),posX,posY,null);
                if(currentAnimationFrame>= moveForward.length){
                    setCurrentAction(3);
                }
                break;
            case 3:
                Log.i("frame", "dibuja iddl: "+(currentAnimationFrame% iddleAnimation.length));
                canvas.drawBitmap(iddleAnimation[currentAnimationFrame% iddleAnimation.length].getFrameMov(),posX,posY,null);
                break;
        }
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
        if(currentAction==3){
            isDoingAMove=false;
        }else{
            isDoingAMove=true;
        }
        currentAnimationFrame=0;
    }
}
