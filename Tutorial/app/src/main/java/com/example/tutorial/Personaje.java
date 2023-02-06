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
    boolean isAlive=true;
    int height = altoPantalla*2/5;
    int width= height*2/3;
    int posX,posY;
    int vidaMaxima;
    int vidaActual;
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

        canvas.drawRect(displayVidaActual,pVida);
        pMarcoVida.setColor(Color.WHITE);
        pMarcoVida.setStrokeWidth(15);
        canvas.drawRect(displayTodaLaVida,pMarcoVida);

        canvas.drawBitmap(currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov(),posX,posY,null);
        if(currentAnimationFrame>=currentMoveAnimation.length && currentAction!=3){
                setCurrentAction(3);
        }
    }
    public boolean golpea(Rect hurtboxEnemigo){
        damageMov=currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].damage;
        return (currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].esGolpeo && hurtbox.intersect(hurtboxEnemigo));
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public boolean setVidaActual(int damageTaken) {
        this.vidaActual =vidaActual-damageTaken;
        displayVidaActual = new Rect(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10,altoPantalla/10,(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10)+(vidaActual*anchoPantalla*3/10/vidaMaxima),altoPantalla*2/10);
        Log.i("puta", "esquina derecha: "+(vidaActual/vidaMaxima)*(anchoPantalla*3/10));
        Log.i("puta", "vida actual "+vidaActual+" maxima "+vidaMaxima+" anchoPant "+anchoPantalla);
        if(vidaActual<=0){
            isAlive=false;
        }
        return isAlive;
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
        isInvulnerable=false;
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
                isInvulnerable=true;
                break;
        }
    }
}
