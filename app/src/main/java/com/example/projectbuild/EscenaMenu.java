package com.example.projectbuild;

import static com.example.projectbuild.Constantes.altoPantalla;
import static com.example.projectbuild.Constantes.anchoPantalla;
import static com.example.projectbuild.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class EscenaMenu extends Escena{

    int numEscena=0;
    Bitmap fondo;
    Boton[] botones=new Boton[5];
    public EscenaMenu(int numEscena) {

        super(numEscena);

        fondo= BitmapFactory.decodeResource(context.getResources(),R.drawable.mishimadojo);
        fondo=Bitmap.createScaledBitmap(fondo,anchoPantalla,altoPantalla,true);
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6,anchoPantalla/2,altoPantalla/10, context.getResources().getString(R.string.FirstMenuOpt),1);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.SecondMenuOpt),2);
        botones[2]= new Boton(anchoPantalla/4,3*altoPantalla/6,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.ThirdMenuOpt),3);
        botones[3]= new Boton(anchoPantalla/4,4*altoPantalla/6,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.FourthMenuOpt),4);
        botones[4]= new Boton(anchoPantalla/4,5*altoPantalla/6,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.FifthMenuOpt),5);
    }

    @Override
    public void dibuja(Canvas canvas) {
        canvas.drawBitmap(fondo,0,0,null);
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
                return b.numEscena;
            }
        }
        return this.numEscena;
    }
}
