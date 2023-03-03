package com.example.march17th;

import static com.example.march17th.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;

public class Boton {

    /**
     * Dimensiones donde el botón detecta que es pulsado
     */
    Rect hitbox;

    /**
     * Texto del botón
     */
    String textoBoton;

    /**
     * Imagen del botón
     */
    Bitmap buttonImage;

    /**
     * número de escena a la que se dirige cuando pulsas el botón
     */
    int numEscena;

    /**
     * Fuente y paint para dibujar el texto
     */
    Paint paintTexto = new Paint();
    Typeface textFont;

    /**
     * Inicia los valores del botón
     * @param x pos x
     * @param y pos y
     * @param ancho ancho
     * @param alto alto
     * @param texto texto
     * @param numEscena escena
     * @param isActive true: pone una imagen azul, false: pone una imagen gris
     */
    public Boton(int x, int y, int ancho, int alto, String texto, int numEscena,boolean isActive){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.numEscena=numEscena;

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
     * @deprecated uso directamente hitbox.contains
     */
    public boolean onTouch(int x, int y){
        return hitbox.contains(x,y);
    }
}
