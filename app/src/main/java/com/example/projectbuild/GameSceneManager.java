package com.example.projectbuild;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.projectbuild.Constantes.*;
import androidx.annotation.NonNull;

public class GameSceneManager extends SurfaceView implements SurfaceHolder.Callback {
    long lastTick;
    long tickTimer;
    AccionesPersonaje ac;
    SurfaceHolder surfaceHolder;
    DrawingThread hiloDibuja;
    int contadorCambioSegundos=0;
    long sleepTime=0;//
    boolean slowHit=false;

    long nanosEnUnSegundo=1000000000;
    Escena escenaActual=new Escena(2);

    public GameSceneManager(Context context, Point resolucion){
        super(context);
        Constantes.context=context;
        altoPantalla=resolucion.y;
        anchoPantalla=resolucion.x;
        Constantes.sensibilidadRotacion =3;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);//todo ver esto
        hiloDibuja =new DrawingThread();
        setFocusable(true);
        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        escenaActual=new EscenaMenu();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void cambiaEscena(int nuevaEscena){
        if(escenaActual.numEscena!=nuevaEscena){

            escenaActual=new Escena(nuevaEscena);

        }
    }

    class DrawingThread extends Thread{
        @Override
        public void run() {
            while(true){
                Canvas canvas=null;
                try {
                    if(!surfaceHolder.getSurface().isValid())continue;
                    if(canvas==null) canvas=surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder){
                        escenaActual.actualizaFisica();
                        escenaActual.dibuja(canvas);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(canvas!=null)surfaceHolder.unlockCanvasAndPost(canvas);
                }
            //todo aqui iria el control de frames? en plan, lo hago siempre
            }
        }
    }
}
