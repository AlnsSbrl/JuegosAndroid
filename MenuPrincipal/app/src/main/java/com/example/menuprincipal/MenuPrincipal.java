package com.example.menuprincipal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    public MenuPrincipal(Context context, Point pantalla) {
        super(context);
        altoPantalla= pantalla.y;
        anchoPantalla=pantalla.x;
        this.context=context;
        fondo=BitmapFactory.decodeResource(getResources(),R.drawable.fondomenuprincipal);
        fondo=Bitmap.createScaledBitmap(fondo,anchoPantalla,altoPantalla,true);
        botones[0]=new Boton(context,anchoPantalla/4,altoPantalla/6,anchoPantalla/2,altoPantalla/10, getResources().getString(R.string.menuTutorial));
        botones[1]= new Boton(context,anchoPantalla/4,2*altoPantalla/6,anchoPantalla/2,altoPantalla/10,getResources().getString(R.string.menuHistoria));
        botones[2]= new Boton(context,anchoPantalla/4,3*altoPantalla/6,anchoPantalla/2,altoPantalla/10,getResources().getString(R.string.menuOpciones));
        botones[3]= new Boton(context,anchoPantalla/4,4*altoPantalla/6,anchoPantalla/2,altoPantalla/10,getResources().getString(R.string.menuRecords));
        botones[4]= new Boton(context,anchoPantalla/4,5*altoPantalla/6,anchoPantalla/2,altoPantalla/10,getResources().getString(R.string.menuCreditos));
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
