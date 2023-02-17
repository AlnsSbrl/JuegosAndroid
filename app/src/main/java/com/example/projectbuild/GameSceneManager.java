package com.example.projectbuild;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.projectbuild.Constantes.*;
import androidx.annotation.NonNull;

public class GameSceneManager extends SurfaceView implements SurfaceHolder.Callback {
    long lastTick;
    long tickTimer;
    SurfaceHolder surfaceHolder;
    DrawingThread hiloDibuja;
    long sleepTime=0;//
    boolean slowHit=false;

    long nanosEnUnSegundo=1000000000;
    Escena escenaActual;//=new Escena(0);
    int nuevaEscena;
    public GameSceneManager(Context context, Point resolucion){
        super(context);
        Constantes.context=context;
        altoPantalla=resolucion.y;
        anchoPantalla=resolucion.x;
        Constantes.sensibilidadRotacion =3;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);//todo ver esto
        hiloDibuja =new DrawingThread();
        hiloDibuja.start();
        setFocusable(true);
        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        escenaActual=new EscenaMenu(0);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

            synchronized (surfaceHolder){
                if(escenaActual.getClass()==EscenaPelea.class){
                    if(!((EscenaPelea)escenaActual).detectorDeGestos.onTouchEvent(event)){
                    int accion=event.getActionMasked();
                    switch (accion){
                    }
                }
            }
        }
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int accion = event.getActionMasked();
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                nuevaEscena=escenaActual.onTouchEvent(event);
                cambiaEscena(nuevaEscena);
                return true;
        }

        return super.onTouchEvent(event);
    }

    public void cambiaEscena(int nuevaEscena){
        if(escenaActual.numEscena!=nuevaEscena){
            switch (nuevaEscena){
                case 0:
                    escenaActual= new EscenaMenu(0);
                    break;
                case 1:
                    escenaActual = new EscenaPelea(1);//aqui pasarle dos personajes
            }
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
                        Log.i("cerda", "dibujo escena "+escenaActual.numEscena);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(canvas!=null)surfaceHolder.unlockCanvasAndPost(canvas);
                }
                lastTick+=Constantes.ticksPerFrame;
                Log.i("frames", ""+lastTick);
                sleepTime = lastTick -System.nanoTime();

                if(slowHit) {
                    try {
                        Thread.sleep(200); //esto es como una pausa que se hace cuando hay un golpe, para darle efecto
                        slowHit = false;
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                else if(sleepTime>0){
                    try {
                        Thread.sleep(sleepTime/1000000);

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                if(escenaActual!=null){
                    if(escenaActual.hasFinished){
                        cambiaEscena(escenaActual.returnEscene);
                    }
                }
            //todo aqui iria el control de frames? en plan, lo hago siempre
            }
        }
    }
}
