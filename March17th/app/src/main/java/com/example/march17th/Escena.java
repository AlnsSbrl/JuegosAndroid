package com.example.march17th;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Escena {

    int numEscena;//=-1;
    boolean hasFinished;

    /**
     * Escenario de combate
     */
    EscenarioCombate escenario;

    int returnEscene;

    public Escena(int numEscena, EscenarioCombate escenario){
        this.numEscena=numEscena;
        this.hasFinished=false;
        this.escenario=escenario;
        this.escenario.Reproduce();
    }



    public void dibuja(Canvas c){
        c.drawBitmap(escenario.fondo,0,0,null);
    }
    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent e){
        //int x= (int)e.getX();
        //int y=(int)e.getY();

        return this.numEscena;
    }
}
