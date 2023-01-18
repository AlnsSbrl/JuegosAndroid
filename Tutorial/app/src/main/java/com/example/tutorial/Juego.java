package com.example.tutorial;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_GYROSCOPE;
import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

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
import android.widget.Toast;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;
import static com.example.tutorial.Constantes.sensibilidadAcelerometro;
import static com.example.tutorial.Constantes.tickFrame;
import static com.example.tutorial.Constantes.umbralSensibilidadX;
import static com.example.tutorial.Constantes.umbralSensibilidadY;
import static com.example.tutorial.Constantes.valorInicialInclinacionX;
import static com.example.tutorial.Constantes.valorInicialInclinacionY;

import androidx.annotation.NonNull;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    final String TAG = "11111";
    SurfaceHolder surfaceHolder;
    Jotaro jotaro;
    Terry terry;
    Enemigo enemigo;
    Bitmap fondo;

    float accX=0, accY=0;

    long lastTick;
    int frame;
    int frameAccionEnemigo;
    int accionEnemigo=0;
    int accionPersonaje=0;

    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    //int currentTime;

    public Juego(Context context, Point resolucion) {
        super(context);
        Constantes.context=context;
        Constantes.altoPantalla=resolucion.y;
        Constantes.anchoPantalla=resolucion.x;
        Constantes.sensibilidadAcelerometro=3;
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        this.surfaceHolder =getHolder();
        this.surfaceHolder.addCallback(this);
        jotaro = new Jotaro(anchoPantalla/3,altoPantalla/3,100);
        //terry = new Terry(anchoPantalla*2/3,altoPantalla/3,100);
        enemigo=new Enemigo();
        setFocusable(true);
        tickFrame=150;
        lastTick=System.currentTimeMillis();
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mishimadojo),(int)(anchoPantalla*1.1),(int)(altoPantalla*1.1),true);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor giros = sensorManager.getDefaultSensor(TYPE_GYROSCOPE);
        Sensor vectorRotacion = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR);
        Sensor accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        if(vectorRotacion!=null){
            sensorManager.registerListener(this,vectorRotacion,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        if (giros != null) {
            sensorManager.registerListener(this, giros,
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
            enemigo = new Enemigo();
            enemigo.start();
        }
        Sensor accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
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

            //todo actualizar al bicho por cada frame que pasa
            frame++;
         //   jotaro.currentAnimationFrame++;//con esto luego cambio los sprites

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int accion=event.getActionMasked();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                jotaro.setCurrentAction(1);
                break;
        }
        return true;
    }

    public void dibujar(Canvas c){
        c.drawBitmap(fondo,0,0,null);
        jotaro.dibuja(c);
        //terry.dibuja(c);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
/*
        if(event.sensor.getType()==TYPE_ROTATION_VECTOR){
            float R[] = new float[9];
            SensorManager.getRotationMatrixFromVector(R,event.values);
            float[] YPR = new float[3];
            SensorManager.getOrientation(R,YPR);
            final float rotacionEnY = YPR[2]*-1.0f;//todo este es el valor que servirá para protegerse
            //final float y = YPR[0]*-1.0f; //todo estos valores varian de 0 a 1.5 mas o menos
            final float rotacionEnX = YPR[1]*-1.0f;//todo este es el valor que serviria para mover adelante atras en landscape


            if(System.currentTimeMillis()-tickFrame>lastTick && !jotaro.isDoingAMove){

                if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY){
                    //todo animacion proteccion hacia arriba
                    Toast.makeText(context, "ARRIBA", Toast.LENGTH_SHORT).show();
                }else if(rotacionEnY-valorInicialInclinacionY>umbralSensibilidadY){
                    //TODO animacion hacia abajo
                    Toast.makeText(context, "ABAJO", Toast.LENGTH_SHORT).show();
                }else if(valorInicialInclinacionX-rotacionEnX>umbralSensibilidadX){
                    //TODO esto es ATRAS
                    Toast.makeText(context, "BACKWARDS", Toast.LENGTH_SHORT).show();
                }else if(rotacionEnX-valorInicialInclinacionX>umbralSensibilidadX){
                    //TODO ESTO VA HACIA DELANTE
                //    jotaro.setCurrentAction(2);
                    Toast.makeText(context, "FORWARD", Toast.LENGTH_SHORT).show();
                }else if(jotaro.getCurrentAction()!=3){
                    //iddle
                   // jotaro.setCurrentAction(3);
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
            //todo: estos valores de x, y fluctuan (mas o menos) entre 0 y 10
            //todo por lo tanto, si supera un umbral(que se podra calibrar desde el menu)-> cambia la accion
            //en java CREO que no se podia hacer un switch case con rangos
            accY = event.values[1];
            Log.i(TAG, "zzonSensorChanged: "+event.values[0]+" "+event.values[1]+" "+event.values[2]+" ");

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

    boolean sigueVivo=true;


    @Override
    public void run() {

        while(sigueVivo){
            Canvas c=null;
            try{
                if(!surfaceHolder.getSurface().isValid())continue;
                if(c==null) c=surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if(System.currentTimeMillis()-tickFrame>lastTick){
                        actualizaFrame();
                        dibujar(c);
                        lastTick=System.currentTimeMillis();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(c!=null)surfaceHolder.unlockCanvasAndPost(c);
            }
        }
    }

}
}