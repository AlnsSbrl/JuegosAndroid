package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;
import static com.example.tutorial.Constantes.widthBarraSalud;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Personaje {
    Frame[] iddleAnimation;
    Frame[] moveForward;
    Frame[] moveBackwards;
    Frame[] punchAnimation;
    Frame[] kickAnimation;
    Frame[] takingLightDamage;
    Frame[] currentMoveAnimation;
    Paint pVida;
    Paint pMarcoVida;
    Rect displayTodaLaVida;
    Rect displayVidaActual;
    boolean isPlayer;
    boolean isDoingAMove;
    boolean isInvulnerable;
    int height = altoPantalla*2/5;
    int width= height*2/3;
    int posX,posY;


    public void setVidaActual(int damageTaken) {
        this.vidaActual =vidaActual-damageTaken;
        displayVidaActual = new Rect(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10,altoPantalla/10,(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10)+(vidaActual*anchoPantalla*3/10/vidaMaxima),altoPantalla*2/10);
        Log.i("puta", "esquina derecha: "+(vidaActual/vidaMaxima)*(anchoPantalla*3/10));
        Log.i("puta", "vida actual "+vidaActual+" maxima "+vidaMaxima+" anchoPant "+anchoPantalla);
        if(vidaActual<=0){
            //todo se muere, el rect OBVIO no puede irse para atras ->se acaba el juego
        }
    }

    int vidaMaxima;
    int vidaActual; //todo set y get, en el set actualiza el rect
    int currentAnimationFrame=0;
    int damageMov=0;
    private int currentAction;
    Rect hurtbox;
    boolean invierteAnimacion; //no se si ponerlo aqui

    public Personaje(int posX, int posY,int vida, boolean isPlayer) {
        setCurrentAction(3);
        pVida = new Paint();
        pVida.setStyle(Paint.Style.FILL);
        pMarcoVida = new Paint();
        pMarcoVida.setColor(Color.BLACK);
        pMarcoVida.setStyle(Paint.Style.STROKE);
        this.posX = posX;
        this.posY = posY;
        this.vidaMaxima =vida;
        this.vidaActual=vidaMaxima;
        isDoingAMove=false;
        isInvulnerable=false;
        this.isPlayer=isPlayer;
        displayTodaLaVida = new Rect(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10,altoPantalla/10,isPlayer?anchoPantalla*4/10:anchoPantalla*9/10,altoPantalla*2/10);
        displayVidaActual = new Rect(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10,altoPantalla/10,(vidaActual/vidaMaxima)*(isPlayer?anchoPantalla*4/10:anchoPantalla*9/10),altoPantalla*2/10);
        hurtbox=new Rect(posX,posY,posX+width,posY+height);
    }

    public void dibuja(Canvas canvas){
        int compR =(int)(255-Math.round((vidaActual/(vidaMaxima/2))-1)*255/2);
        if(vidaActual<=vidaMaxima/2){
            compR=255;
        }
        int compG =255;
        if(vidaActual<=vidaMaxima/2){
            compG=0;
        }
        pVida.setARGB(255,compR,compG,0);
        //pVida.setARGB(255,0,255,0);
        //actualizaRect()
        //Color c = new Color();
        canvas.drawRect(displayVidaActual,pVida);
        pMarcoVida.setColor(Color.WHITE);
        pMarcoVida.setStrokeWidth(15);
        canvas.drawRect(displayTodaLaVida,pMarcoVida);
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
