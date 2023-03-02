package com.example.march17th;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.march17th.Constantes.*;
import androidx.annotation.NonNull;

public class GameSceneManager extends SurfaceView implements SurfaceHolder.Callback {
    long lastTick;
    long tickTimer;
    SurfaceHolder surfaceHolder;
    DrawingThread hiloDibuja;
    long sleepTime=0;//
    boolean slowHit=false;
    AudioManager audioManager;//=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    //static int volume;//= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    long nanosEnUnSegundo=1000000000;
    Escena escenaActual;//=new Escena(0);
    int nuevaEscena;
    public GameSceneManager(Context context, Point resolucion){
        super(context);
        Constantes.context=context;
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        volume= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Constantes.leerValores();
        Constantes.setIdioma(Constantes.getIdioma());
        altoPantalla=resolucion.y;
        anchoPantalla=resolucion.x;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);//todo ver esto
        hiloDibuja =new DrawingThread();
        hiloDibuja.start();
        setFocusable(true);
        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
        MapaSelector.Init();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //escenaActual=new EscenaMenu(0);
        //escenaActual=new EscenaCalibracionGyro(8);
        escenaActual = new EscenaSeleccionPersonaje(scn.ELEGIR_PERSONAJES.getEscena(),false);
        //escenaActual= new EscenaConfiguracion(scn.SETTINGS.getEscena());
        //escenaActual=new EscenaPelea(0);


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
                if(escenaActual.getClass()==EscenaSeleccionPersonaje.class){
                    if(!((EscenaSeleccionPersonaje)escenaActual).detectorDeGestos.onTouchEvent(event)){
                        int action=event.getActionMasked();
                        switch (action){

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
            EscenasJuego scn = EscenasJuego.values()[nuevaEscena];
            Log.i("scn", "cambiaEscena: "+scn);

            switch (scn){
                case MENU_PRINCIPAL:
                    escenaActual= new EscenaMenu(0);
                    break;

                case COMBATE_REAL:
                    int persoPlayer = ((EscenaSeleccionPersonaje)escenaActual).selectedCharacter;
                    int map = ((EscenaSeleccionPersonaje)escenaActual).indexMapa;
                    escenaActual = new EscenaPelea(1,persoPlayer,0,MapaSelector.consigueMapa(map));//aqui pasarle dos personajes
                    break;
                case SETTINGS:
                    escenaActual = new EscenaConfiguracion(3);
                    break;
                case CALIBRACION:
                    escenaActual = new EscenaCalibracionGyro(8);
                    break;
                case ELEGIR_PERSONAJES:
                    Log.i("scn", "cambiaEscena: "+scn);
                    //boolean isTutorial=true;
                    if(escenaActual instanceof EscenaPelea || (escenaActual instanceof EscenaMenu && !((EscenaMenu) escenaActual).goesToTutorial)){
                        Log.i("scn", "detecta para poner tutorial: "+scn);
                        //sera que con el instanceof o el cast hace que no pueda aprovechar
                        escenaActual= new EscenaSeleccionPersonaje(scn.ELEGIR_PERSONAJES.getEscena(),false);


                    }

                    /*
                    if(escenaActual instanceof EscenaTutorial || (escenaActual instanceof  EscenaMenu && !((EscenaMenu)escenaActual).goesToTutorial)){
                        escenaActual = new EscenaSeleccionPersonaje(scn.ELEGIR_PERSONAJES.getEscena(),true);
                    }
                    */
            }
        }
    }

    /**
     * Trozo de código pa que no me pete el juego
     * @author Mónica Romero
     */
    public Handler mHandler;
    boolean termina=true;
    class DrawingThread extends Thread{

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    // process incoming messages here
                }
            };
            while(termina){
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
                lastTick+=Constantes.ticksPerFrame;
                sleepTime = lastTick -System.nanoTime();

                if(slowHit) {
                    try {
                        Thread.sleep(250); //esto es como una pausa que se hace cuando hay un golpe, para darle efecto
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
                        escenaActual.escenario.Pausa();
                        cambiaEscena(escenaActual.returnEscene);
                    }
                }
            //todo aqui iria el control de frames? en plan, lo hago siempre
            }
            Looper.loop();
        }
    }
}
