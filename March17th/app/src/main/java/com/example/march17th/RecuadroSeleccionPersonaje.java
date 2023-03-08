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

/**
 * Marco para escoger al personaje que se va a usar en las batallas
 */
public class RecuadroSeleccionPersonaje {

    /**
     * Hitbox del recuadro
     */
    Rect hitbox;

    /**
     * Imagen del recuadro
     */
    Bitmap buttonImage;

    /**
     * Dimensiones del recuadro
     */
    int height;
    int width;

    /**
     * Personaje al que representa el recuadro
     */
    int personaje;

    /**
     * Conjunto de las imágenes que se usan para todos los personajes
     */
    int[] imagenPersonaje= new int[]{R.drawable.random,R.drawable.ryuportrait, R.drawable.terryportrait };//todo hacer recorte del cuadro

    /**
     * Pincel para dibujar el marco cuando el personaje está seleccionado
     */
    Paint p;


    /**
     *  Indica si el personaje ha sido seleccionado
     */
    boolean isSelected;

    /**
     * Inicializa los valores según los parámetros
     * @param x posición en X
     * @param y posición en Y
     * @param ancho ancho que ocupa el recuadro
     * @param alto alto que ocupa el recuadro
     * @param personaje personaje al que representa
     */
    public RecuadroSeleccionPersonaje(int x, int y, int ancho, int alto,int personaje){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.personaje=personaje;
        width=ancho;
        height=alto;
        isSelected=false;
        buttonImage=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),imagenPersonaje[personaje]),ancho,alto,true);
       p = new Paint();
       p.setColor(Color.YELLOW);
       p.setStyle(Paint.Style.STROKE);
       p.setStrokeWidth(5);

    }

    /**
     * Dibuja el recuadro y un rectángulo amarillo en el borde, en el caso de que esté seleccionado
     * @param c
     */
    public void dibujar(Canvas c){
        c.drawBitmap(buttonImage,hitbox.left,hitbox.top,null);
        if(isSelected){
            c.drawRect(hitbox,p);
        }
    }

    /**
     * @deprecated uso el hitbox contains directamente
     * Detecta si la pulsacion se ha cometido en las dimensiones del boton
     * @param x coordenada x de la pulsación
     * @param y coordenada y de la pulsación
     * @return true si x,y están dentro del botón, false si no
     */
    public boolean onTouch(int x, int y){
        return hitbox.contains(x,y);
    }
}
