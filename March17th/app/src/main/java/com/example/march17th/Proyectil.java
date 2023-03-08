package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Representa la base del proyectil lanzado por cada personaje
 */
public class Proyectil {

    /**
     * animaciones
     */
    Frame[] projectileAnimation;
    Frame[] hitAnimation;
    Frame[] currentAnimation;
    /**
     * Dimensiones del proyectil
     */
    Rect hitbox;

    /**
     * posición del proyectil
     */
    int posX,posY;

    /**
     * frame actual del movimiento
     */
    int frame=0;

    /**
     * daño que realiza el movimiento
     */
    int damageMov;

    /**
     * Velocidad a la que se mueve el proyectil
     */
    int speed=anchoPantalla/50;

    /**
     * Indica si lo ha lanzado el jugador o el enemigo
     */
    boolean isFromPlayer;


    boolean hasFinished=false;

    Paint p;

    /**
     * Crea el proyectil y establece sus propiedades según los parámetros
     * @param animDisp animación mientras se mueve el proyectil
     * @param animGolp animación tras el impacto
     * @param isFromPlayer indica si lo ha lanzado el jugador
     * @param posIniX posición inicial del proyectil en el eje X
     * @param posIniY posición inicial del proyectil en el eje Y
     */
    public Proyectil(Frame[] animDisp,Frame[] animGolp,boolean isFromPlayer,int posIniX,int posIniY){
        projectileAnimation=animDisp;
        //hitAnimation=animGolp;
        this.isFromPlayer=isFromPlayer;
        currentAnimation=projectileAnimation;
        damageMov=animDisp[0].getDamage();
        this.posX=posIniX;
        this.posY=posIniY;
        actualizaHitbox();
    }

    /**
     * Indica si el proyectil ha impactado con otra hurtbox
     * @param hurtboxEnemigo la otra hitbox
     * @return true si hay impacto, false si no
     */
    public boolean golpea(Rect hurtboxEnemigo){
        damageMov=currentAnimation[frame%currentAnimation.length].damage;
        return (currentAnimation[frame%currentAnimation.length].esGolpeo && hurtboxEnemigo.contains(hitbox));//tiene que estar _todo_ el proyectil dentro del personaje pa que detecte (visualmente quedaba feo el otro método de detección, desaparecía el proyectil antes de tocar al personaje)
    }

    /**
     * Actualiza el valor de la hitbox del proyectil a los valores actuales de posición
     */
    public void actualizaHitbox(){
        int ancho = currentAnimation[frame%currentAnimation.length].getFrameMov().getWidth();
        int alto = currentAnimation[frame%currentAnimation.length].getFrameMov().getHeight();
        hitbox=new Rect(this.posX,this.posY,ancho+this.posX,alto+this.posY);
    }

    /**
     * mueve el proyectil dentro de los confines posibles y en el caso de que el movimiento fuese imposible cambia el valor de la booleana para eliminarlo en la colección
     * @param posX incremento en el eje X
     */
    public boolean moverEnX(int posX) {
        if(this.posX+posX<anchoPantalla-this.hitbox.width() && this.posX+posX>0) {
            this.posX += isFromPlayer?posX:-posX;
            actualizaHitbox();
        }
        if(this.posX==0||this.posX>=anchoPantalla){
            hasFinished=true;
            return false;
        }
        return true;
    }

    /**
     * dibuja la animación actual en el frame actual
     * @param canvas
     */
    public void dibuja(Canvas canvas){
        p=new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(15);
        canvas.drawBitmap(currentAnimation[frame%currentAnimation.length].getFrameMov(),posX,posY,null);
        //canvas.drawRect(hitbox,p);
    }
}
