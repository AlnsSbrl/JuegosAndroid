package com.example.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    final String TAG = "11111";
    SurfaceHolder surfaceHolder;
    Context context;
    Enemigo enemigo;
    int resx,resy;
    int tickFrame;
    long lastTick;
    int frame;
    int frameAccionEnemigo;
    int accionEnemigo=0;
    boolean enemigoEstaHaciendoUnaAccion=false;
    //int currentTime;

    public Juego(Context context, Point resolucion) {
        super(context);
        this.context = context;
        this.surfaceHolder =getHolder();
        this.surfaceHolder.addCallback(this);
        enemigo=new Enemigo(resolucion.x,resolucion.y,context);
        resx=resolucion.x;
        resy=resolucion.y;
        setFocusable(true);
        tickFrame=100;
        lastTick=System.currentTimeMillis();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i(TAG, "run:oo ");
        if (enemigo.getState() == Thread.State.NEW) enemigo.start();
        if (enemigo.getState() == Thread.State.TERMINATED) {
            enemigo = new Enemigo(resx,resy,context);
            enemigo.start();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void actualizaFrame(){
        if(System.currentTimeMillis()-tickFrame>lastTick){
            //todo actualizar al bicho por cada frame que pasa
            frame++;
            frameAccionEnemigo++;//con esto luego cambio los sprites
            if(!enemigoEstaHaciendoUnaAccion) {
                accionEnemigo = (int)(Math.random()*10);
                enemigoEstaHaciendoUnaAccion=true;
                frameAccionEnemigo=1;
            }

            lastTick=System.currentTimeMillis();
        }
    }

    public void dibujar(Canvas c){
        c.drawBitmap(enemigo.pruebaTerry[0],500,500,null);
        switch (accionEnemigo){

            case 1:
                //punch
                c.drawBitmap(enemigo.punchAnimation[(frameAccionEnemigo%enemigo.punchAnimation.length)],0,0,null);

                if(frameAccionEnemigo%enemigo.punchAnimation.length==0){
                    enemigoEstaHaciendoUnaAccion=false;
                }

                break;
            default:
                //iddle
                c.drawBitmap(enemigo.iddleAnimation[frame % enemigo.iddleAnimation.length], 0, 0, null);
                enemigoEstaHaciendoUnaAccion=false;
                break;
        }
    }

class Enemigo extends Thread{

    Bitmap[] iddleAnimation = new Bitmap[5];
    Bitmap[] punchAnimation = new Bitmap[5];
    Bitmap todasLasAnimaciones;
    Bitmap animacionesTerry;
    Bitmap[] pruebaTerry = new Bitmap[1];
    int posx,posy;
    int height, width;
    int selectedFrame=0;
    boolean sigueVivo=true;


    @Override
    public void run() {

        while(sigueVivo){
            Canvas c=null;
            try{
               // Toast.makeText(context, "meLanza", Toast.LENGTH_SHORT).show();

                if(!surfaceHolder.getSurface().isValid())continue;

                if(c==null) c=surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    c.drawColor(Color.BLUE);
                    actualizaFrame(); //todo EQUIVALENTE A ACTUALIZAR FISICA()
                    dibujar(c);
                    //TODO EQUIVALENTE A DIBUJAR()
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(c!=null)surfaceHolder.unlockCanvasAndPost(c);
            }
        }
        //todo el bucle de toma de decisiones, que a veces ataque, a veces se mueva
        //todo otras se defienda...
    }

    public Enemigo(int anchoPantalla,int altoPantalla, Context context){
        todasLasAnimaciones=BitmapFactory.decodeResource(context.getResources(),R.drawable.animaciones);
        animacionesTerry=BitmapFactory.decodeResource(context.getResources(),R.drawable.joeee);

        // y los hago transparente en https://transparent.imageonline.co/

        this.height=altoPantalla/2;
        this.width=this.height/3;
        posx=altoPantalla/2;
        posy=anchoPantalla/2;
        sigueVivo=true;
        Bitmap bAux;
        bAux = Bitmap.createBitmap(animacionesTerry,0,0,animacionesTerry.getWidth()/20,animacionesTerry.getHeight()/20);
        pruebaTerry[0]=Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,0,0,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        iddleAnimation[0] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()/6,0,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        iddleAnimation[1] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*2/6,0,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        iddleAnimation[2] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*3/6,0,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        iddleAnimation[3] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*4/6,0,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        iddleAnimation[4] = Bitmap.createScaledBitmap(bAux,width,height,true);

        bAux= Bitmap.createBitmap(todasLasAnimaciones,0,todasLasAnimaciones.getHeight()*3/4,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        punchAnimation[0] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()*3/4,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        punchAnimation[1] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*2/6,todasLasAnimaciones.getHeight()*3/4,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        punchAnimation[2] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*3/6,todasLasAnimaciones.getHeight()*3/4,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        punchAnimation[3] = Bitmap.createScaledBitmap(bAux,width,height,true);
        bAux= Bitmap.createBitmap(todasLasAnimaciones,todasLasAnimaciones.getWidth()*4/6,todasLasAnimaciones.getHeight()*3/4,todasLasAnimaciones.getWidth()/6,todasLasAnimaciones.getHeight()/4);
        punchAnimation[4] = Bitmap.createScaledBitmap(bAux,width,height,true);
    }
}
}