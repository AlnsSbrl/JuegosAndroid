package com.example.menuprincipal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MenuPrincipal extends View {

    private Context context;
    Bitmap fondo;
    int anchoPantalla;
    int altoPantalla;
    Boton[] botones=new Boton[5];

    //todo cambio el constructor para que le pase el ancho y alto de pantalla,
    //todo ya que se consiguen en el main activity (tema 4)->evita nullpointer y hago todo mas bonito
    public MenuPrincipal(Context context) {
        super(context);
        this.context=context;
        fondo=BitmapFactory.decodeResource(getResources(),R.drawable.fondomenuprincipal);
        //fondo=fondo.createScaledBitmap(fondo,anchoPantalla,altoPantalla,true);
        botones[0]=new Boton(context,100,10,500,400,"tutorial");
        botones[1]= new Boton(context,100,510,500,400,"Historia");
        botones[2]= new Boton(context,100,1010,500,400,"Opciones");
        botones[3]= new Boton(context,100,1510,500,400,"Records");
        botones[4]= new Boton(context,100,2010,500,400,"Créditos");


        //fondo= BitmapFactory.decodeResource(getResources(),R.drawable.fondomenuprincipal);
        //fondo=Bitmap.createScaledBitmap(fondo,700,1000,false);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(fondo,0,0,null);
        for (Boton boton: botones) {
            boton.dibujar(canvas);
        }
        canvas.save();
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        anchoPantalla=w;
        altoPantalla=h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int ejeXClick=(int)event.getX();
        int ejeYClick=(int)event.getY();

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                for(Boton boton: botones){
                    if(boton.onTouch(ejeXClick,ejeYClick)){
                        Toast.makeText(context, boton.textoBoton, Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onTouchEvent(event);
    }



}
