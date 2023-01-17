package com.example.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    final String TAG = "11111";
    SurfaceHolder surfaceHolder;
    Context context;
    Enemigo enemigo;
    Bitmap fondo;
    int resx,resy;
    float accX=0, accY=0;
    int tickFrame;
    long lastTick;
    int frame;
    int frameAccionEnemigo;
    int accionEnemigo=0;
    boolean enemigoEstaHaciendoUnaAccion=false;

    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
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
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mishimadojo),resx,resy,true);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

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
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);


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
    }

    public void actualizaFrame(){
        if(System.currentTimeMillis()-tickFrame>lastTick){
            //todo actualizar al bicho por cada frame que pasa
            frame++;
            frameAccionEnemigo++;//con esto luego cambio los sprites
            if(!enemigoEstaHaciendoUnaAccion) {
                //accionEnemigo = (int)(Math.random()*10);
                accionEnemigo=0;
                enemigoEstaHaciendoUnaAccion=true;
                frameAccionEnemigo=1;
            }

            lastTick=System.currentTimeMillis();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //int accion=event.getAction();
        int accion=event.getActionMasked();

        switch (accion){
            case MotionEvent.ACTION_DOWN:

                enemigo.animacionActual=enemigo.jotaroPunchingAnimation;
                frameAccionEnemigo=0;
                enemigoEstaHaciendoUnaAccion=true;
                break;
        }



        return true;
    }

    public void dibujar(Canvas c){
        //c.drawBitmap(enemigo.pruebaTerry[0],500,500,null);
        c.drawBitmap(fondo,0,0,null);
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
                //todo cambiar como represento las acciones, no todas las puedo hacer en este metodo, tengo que hacer un switch
                //todo y distintos valores de frames, ya que si no embeces empieza un ataque pero no lo continua
                //y cosas raras
                c.drawBitmap(enemigo.animacionActual[frame%enemigo.animacionActual.length],getWidth()/3,getHeight()/3,null);
                if(frameAccionEnemigo%enemigo.animacionActual.length==0){
                    enemigoEstaHaciendoUnaAccion=false;
                    enemigo.animacionActual=enemigo.jotaroStandingAnimation;
                }
                break;
        }
        c.drawBitmap(enemigo.terryStandingAnimation[frame % enemigo.terryStandingAnimation.length], getWidth()*2/3, getHeight()/3, null);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
            //todo: estos valores de x, y fluctuan (mas o menos) entre 0 y 10
            //todo por lo tanto, si supera un umbral(que se podra calibrar desde el menu)-> cambia la accion
            //en java CREO que no se podia hacer un switch case con rangos
            accY = event.values[1];
            //efectivamente, esto cambia las animaciones que se realizan, tendría que
            if(!enemigoEstaHaciendoUnaAccion){


                if(accY>3){
                    enemigo.animacionActual=enemigo.jotaroWalkingForwardAnimation;
                }else if(accY<-3){
                    //walkingBackwards
                    //
                }else{
                    //iddle
                    enemigo.animacionActual=enemigo.jotaroStandingAnimation;
                }
            }
            //todo aqui hacer los eventos de pitch y roll??
            //Toast.makeText(context, accX+" en X,  "+accY+" en Y", Toast.LENGTH_SHORT).show();
            //mi pana esto LO EJECUTA TODO EL RATO, INCLUSO CON EL MOVIL QUIETO???
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
    }

    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "mRotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        // "mOrientationAngles" now has up-to-date information.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    class Enemigo extends Thread{
    Bitmap[] animacionActual;
    Bitmap[] iddleAnimation = new Bitmap[5];
    Bitmap[] punchAnimation = new Bitmap[5];
    Bitmap iddleAnimationFrames; //todo este es el de verdad, los de arriba son pruebas
    Bitmap[] terryStandingAnimation = new Bitmap[11];
    Bitmap[] jotaroStandingAnimation = new Bitmap[24];
    Bitmap[] jotaroWalkingForwardAnimation = new Bitmap[16];
    Bitmap[] jotaroPunchingAnimation = new Bitmap[10];
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

        Bitmap aux;
        this.height=altoPantalla/2;
        this.width=this.height/3;

        terryStandingAnimation[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle1),width,height,true);
        terryStandingAnimation[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle2),width,height,true);
        terryStandingAnimation[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle3),width,height,true);
        terryStandingAnimation[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle4),width,height,true);
        terryStandingAnimation[4]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle5),width,height,true);
        terryStandingAnimation[5]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle6),width,height,true);
        terryStandingAnimation[6]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle7),width,height,true);
        terryStandingAnimation[7]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle8),width,height,true);
        terryStandingAnimation[8]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle9),width,height,true);
        terryStandingAnimation[9]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle10),width,height,true);
        terryStandingAnimation[10]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle11),width,height,true);

        jotaroPunchingAnimation[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch1),width,height,true);
        jotaroPunchingAnimation[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch2),width,height,true);
        jotaroPunchingAnimation[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch3),width,height,true);
        jotaroPunchingAnimation[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch4),width,height,true);
        jotaroPunchingAnimation[4]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch5),width,height,true);
        jotaroPunchingAnimation[5]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch6),width,height,true);
        jotaroPunchingAnimation[6]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch7),width,height,true);
        jotaroPunchingAnimation[7]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch8),width,height,true);
        jotaroPunchingAnimation[8]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch9),width,height,true);
        jotaroPunchingAnimation[9]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch10),width,height,true);


        iddleAnimationFrames=BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro);

        for (int i = 0; i < jotaroStandingAnimation.length; i++) {
            aux=Bitmap.createBitmap(iddleAnimationFrames,i*iddleAnimationFrames.getWidth()/24,0,iddleAnimationFrames.getWidth()/24,iddleAnimationFrames.getHeight());
            jotaroStandingAnimation[i]=Bitmap.createScaledBitmap(aux,width,height,true);
        }
        iddleAnimationFrames=BitmapFactory.decodeResource(context.getResources(),R.drawable.walkingforwardjotaro);
        for (int i = 0; i < jotaroWalkingForwardAnimation.length; i++) {
            aux=Bitmap.createBitmap(iddleAnimationFrames,i*iddleAnimationFrames.getWidth()/16,0,iddleAnimationFrames.getWidth()/16,iddleAnimationFrames.getHeight());
            jotaroWalkingForwardAnimation[i]=Bitmap.createScaledBitmap(aux,width,height,true);
        }
        // y los hago transparente en https://transparent.imageonline.co/

        animacionActual= jotaroStandingAnimation;

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