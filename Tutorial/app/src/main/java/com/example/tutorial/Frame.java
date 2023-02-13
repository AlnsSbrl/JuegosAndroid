package com.example.tutorial;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Frame {
    Bitmap frameMov;
    boolean esGolpeo;
    Rect hitbox; //estos son los frames activos del ataque, los que hacen daño


    int damage;//igual esto lo quito de aqui y lo situo en una clase nueva "movimiento"
    int energy;//esto igual que lo de arriba

    public boolean golpea(Rect hitboxRival){
        if(esGolpeo && hitbox.intersect(hitboxRival)) return true;
        return false;
    }

    public Bitmap volteaImagen(Bitmap frameMov,boolean isPlayer){
        Matrix matrix = new Matrix();
        if(isPlayer) matrix.preScale(-1,1);
        else return  frameMov;
        return Bitmap.createBitmap(frameMov,0,0,frameMov.getWidth(),frameMov.getHeight(), matrix,true);
    }

    public Frame(Bitmap frameMov, boolean esGolpeo, boolean isPlayer, int damage, int energy) {
        this.frameMov = volteaImagen(frameMov, isPlayer);
        this.esGolpeo = esGolpeo;
        //this.hitbox = hitbox;
        this.damage = damage;
        this.energy = energy;
    }

    public Bitmap getFrameMov() {
        return frameMov;
    }

    public void setFrameMov(Bitmap frameMov) {
        this.frameMov = frameMov;
    }

    public boolean isEsGolpeo() {
        return esGolpeo;
    }

    public void setEsGolpeo(boolean esGolpeo) {
        this.esGolpeo = esGolpeo;
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
