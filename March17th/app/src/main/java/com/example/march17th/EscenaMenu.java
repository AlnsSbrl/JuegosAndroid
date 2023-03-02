package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.scn;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.view.MotionEvent;

import java.util.Locale;

public class EscenaMenu extends Escena{

    int numEscena=0;
    EscenarioCombate escenario;
    Boton[] botones=new Boton[5];
    boolean goesToTutorial;

    public EscenaMenu(int numEscena, EscenarioCombate escen) {
        super(numEscena,escen);
        escenario = new EscenarioCombate(R.drawable.mishimadojo,R.raw.thezameteamgalacticremastered);
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getResources().getString(R.string.Tutorial).toUpperCase(Locale.ROOT),scn.ELEGIR_PERSONAJES.getEscena(),true);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.PlayAgainstComputer).toUpperCase(Locale.ROOT),scn.ELEGIR_PERSONAJES.getEscena(),true);
        botones[2]= new Boton(anchoPantalla/4,3*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Settings).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),true);
        botones[3]= new Boton(anchoPantalla/4,4*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Records).toUpperCase(Locale.ROOT),4,true);
        botones[4]= new Boton(anchoPantalla/4,5*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Credits).toUpperCase(Locale.ROOT),5,true);

    }

    @Override
    public void dibuja(Canvas canvas) {
        canvas.drawBitmap(escenario.fondo,0,0,null);
        for (Boton boton: botones) {
            boton.dibujar(canvas);
        }
        canvas.save();
        canvas.restore();
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        int x= (int)e.getX();
        int y=(int)e.getY();
        int aux=super.onTouchEvent(e);
        if(aux!=this.numEscena &&aux!=-1){
            return aux;
        }
        for (Boton b:botones) {
            if(b.hitbox.contains(x,y)){

                goesToTutorial= (b== botones[0]);

                return b.numEscena;
            }
        }
        return this.numEscena;
    }
}
