package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Proyectil {
    Frame[] projectileAnimation;
    Frame[] hitAnimation;
    Frame[] currentAnimation;
    Rect hitbox;
    int height;// = altoPantalla*2/5;
    int width;//= height*2/3;
    int posX,posY;
    int frame;
    int damageMov;
    int speed=anchoPantalla/50;
    boolean isFromPlayer;
    boolean hasFinished=false; //esto para eliminarlo luego de la coleccion, porque no puedo hacerlo en el foreach (creo)
    Paint p;

    public boolean golpea(Rect hurtboxEnemigo){
        damageMov=currentAnimation[frame%currentAnimation.length].damage;
        return (currentAnimation[frame%currentAnimation.length].esGolpeo && hitbox.intersect(hurtboxEnemigo));
    }

    public void actualizaHitbox(){
        int ancho = currentAnimation[frame%currentAnimation.length].getFrameMov().getWidth();
        int alto = currentAnimation[frame%currentAnimation.length].getFrameMov().getHeight();
        hitbox=new Rect(this.posX,this.posY,ancho+this.posX,alto+this.posY);
    }
    public void moverEnX(int posX) {
        if(this.posX+posX<anchoPantalla-width && this.posX+posX>0) {
            this.posX += isFromPlayer?posX:-posX;
            actualizaHitbox();
        }
    }

    public void dibuja(Canvas canvas){
        moverEnX(speed);
        p=new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(15);
        canvas.drawBitmap(currentAnimation[frame%currentAnimation.length].getFrameMov(),posX,posY,null);
        canvas.drawRect(hitbox,p);
    }
}
