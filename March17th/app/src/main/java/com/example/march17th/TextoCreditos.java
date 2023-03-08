package com.example.march17th;

import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

/**
 * Clase que contiene las propiedades de los textos que se muestran en los créditos
 */
public class TextoCreditos {

    /**
     * @deprecated
     * Posición del texto en el eje X
     */
    int posX;//no lo uso, pongo todos los textos en el centro de la pantalla

    /**
     * Posición del texto en el eje Y
     */
    int posY;

    /**
     * Texto que se muestra por pantalla
     */
    String texto;

    /**
     * Pincel que dibuja al texto
     */
    Paint p;

    /**
     * Fuente del texto
     */
    Typeface fuente;

    /**
     * Inicializa los valores según los parámetros
     * @param posY posición  inicial del texto en el eje Y
     * @param recursoTexto recurso de strings que muestra el texto
     * @param size tamaño del texto
     * @param color color del texto
     */
    public  TextoCreditos(int posY, int recursoTexto, int size, int color){
        this.posY=posY;
        texto = String.valueOf(context.getResources().getString(recursoTexto));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fuente= context.getResources().getFont(R.font.attfshingoproultra);
        }
        p=new Paint();
        p.setTypeface(fuente);
        p.setColor(color);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(size);
    }

    /**
     * Dibuja el texto centrado en pantalla en la posición que tiene en el eje Y
     * @param c canvas
     */
    public void dibujar(Canvas c){
        c.drawText(texto,anchoPantalla/2,posY,p);
    }

    /**
     * Mueve la posición del texto hacia arriba en una cantidad determinada
     * @param faster true: mueve más rápido
     */
    public void mover(boolean faster){
        int desplazamiento=faster?10:5;
        this.posY-=desplazamiento;
    }
}
