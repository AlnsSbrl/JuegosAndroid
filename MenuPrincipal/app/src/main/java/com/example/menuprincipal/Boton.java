package com.example.menuprincipal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Boton {
    Rect hitbox;
    Context context;
    Point startingPoint;
    String textoBoton;
    int height;
    int width;
    Paint paint = new Paint();
    Paint paintTexto = new Paint();

    public Boton(Context context, int x, int y, int ancho, int alto, String texto){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.context=context;
        startingPoint=new Point(x,y);
        width=ancho;
        height=alto;
        textoBoton=texto;
        paint.setColor(context.getResources().getColor(R.color.teal_700,null));
        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(75);
    }

    public void dibujar(Canvas c){
        c.drawRect(hitbox,paint);
        c.drawText(textoBoton,hitbox.centerX(),hitbox.centerY(),paintTexto);
        c.save();
    }

    public boolean onTouch(int x, int y){
        return hitbox.contains(x,y);
    }
}
