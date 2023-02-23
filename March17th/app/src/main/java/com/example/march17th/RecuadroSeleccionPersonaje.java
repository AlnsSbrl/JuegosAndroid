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

public class RecuadroSeleccionPersonaje {

    //hacer varios y uno con el simbolo random, que sera el preseleccionado
    Rect hitbox;
    Point startingPoint;
    Bitmap buttonImage;
    int height;
    int width;
    int personaje;
    int[] imagenPersonaje= new int[]{R.drawable.ryuagachao,R.drawable.terryiddle1};//todo hacer recorte del cuadro
    Paint p;
    boolean isSelected;

    public RecuadroSeleccionPersonaje(int x, int y, int ancho, int alto,int personaje){
        this.hitbox=new Rect(x,y,x+ancho,y+alto);
        this.personaje=personaje;
        startingPoint=new Point(x,y);
        width=ancho;
        height=alto;
        isSelected=false;
        buttonImage=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),imagenPersonaje[personaje]),ancho,alto,true);
       Paint p = new Paint();
       p.setColor(Color.YELLOW);
    }

    public void dibujar(Canvas c){
        c.drawBitmap(buttonImage,hitbox.left,hitbox.top,null);
        //c.drawText(textoBoton,hitbox.centerX(),hitbox.centerY()+paintTexto.getTextSize()/2,paintTexto);
        if(isSelected){
            c.drawRect(hitbox,p);
        }
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
