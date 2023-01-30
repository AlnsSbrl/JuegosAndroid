package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.context;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

public class Personaje {
    Frame[] iddleAnimation;
    Frame[] moveForward;
    Frame[] moveBackwards;
    Frame[] punchAnimation;
    Frame[] kickAnimation;
    Frame[] takingLightDamage;
    Frame[] currentMoveAnimation;

    boolean isDoingAMove;
    boolean isInvulnerable;
    int height = altoPantalla*2/5;
    int width= height*2/3;
    int posX,posY;
    int vida;
    int currentAnimationFrame=0;
    int damageMov=0;
    private int currentAction;
    Rect hurtbox;
    boolean invierteAnimacion; //no se si ponerlo aqui

    public Personaje(int posX, int posY,int vida) {
        setCurrentAction(3);
        this.posX = posX;
        this.posY = posY;
        this.vida=vida;
        isDoingAMove=false;
        isInvulnerable=false;
        hurtbox=new Rect(posX,posY,posX+width,posY+height);
    }

    public void dibuja(Canvas canvas){
        Log.i("frame", "dibuja ca:"+currentAction+" "+(currentAnimationFrame));
        switch (currentAction){
            case 1:
                Log.i("frame", "dibuja: punc"+(currentAnimationFrame));
                canvas.drawBitmap(punchAnimation[currentAnimationFrame% punchAnimation.length].getFrameMov(),posX,posY,null);
                //canvas.drawBitmap(punchAnimation[currentAnimationFrame].getFrameMov(),posX,posY,null);
                //currentAnimationFrame++;
                if(currentAnimationFrame>= 10){
                    setCurrentAction(3);
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
            case 4:
                canvas.drawBitmap(kickAnimation[currentAnimationFrame%kickAnimation.length].getFrameMov(),posX,posY,null);
                if(currentAnimationFrame>= kickAnimation.length){
                    setCurrentAction(3);
                }
                break;
            case 5:
                canvas.drawBitmap(takingLightDamage[currentAnimationFrame%takingLightDamage.length].getFrameMov(),posX,posY,null);
                if(currentAnimationFrame>= takingLightDamage.length){
                    isInvulnerable=false;
                    setCurrentAction(3);
                }
        }
    }
    public boolean golpea(Rect hurtboxEnemigo){
        //if (currentAnimationFrame>=currentMoveAnimation.length) currentAnimationFrame=0;
        Log.i("gol", "golpea: "+(currentMoveAnimation==iddleAnimation));
        damageMov=currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].damage;
        return (currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].esGolpeo && hurtbox.intersect(hurtboxEnemigo));
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(int currentAction) {
        setCurrentAnimation(currentAction);
        this.currentAction = currentAction;
        if(currentAction==3||currentAction==2){
            isDoingAMove=false;
        }else{
            isDoingAMove=true;
        }
        currentAnimationFrame=0;
    }
    public void setCurrentAnimation(int action){
        switch (action){
            case 1:
                currentMoveAnimation=punchAnimation;
                break;
            case 2:
                currentMoveAnimation=moveForward;
                    break;
            case 3:
                currentMoveAnimation=iddleAnimation;
                break;
            case 4:
                currentMoveAnimation=kickAnimation;
                break;
            case 5:
                currentMoveAnimation=takingLightDamage;
                break;
        }
    }
}
