package com.example.projectbuild;

import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static com.example.projectbuild.Constantes.*;
import androidx.core.view.GestureDetectorCompat;

import java.util.ArrayList;

public class EscenaPelea extends Escena implements SensorEventListener {

    AudioManager audioManager;
    Personaje player;
    Personaje enemy;
    ArrayList<Proyectil> proyectiles;
    int duracionCombate;
    int dmgTakenDisplay=0;
    int dmgTakenTextModifier=1;
    int dmgDoneDisplay=0;
    int dmgDoneTextModifier=1;
    private SensorManager sensorManager;
    GestureDetectorCompat detectorDeGestos;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    float rotacionEnY;
    Fondo fondo;
    Typeface dmgFont;




    public EscenaPelea(int numEscena) {
        super(numEscena);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //dmgFont = getResources().getFont(R.font.reallyloveselviadesignpersonaluse);
        }
        duracionCombate=Constantes.tiempoCombate;
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int v=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        player = new Ryu(anchoPantalla*0,altoPantalla*11/23,10000,true);
        enemy = new Ryu(anchoPantalla*2/3,altoPantalla*11/23,200,false);
        proyectiles= new ArrayList<>();
        //fondo = new Fondo(R.drawable.mishimadojo,R.raw.spearofjustice,v);
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
        detectorDeGestos = new GestureDetectorCompat(context,new MultiTouchHandler());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==TYPE_ROTATION_VECTOR){
            float R[] = new float[9];
            SensorManager.getRotationMatrixFromVector(R,event.values);
            float[] YPR = new float[3];
            SensorManager.getOrientation(R,YPR);
            rotacionEnY = YPR[2]*-1.0f;// este es el valor que servirá para protegerse
            //final float y = YPR[0]*-1.0f; // estos valores varian de 0 a 1.5 mas o menos
            final float rotacionEnX = YPR[1]*-1.0f;// este es el valor que serviria para mover adelante atras en landscape

            if(!player.isDoingAMove){
                if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY){
                    if(player.getCurrentAction()!=ac.PROTECT.getAction()){
                        player.setCurrentAnimation(ac.PROTECT.getAction());
                    }
                }else if(rotacionEnY-valorInicialInclinacionY>umbralSensibilidadY){
                    if(!player.isDoingAMove){
                        player.setCurrentAnimation(ac.CROUCH.getAction());
                    }
                }else if(valorInicialInclinacionX-rotacionEnX>umbralSensibilidadX){

                    if(player.getCurrentAction()!=ac.MOVE_BACKWARDS.getAction()) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                    }
                }else if(rotacionEnX-valorInicialInclinacionX>umbralSensibilidadX){

                    if(player.getCurrentAction()!=ac.MOVE_FORWARD.getAction()) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                    }
                }else if(player.getCurrentAction()!=ac.IDDLE.getAction()){
                    player.setCurrentAnimation(ac.IDDLE.getAction());
                }
                //todo esto es muy muy susceptible a como el usuario sujete el dispositivo, lo mejor sería calibrar
                //todo antes de jugar y jugar con variables del usuario
                //Toast.makeText(context, String.format(" %3f %3f",p,r) , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    /**
     * Clase que gestiona los eventos de pantalla. Le asigna una animación al jugador según el evento.
     */
    class MultiTouchHandler implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if(!player.isDoingAMove || (player.isDoingAMove &&player.getCurrentAction() ==ac.PUNCH.getAction())){
                player.setCurrentAnimation(ac.STRONG_PUNCH.getAction());
            }
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {}

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if(!player.isDoingAMove) {
                player.setCurrentAnimation(ac.PUNCH.getAction());
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            //todo proyectil tipo hadouken, o el power geiser de terry O UN TAUNT QUE TE BOOSTEA EL DAÑO QUE HACES OMG
            if(!player.isDoingAMove) {
                player.setCurrentAnimation(ac.PROJECTILE.getAction());
            }
        }

        @Override
        public boolean onFling(MotionEvent me, MotionEvent me1, float vX, float vY) {

            if(!player.isDoingAMove) {
                if (Math.abs( me.getX()-me1.getX())>Math.abs(me.getY()-me1.getY())) {
                    if(me.getX()<me1.getX()){
                        player.setCurrentAnimation(ac.ATTACK_FORWARD.getAction());
                    }else{
                        player.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                    }
                }else{
                    if(me.getY()<me1.getY()){
                        player.setCurrentAnimation(ac.LOWKICK.getAction());
                    }else{
                        player.setCurrentAnimation(ac.UPPERCUT.getAction());
                    }
                }
            }
            return true;
        }
    }
    }


