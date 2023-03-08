package com.example.march17th;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Proyectil especial del personaje Terry
 */
public class PowerGeyser extends Proyectil{

    /**
     * Crea el proyectil y establece sus propiedades según los parámetros
     *
     * @param animDisp     animación mientras se mueve el proyectil
     * @param animGolp     animación tras el impacto
     * @param isFromPlayer indica si lo ha lanzado el jugador
     * @param posIniX      posición inicial del proyectil en el eje X
     * @param posIniY      posición inicial del proyectil en el eje Y
     */
    public PowerGeyser(Frame[] animDisp, Frame[] animGolp, boolean isFromPlayer, int posIniX, int posIniY) {
        super(animDisp, animGolp, isFromPlayer, posIniX, posIniY);
        this.currentAnimation=animDisp;
    }

    /**
     * Impide que se mueva en el eje x como el resto de proyectiles
     * @param posX incremento en el eje X
     * @return si ha acabado de hacer la animación
     */
    @Override
    public boolean moverEnX(int posX) {
        actualizaHitbox();
        this.frame++;
        Log.i("geyser", "mueve: ");

        return frame>=currentAnimation.length;
    }



    /**
     * dibuja al proyectil
     * @param canvas canvas
     */
    @Override
    public void dibuja(Canvas canvas) {
        super.dibuja(canvas);
    }

    @Override
    public boolean golpea(Rect hurtboxEnemigo) {
        //todo arreglar esto para que el power geyser siga la animacion aunque golpee
        damageMov=currentAnimation[frame%currentAnimation.length].damage;
        boolean hit = currentAnimation[frame%currentAnimation.length].esGolpeo && hitbox.intersect(hurtboxEnemigo);
        if(hit&&this.currentAnimation!=hitAnimation){
            this.currentAnimation=hitAnimation;
            return hit;
        }
        //bro que pedazo codigo espaguetti es esto
        return hit;
    }
}
