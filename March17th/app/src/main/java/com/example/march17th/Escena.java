package com.example.march17th;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Escena {

    int numEscena=-1;
    boolean hasFinished;

    int returnEscene;

    public Escena(int numEscena){
        this.numEscena=numEscena;
        this.hasFinished=false;
    }



    public void dibuja(Canvas c){

    }
    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent e){
        int x= (int)e.getX();
        int y=(int)e.getY();

        return this.numEscena;
    }
}
