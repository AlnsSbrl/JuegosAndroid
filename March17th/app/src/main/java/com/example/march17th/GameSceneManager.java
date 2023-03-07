package com.example.march17th;

import static android.content.Context.VIBRATOR_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.example.march17th.Constantes.*;
import androidx.annotation.NonNull;

public class GameSceneManager extends SurfaceView implements SurfaceHolder.Callback {

    long lastTick;
    long tickTimer;
    final SurfaceHolder surfaceHolder;
    DrawingThread hiloDibuja;
    long sleepTime=0;//
    boolean slowHit=false;
    AudioManager audioManager;
    Escena escenaActual;
    int nuevaEscena;
    int victoriasPlayer=0;
    int victoriasCPU=0;
    int vicSeguidas;//=rachaActual;


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
        this.surfaceHolder.addCallback(this);
        hiloDibuja =new DrawingThread();
        hiloDibuja.start();
        setFocusable(true);
        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
        vicSeguidas=rachaActual;
        //vibrator=
        escenaActual = new EscenaInicio(EscenasJuego.INICIO.getEscena(), MapaSelector.consigueMenu(Menus.RECORDS.getMenu()));


    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //escenaActual=new EscenaMenu(EscenasJuego.MENU_PRINCIPAL.getEscena(), MapaSelector.consigueMenu(Menus.MENU_PRINCIPAL.getMenu()));
        //escenaActual= new EscenaCreditos(EscenasJuego.CREDITOS.getEscena(), MapaSelector.consigueMenu(Menus.MENU_PRINCIPAL.getMenu()));
        EscenarioCombate e = MapaSelector.consigueMenu(Menus.RECORDS.getMenu());
        escenaActual = new EscenaInicio(EscenasJuego.INICIO.getEscena(), e);
        Log.i("scn", "surfaceCreated: "+e.mp.isPlaying());

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //todo: arreglar aqui cuando sales de la app y vuelves a entrar
        this.escenaActual.escenario.Pausa();
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
                if(escenaActual.getClass()==EscenaCreditos.class){
                    if(!((EscenaCreditos)escenaActual).detectorDeGestos.onTouchEvent(event)){
                        int action=event.getActionMasked();
                        switch (action){

                        }
                    }
                }
                if(escenaActual.getClass()==EscenaEntrenamiento.class){
                    if(!((EscenaEntrenamiento)escenaActual).detectorDeGestos.onTouchEvent(event)){

                        ((EscenaEntrenamiento)escenaActual).onTouchEvent(event);
                    }
                }
            }
        int accion = event.getActionMasked();

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
            escenaActual.escenario.Pausa();
            switch (scn){
                case MENU_PRINCIPAL:
                    escenaActual= new EscenaMenu(0, MapaSelector.consigueMenu(Menus.MENU_PRINCIPAL.getMenu()));
                    victoriasPlayer=0;
                    victoriasCPU=0;
                    break;

                case COMBATE_REAL:
                    int persoPlayer = ((EscenaSeleccionPersonaje)escenaActual).selectedCharacter;
                    int map = ((EscenaSeleccionPersonaje)escenaActual).indexMapa;
                    if(persoPlayer<0) persoPlayer=0;
                    escenaActual = new EscenaPelea(1,MapaSelector.consigueMapa(map),persoPlayer,0);//aqui pasarle dos personajes
                    ((EscenaPelea)escenaActual).scoreboard.playerWins=victoriasPlayer;
                    ((EscenaPelea)escenaActual).scoreboard.cpuWins=victoriasCPU;

                    break;

                case SETTINGS:
                    escenaActual = new EscenaConfiguracion(3,escenaActual instanceof EscenaCalibracionGyro?escenaActual.escenario:MapaSelector.consigueMenu(Menus.OPCIONES.getMenu()));
                    break;

                case CALIBRACION:
                    escenaActual = new EscenaCalibracionGyro(8,escenaActual.escenario);
                    break;

                case ELEGIR_PERSONAJES:
                    Log.i("scn", "cambiaEscena: "+scn);
                    //boolean isTutorial=true;
                    if(escenaActual.getClass()== EscenaPelea.class || (escenaActual instanceof EscenaMenu && !((EscenaMenu) escenaActual).goesToTutorial)) {
                        Log.i("scn", "detecta para poner tutorial: " + scn);

                        if (escenaActual.getClass()== EscenaPelea.class) {
                            if (((EscenaPelea) escenaActual).player.vidaActual < ((EscenaPelea) escenaActual).enemy.vidaActual) {
                                victoriasCPU++;
                                if (vicSeguidas > victoriasConsecutivas) {
                                    victoriasConsecutivas = vicSeguidas;
                                }
                                vicSeguidas = 0;
                            } else {
                                victoriasPlayer++;
                                vicSeguidas++;
                            }
                            totalPlayerWins += victoriasPlayer;
                            totalCPUWins += victoriasCPU;
                            rachaActual = vicSeguidas;
                            guardarValores();
                        }
                        //sera que con el instanceof o el cast hace que no pueda aprovechar
                        escenaActual = new EscenaSeleccionPersonaje(EscenasJuego.ELEGIR_PERSONAJES.getEscena(), MapaSelector.consigueMenu(Menus.CHAR_SELECT.getMenu()), false);

                    }else{
                        escenaActual = new EscenaSeleccionPersonaje(EscenasJuego.ELEGIR_PERSONAJES.getEscena(), MapaSelector.consigueMenu(Menus.CHAR_SELECT.getMenu()),true);
                    }

/*
                    if(escenaActual instanceof EscenaEntrenamiento || (escenaActual instanceof  EscenaMenu && !((EscenaMenu)escenaActual).goesToTutorial)){
                        escenaActual = new EscenaSeleccionPersonaje(EscenasJuego.ELEGIR_PERSONAJES.getEscena(), MapaSelector.consigueMenu(Menus.CHAR_SELECT.getMenu()),true);
                    }*/

                    break;
                case CREDITOS:
                    escenaActual = new EscenaCreditos(EscenasJuego.CREDITOS.getEscena(), MapaSelector.consigueMenu(Menus.CREDITOS.getMenu()));
                    break;
                case TUTORIAL:
                    int personajePlayer = ((EscenaSeleccionPersonaje)escenaActual).selectedCharacter;
                    int mapa = ((EscenaSeleccionPersonaje)escenaActual).indexMapa;
                    if(personajePlayer<0) personajePlayer=0;
                    escenaActual = new EscenaEntrenamiento(1,MapaSelector.consigueMapa(mapa),personajePlayer,0);//aqui pasarle dos personajes
                    break;
            }
        }
    }

    /**
     * Trozo de código pa que no me pete el juego
     * @author Mónica Romero
     */
    public Handler mHandler;
    boolean termina=true;

    /**
     * Hilo que se encarga de dibujar la escena actual al framerate deseado.
     */
    class DrawingThread extends Thread{


        @SuppressLint("HandlerLeak")
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
                    canvas=surfaceHolder.lockCanvas();
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
                        cambiaEscena(escenaActual.returnEscene);
                    }
                }
            //todo aqui iria el control de frames? en plan, lo hago siempre
            }
            Looper.loop();
        }
    }
}
