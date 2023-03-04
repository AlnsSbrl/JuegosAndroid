package com.example.march17th;

import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

public class TextoCreditos {

    int posX;//igual sudo de esto y lo pongo todo en el centro de la pantalla
    int posY;
    String texto;
    Paint p;
    Typeface fuente;
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

    public void dibujar(Canvas c){
        c.drawText(texto,anchoPantalla/2,posY,p);
    }

    public void mover(boolean faster){
        int desplazamiento=5;
        if(faster){
            desplazamiento=10;
        }
        this.posY-=desplazamiento;
    }
}
