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

    //todo cambiar el tamaño del texto y la posicion del mismo dependiendo del tamaño del string
    public Boton(Context context, int x, int y, int ancho, int alto, String texto){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.context=context;
        startingPoint=new Point(x,y);
        width=ancho;
        height=alto;
        textoBoton=texto;
        paint.setColor(context.getResources().getColor(R.color.teal_700,null));
        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(75); //todo archivo dimens
        paintTexto.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Dibuja los elementos del botón
     * @param c canvas
     */
    public void dibujar(Canvas c){
        c.drawRect(hitbox,paint);
        c.drawText(textoBoton,hitbox.centerX(),startingPoint.y +(height-paintTexto.getTextSize()/3)-paintTexto.getTextSize(),paintTexto);
        c.save();
    }

    /**
     * Detecta si la pulsacion se ha cometido en las dimensiones del boton
     * @param x coordenada x de la pulsación
     * @param y coordenada y de la pulsación
     * @return true si x,y están dentro del botón, false si no
     */
    public boolean onTouch(int x, int y){
        return hitbox.contains(x,y);
    }
}
