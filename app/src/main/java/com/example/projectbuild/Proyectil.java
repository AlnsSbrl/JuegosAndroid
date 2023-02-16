package com.example.projectbuild;

import static com.example.projectbuild.Constantes.altoPantalla;
import static com.example.projectbuild.Constantes.anchoPantalla;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Proyectil {
    Frame[] projectileAnimation;
    Frame[] hitAnimation;
    Frame[] currentAnimation;
    Rect hitbox;
    int height;// = altoPantalla*2/5;
    int width;//= height*2/3;
    int posX,posY;
    int frame=0;
    int damageMov;
    int speed=anchoPantalla/50;
    boolean isFromPlayer;
    boolean hasFinished=false; //esto para eliminarlo luego de la coleccion, porque no puedo hacerlo en el foreach (creo)
    Paint p;

    public Proyectil(Frame[] animDisp,Frame[] animGolp,boolean isFromPlayer,int posIniX,int posIniY){
        projectileAnimation=animDisp;
        //hitAnimation=animGolp;
        this.isFromPlayer=isFromPlayer;
        currentAnimation=projectileAnimation;
        damageMov=animDisp[0].getDamage();
        this.posX=posIniX;
        this.posY=posIniY;
    }

    public boolean golpea(Rect hurtboxEnemigo){
        damageMov=currentAnimation[frame%currentAnimation.length].damage;
        Log.i("merda", "comprueba que golpea");
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
        p=new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(15);
        canvas.drawBitmap(currentAnimation[frame%currentAnimation.length].getFrameMov(),posX,posY,null);
        //canvas.drawRect(hitbox,p);
    }
}
