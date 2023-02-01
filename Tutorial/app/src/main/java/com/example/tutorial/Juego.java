package com.example.tutorial;


import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import static com.example.tutorial.Constantes.FPS;
import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;
import static com.example.tutorial.Constantes.umbralSensibilidadX;
import static com.example.tutorial.Constantes.umbralSensibilidadY;
import static com.example.tutorial.Constantes.valorInicialInclinacionX;
import static com.example.tutorial.Constantes.valorInicialInclinacionY;

import androidx.annotation.NonNull;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    SurfaceHolder surfaceHolder;
    Personaje player;
    Personaje enemy;
    DrawingThread hiloDibuja;
    Bitmap fondo; //
    long lastTick;
    long tickTimer;
    int duracionCombate;
    long nanosEnUnSegundo=1000000000;
    long sleepTime=0;//
    private SensorManager sensorManager;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    MediaPlayer mp;
    AudioManager audioManager;
    private boolean pause;


    public Juego(Context context, Point resolucion) {
        super(context);
        Constantes.context=context;
        Constantes.altoPantalla=resolucion.y;
        Constantes.anchoPantalla=resolucion.x;
        Constantes.sensibilidadRotacion =3;
        duracionCombate=Constantes.tiempoCombate;
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mp=MediaPlayer.create(context,R.raw.battleteamgalacticgrunt8bitremixthezame);
        int v=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mp.setVolume(v/2,v/2);
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);
        //la clase que es player, podria ponerla en un switch
        //tambien se podria
        player = new Jotaro(anchoPantalla*2/3,altoPantalla*11/23,100,true);
        enemy = new Terry(anchoPantalla*2/3,altoPantalla*11/23,200,false);
        hiloDibuja =new DrawingThread();
        setFocusable(true);

        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mishimadojo),(int)(anchoPantalla*1.1),(int)(altoPantalla*1.1),true);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor vectorRotacion = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR);

        if(vectorRotacion!=null){
            sensorManager.registerListener(this,vectorRotacion,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }

        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if(mp==null) mp=MediaPlayer.create(context,R.raw.battleteamgalacticgrunt8bitremixthezame);
        mp.start(); //igual condicionarlo a si el usuario quiere musica
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        if (hiloDibuja.getState() == Thread.State.NEW) hiloDibuja.start();
        if (hiloDibuja.getState() == Thread.State.TERMINATED) {
            hiloDibuja = new DrawingThread();
            hiloDibuja.start();
        }
        Sensor vectorRotacion = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR);

        if(vectorRotacion!=null){
            sensorManager.registerListener(this,vectorRotacion,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }

        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        sensorManager.unregisterListener(this);
        //pause=true;
        mp.pause();
    }

    public void actualizaFrame(){

        if(player.golpea(enemy.hurtbox)&&!enemy.isInvulnerable){
            //enemy.isInvulnerable=true;
            enemy.setVidaActual(player.damageMov);
        }
        //int rnd = (int)(Math.random()*15);
        //if(rnd<6 && !enemy.isDoingAMove){
          //  enemy.setCurrentAction(4);
        //}
        player.currentAnimationFrame++;
        enemy.currentAnimationFrame++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //todo configurar el resto de opciones de multitouch
        int accion=event.getActionMasked();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                player.setCurrentAction(1);
                break;
        }
        return true;
    }

    public void dibujar(Canvas c){
        c.drawBitmap(fondo,0,0,null);
        player.dibuja(c);
        enemy.dibuja(c);
        Paint p= new Paint();
        p.setTextSize(100);
        p.setColor(Color.WHITE);
        c.drawText((String.valueOf(duracionCombate)),anchoPantalla/2,altoPantalla/10,p);

        if(lastTick-tickTimer>=nanosEnUnSegundo){
            duracionCombate--;
            tickTimer=lastTick;
            //if(duracionCombate==0) acabar escena
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType()==TYPE_ROTATION_VECTOR){
            float R[] = new float[9];
            SensorManager.getRotationMatrixFromVector(R,event.values);
            float[] YPR = new float[3];
            SensorManager.getOrientation(R,YPR);
            final float rotacionEnY = YPR[2]*-1.0f;//todo este es el valor que servirá para protegerse
            //final float y = YPR[0]*-1.0f; //todo estos valores varian de 0 a 1.5 mas o menos
            final float rotacionEnX = YPR[1]*-1.0f;//todo este es el valor que serviria para mover adelante atras en landscape
            if(!player.isDoingAMove){
                if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY){
                    //todo animacion proteccion hacia arriba
                    //Toast.makeText(context, "ARRIBA", Toast.LENGTH_SHORT).show();
                }else if(rotacionEnY-valorInicialInclinacionY>umbralSensibilidadY){
                    //TODO animacion hacia abajo
                    //Toast.makeText(context, "ABAJO", Toast.LENGTH_SHORT).show();
                }else if(valorInicialInclinacionX-rotacionEnX>umbralSensibilidadX){
                    //TODO esto es ATRAS
                    if(player.posX>player.width){
                        player.posX-=anchoPantalla/(FPS*5);
                        player.hurtbox=new Rect(player.posX,player.posY,player.width+player.posX,player.height+player.posY);
                    }
                }else if(rotacionEnX-valorInicialInclinacionX>umbralSensibilidadX){
                    //TODO ESTO VA HACIA DELANTE
                    if(player.posX<anchoPantalla- player.width){
                        player.posX+=anchoPantalla/(FPS*5);
                        player.hurtbox=new Rect(player.posX,player.posY,player.width+player.posX,player.height+player.posY);
                        //todo esto pasarlo a dentro de la clase, dentro del setPOS

                    }
                    if(player.getCurrentAction()!=2) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAction(2);
                    }
                    //Toast.makeText(context, "FORWARD", Toast.LENGTH_SHORT).show();
                }else if(player.getCurrentAction()!=3){
                    //iddle
                    player.setCurrentAction(3);
                }
                //todo esto es muy muy susceptible a como el usuario sujete el dispositivo, lo mejor sería calibrar
                //todo antes de jugar y jugar con variables del usuario
                //Toast.makeText(context, String.format(" %3f %3f",p,r) , Toast.LENGTH_SHORT).show();
            }
        }

        /*if (false) {
            //event.sensor.getType() == TYPE_ACCELEROMETER
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);

            //todo dejo esto aqui comentado por si mas adelante decido usar el acelerometro como controller
            accY = event.values[1];


            if(!jotaro.isDoingAMove){
                if(accY>sensibilidadAcelerometro){
                    //todo jotaro walking forward
                    jotaro.setCurrentAction(2);
                }else if(accY<-sensibilidadAcelerometro){
                    //walkingBackwards
                    //
                }else{
                    //iddle
                    jotaro.setCurrentAction(3);
                    //todo iddle
                }
            }
            //todo aqui hacer los eventos de pitch y roll??
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }*/

    }

    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        //SensorManager.getRotationMatrix(rotationMatrix, null,
         //       accelerometerReading, magnetometerReading);
        // "mRotationMatrix" now has up-to-date information.
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        // "mOrientationAngles" now has up-to-date information.
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    class DrawingThread extends Thread{

        @Override
        public void run() {
            while(duracionCombate>=0 &&enemy.isAlive &&player.isAlive){
                Canvas c=null;
                try{
                    if(!surfaceHolder.getSurface().isValid())continue;
                    if(c==null) c=surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        actualizaFrame();
                        dibujar(c);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(c!=null)surfaceHolder.unlockCanvasAndPost(c);
                }
                lastTick+=Constantes.ticksPerFrame;
                Log.i("frames", ""+lastTick);
                sleepTime = lastTick -System.nanoTime();
                if(sleepTime>0){
                    try {
                        Thread.sleep(sleepTime/1000000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            //todo hacer aqui las victory/losing screens
            mp.stop(); //esto LO PARA, pero lo de abajo, aunque le ponga un nuevo archivo, lo reanuda???? what
            //mp=MediaPlayer.create(context,R.raw.medievalfanfare);
            //mp.start();
            if(player.vidaActual/player.vidaMaxima>enemy.vidaActual/enemy.vidaMaxima){
                //todo winning
            }else{
                //todo losing
            }
        }
    }
}