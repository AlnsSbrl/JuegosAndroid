package com.example.tutorial;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Frame {
    Bitmap frameMov;
    boolean esGolpeo;
    Rect hitbox; //estos son los frames activos del ataque, los que hacen daño
    int damage;
    int energy;

    public boolean golpea(Rect hitboxRival){
        return true;
    }

    public Frame(Bitmap frameMov, boolean esGolpeo /*,Rect hitbox*/, int damage, int energy) {
        this.frameMov = frameMov;
        this.esGolpeo = esGolpeo;
       // this.hitbox = hitbox;
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
