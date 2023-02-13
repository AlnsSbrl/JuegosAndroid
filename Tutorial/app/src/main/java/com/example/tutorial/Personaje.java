package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Personaje {
    AccionesPersonaje ac;
    Frame[] iddleAnimation;
    Frame[] moveForward;
    Frame[] moveBackwards;
    Frame[] crouch;
    Frame[] parry;

    Frame[] punchAnimation;
    Frame[] strongPunch;
    Frame[] attackBackwards;
    Frame[] uppercut;
    Frame[] attackForward;
    Frame[] lowKick;
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

        setCurrentAction(ac.IDDLE.getAction());
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
        this.actualizaFisica(currentAction);
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
        if(currentAnimationFrame>=currentMoveAnimation.length && currentAction!=ac.IDDLE.getAction()){
                setCurrentAction(ac.IDDLE.getAction());
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
        if(currentAction==ac.IDDLE.getAction()||currentAction==ac.MOVE_FORWARD.getAction()){
            isDoingAMove=false;
        }else{
            isDoingAMove=true;
        }
        currentAnimationFrame=0;
    }

    public void actualizaFisica(int action){
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case MOVE_FORWARD:
                moverEnX(anchoPantalla/10);
                break;
            case MOVE_BACKWARDS:
                moverEnX(-anchoPantalla/10);
                break;
        }
    }

    public void setCurrentAnimation(int action){
        isInvulnerable=false;
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case PUNCH:
                currentMoveAnimation=punchAnimation;
                break;
            case MOVE_FORWARD:
                currentMoveAnimation=moveForward;
                    break;
            case IDDLE:
                currentMoveAnimation=iddleAnimation;
                break;
            case ATTACK_FORWARD:
                currentMoveAnimation= attackForward;
                break;
            case TAKING_LIGHT_DAMAGE:
                currentMoveAnimation=takingLightDamage;
                isInvulnerable=true;
                break;
            case STRONG_PUNCH:
                currentMoveAnimation=strongPunch;
                break;
            case ATTACK_BACKWARDS:
                currentMoveAnimation=attackBackwards;
                break;
            case UPPERCUT:
                currentMoveAnimation=uppercut;
                break;
            case LOWKICK:
                currentMoveAnimation=lowKick;
                break;
            case PROJECTILE:
                break;
            case CROUCH:
                currentMoveAnimation=crouch;
                break;
            case TAUNT:
                break;
            case MOVE_BACKWARDS:
                break;
        }
        /*(0),(1),(2),(3),(4),(5),(6),
    (7),(8),TAKING_HEAVY_DAMAGE(9),(10),LOSE(11), WIN(12),(13),
    (14),(15);*/
    }

    public int getPosX() {
        return posX;
    }

    public void moverEnX(int posX) {
        if(this.posX+posX<anchoPantalla-width && this.posX+posX>0) {
            this.posX += isPlayer?posX:-posX;
            hurtbox=new Rect(posX,posY,width+posX,height+posY);
        }
    }

    public int getPosY() {
        return posY;
    }

    public void moverEnY(int posY) {
        if(this.posY+posY<altoPantalla+height && this.posY+posY>0){
            hurtbox=new Rect(posX,posY,width+posX,height+posY);
            this.posY += posY;
        }
    }
}
