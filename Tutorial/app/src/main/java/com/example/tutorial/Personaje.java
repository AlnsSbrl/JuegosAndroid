package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.emplearSFX;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
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
    Frame[] throwingProjectile;
    //todo lo mejor de esto sería condensar los frames+mediaplayer en una clase? pues sí, pero te jodes meu, xa é demasiado tarde
    MediaPlayer mpUpperCut;
    MediaPlayer mpProjectile;
    MediaPlayer mpBackwardsAttack;
    MediaPlayer mpForwardAttack;

    Paint pVida;
    Paint pMarcoVida;
    Rect displayTodaLaVida;
    Rect displayVidaActual;

    boolean isPlayer;
    boolean isDoingAMove;
    boolean isInvulnerable;
    boolean isBlocking;
    boolean isAlive=true;
    int height = altoPantalla*2/5;
    int width= height*2/3;
    int posX,posY;
    int vidaMaxima;
    int vidaActual;
    private int currentAnimationFrame=0;

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }

    public void setDeltaCurrentAnimationFrame(int currentAnimationFrame) {
        this.currentAnimationFrame += currentAnimationFrame;
        actualizaHitbox();
    }

    int damageMov=0;
    int parryFrame;

    private int currentAction;
    Rect hurtbox;
    boolean invierteAnimacion; //no se si ponerlo aqui

    public Personaje(int posX, int posY,int vida, boolean isPlayer) {

        setCurrentAnimation(ac.IDDLE.getAction());
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
                setCurrentAnimation(ac.IDDLE.getAction());
        }
        canvas.drawRect(this.hurtbox,pMarcoVida);
    }
    public boolean golpea(Rect hurtboxEnemigo){
        damageMov=currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].damage;
        return (currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].esGolpeo && hurtbox.intersect(hurtboxEnemigo));
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public boolean setVidaActual(int damageTaken) {
        this.vidaActual =vidaActual-damageTaken<=0?0:vidaActual-damageTaken;
        displayVidaActual = new Rect(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10,altoPantalla/10,(isPlayer?anchoPantalla*1/10:anchoPantalla*6/10)+(vidaActual*anchoPantalla*3/10/vidaMaxima),altoPantalla*2/10);
        Log.i("puta", "esquina derecha: "+(vidaActual/vidaMaxima)*(anchoPantalla*3/10));
        Log.i("puta", "vida actual "+vidaActual+" maxima "+vidaMaxima+" anchoPant "+anchoPantalla);
        if(vidaActual<=0){
            isAlive=false;
        }
        return isAlive;
    }

    public void actualizaFisica(int action){
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case MOVE_FORWARD:
                moverEnX(anchoPantalla/50);
                break;
            case MOVE_BACKWARDS:
                moverEnX(-anchoPantalla/50);
                break;
        }
    }

    /**
     * Cambia el movimiento del personaje, así como las características especiales que puedan tener estos
     * @param action la acción que se va a realizar
     */
    public void setCurrentAnimation(int action){
        currentAnimationFrame=0;
        isDoingAMove=true;
        isInvulnerable=false;
        isBlocking=false;
        this.currentAction = action;
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case PUNCH:
                currentMoveAnimation=punchAnimation;
                break;
            case MOVE_FORWARD:
                currentMoveAnimation=moveForward;
                isDoingAMove=false;
                break;
            case IDDLE:
                currentMoveAnimation=iddleAnimation;
                isDoingAMove=false;
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
                if(mpUpperCut!=null && emplearSFX){
                    mpBackwardsAttack.start();
                }
                break;
            case UPPERCUT:
                currentMoveAnimation=uppercut;
                if(mpUpperCut!=null && emplearSFX){
                    mpUpperCut.start();
                }
                break;
            case LOWKICK:
                currentMoveAnimation=lowKick;
                break;
            case PROJECTILE:
                currentMoveAnimation=throwingProjectile;
                if(mpUpperCut!=null && emplearSFX){
                    mpProjectile.start();
                }
                break;
            case CROUCH:
                currentMoveAnimation=crouch;
                break;
            case TAUNT:
                break;
            case MOVE_BACKWARDS:
                currentMoveAnimation=moveBackwards;
                isDoingAMove=false;
                break;
            case PROTECT:
                currentMoveAnimation=parry;
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
            actualizaHitbox();
        }
    }

    public int getPosY() {
        return posY;
    }

    public void moverEnY(int posY) {
        if(this.posY+posY<altoPantalla+height && this.posY+posY>0){
            this.posY += posY;
            actualizaHitbox();
        }
    }
    public void actualizaHitbox(){
        int ancho = currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov().getWidth();
        int alto = currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov().getHeight();
        hurtbox=new Rect(this.posX,this.posY,ancho+this.posX,alto+this.posY);
    }
}
