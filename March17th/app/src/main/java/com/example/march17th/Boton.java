package com.example.march17th;

import static com.example.march17th.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;

public class Boton {

    Rect hitbox;
    Point startingPoint;
    String textoBoton;
    Bitmap buttonImage;
    int height;
    int width;
    int numEscena;
    Paint paintTexto = new Paint();
    Typeface textFont;


    public Boton(int x, int y, int ancho, int alto, String texto, int numEscena,boolean isActive){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.numEscena=numEscena;
        startingPoint=new Point(x,y);
        width=ancho;
        height=alto;
        textoBoton=texto;
        buttonImage=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),isActive?R.drawable.pngimgcombutton:R.drawable.pngimgcombuttondisabled),ancho,alto,true);
        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(alto*2/3); //todo archivo dimens
        paintTexto.setTextAlign(Paint.Align.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textFont = context.getResources().getFont(R.font.attfshingoproultra);
        }
        paintTexto.setTypeface(textFont);
    }

    /**
     * Dibuja los elementos del botón
     * @param c canvas
     */
    public void dibujar(Canvas c){
        c.drawBitmap(buttonImage,hitbox.left,hitbox.top,null);
        c.drawText(textoBoton,hitbox.centerX(),hitbox.centerY()+paintTexto.getTextSize()/2,paintTexto);
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
