package com.example.tutorial;


import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;
import static com.example.tutorial.Constantes.umbralSensibilidadX;
import static com.example.tutorial.Constantes.umbralSensibilidadY;
import static com.example.tutorial.Constantes.valorInicialInclinacionX;
import static com.example.tutorial.Constantes.valorInicialInclinacionY;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    AccionesPersonaje ac;
    SurfaceHolder surfaceHolder;
    Personaje player;
    Personaje enemy;
    ArrayList<Proyectil> proyectiles;
    DrawingThread hiloDibuja;
    Bitmap fondo; //
    long lastTick;
    long tickTimer;
    int duracionCombate;
    int dmgTakenDisplay=0;
    int dmgTakenTextModifier=1;
    int dmgDoneDisplay=0;
    int dmgDoneTextModifier=1;
    long nanosEnUnSegundo=1000000000;
    long sleepTime=0;//
    private SensorManager sensorManager;
    GestureDetectorCompat detectorDeGestos;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    MediaPlayer mp;
    AudioManager audioManager;
    private boolean pause;
    Typeface dmgFont;
    boolean slowHit=false;
    HashMap<Integer,Integer> accionesRelizadas=new HashMap<>();
     float rotacionEnY;
    public Juego(Context context, Point resolucion) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dmgFont = getResources().getFont(R.font.reallyloveselviadesignpersonaluse);
        }
        Constantes.context=context;
        altoPantalla=resolucion.y;
        anchoPantalla=resolucion.x;
        Constantes.sensibilidadRotacion =3;
        duracionCombate=Constantes.tiempoCombate;
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mp=MediaPlayer.create(context,R.raw.megalovania);
        int v=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mp.setVolume(v/2,v/2);
        //dmgFont = Typeface.createFromAsset(context.getAssets(), "font/reallyloveselviadesignpersonaluse.ttf");
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);
        player = new Ryu(anchoPantalla*1/2,altoPantalla*11/23,100,true);
        enemy = new Terry(anchoPantalla*2/3,altoPantalla*11/23,200,false);
        proyectiles= new ArrayList<>();
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
        detectorDeGestos = new GestureDetectorCompat(context,new MultiTouchHandler());
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if(mp==null) mp=MediaPlayer.create(context,R.raw.battleteamgalacticgrunt8bitremixthezame);
        //mp.start(); //igual condicionarlo a si el usuario quiere musica
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

        if(player.golpea(enemy.hurtbox)&&!enemy.isInvulnerable&&!enemy.isBlocking){
            enemy.setVidaActual(player.damageMov);
            slowHit=true;
            enemy.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
            dmgDoneDisplay=player.damageMov;
            dmgDoneTextModifier=1;
        }

        if(enemy.golpea(player.hurtbox)&&!player.isInvulnerable&&!player.isBlocking){
            player.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
            slowHit=true;
            player.setVidaActual(enemy.damageMov);
            dmgTakenDisplay=enemy.damageMov;
            dmgTakenTextModifier=1;
        }

        if(enemy.golpea(player.hurtbox)&&player.isBlocking){
            player.setCurrentAnimation(ac.IDDLE.getAction());
            slowHit=true;
            enemy.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
            enemy.isInvulnerable=false;
        }

        if(player.golpea(enemy.hurtbox)&&enemy.isBlocking){
            enemy.setCurrentAnimation(ac.IDDLE.getAction());
            slowHit=true;
            player.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
            player.isInvulnerable=false;
            //enemy.isBlocking=false;
        }
        //int rnd = (int)(Math.random()*15);
        //if(rnd<6 && !enemy.isDoingAMove){
          //  enemy.setCurrentAction(4);
        //}
        //todo colision de proyectiles
        for(Proyectil proyectil: proyectiles){
            proyectil.golpea(proyectil.isFromPlayer?enemy.hurtbox:player.hurtbox);
        }

        if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY && player.getCurrentAnimationFrame()==player.parryFrame &&player.getCurrentAction()==ac.PROTECT.getAction()){
            player.setDeltaCurrentAnimationFrame(-1);
            player.isBlocking=true;
        }
        player.setDeltaCurrentAnimationFrame(1);
        enemy.setDeltaCurrentAnimationFrame(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder){
            if(!detectorDeGestos.onTouchEvent(event)){
                int accion=event.getActionMasked();
                switch (accion){
                }
            }
        }
        return true;
    }

    public void dibujar(Canvas c){
        c.drawBitmap(fondo,0,0,null);
        player.dibuja(c);
        enemy.dibuja(c);
        Paint p= new Paint();
        Paint pDmgDone=new Paint();
        Paint pDmgTaken=new Paint();
        pDmgDone.setTypeface(dmgFont);
        pDmgTaken.setTypeface(dmgFont);
        pDmgDone.setTextSize(dmgDoneTextModifier*30);
        pDmgTaken.setTextSize(dmgTakenDisplay*30);
        pDmgDone.setARGB(255/dmgDoneTextModifier*30,255,255,255);
        pDmgTaken.setARGB(255/dmgTakenTextModifier*30,255,255,255);
        p.setTextSize(100);
        p.setColor(Color.WHITE);
        c.drawText((String.valueOf(duracionCombate)),anchoPantalla/2,altoPantalla/10,p);

        if(enemy.isInvulnerable){
            c.drawText(String.valueOf(dmgDoneDisplay),enemy.posX+enemy.width,enemy.height,pDmgDone);
            dmgDoneTextModifier++;
        }
        if(player.isInvulnerable){
            c.drawText(String.valueOf(dmgTakenDisplay),player.posX,enemy.height,pDmgDone);
        }
        if(lastTick-tickTimer>=nanosEnUnSegundo){
            duracionCombate--;
            tickTimer=lastTick;
        }

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

        /*if (false) {
            //event.sensor.getType() == TYPE_ACCELEROMETER
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);

            //todo dejo esto aqui comentado por si mas adelante decido usar el acelerometro como controller
            accY = event.values[1];



                if(accY>sensibilidadAcelerometro){
                  //forward

                }else if(accY<-sensibilidadAcelerometro){
                    //walkingBackwards
                    //
                }else{

                }
            }
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
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if(!player.isDoingAMove) {
                player.setCurrentAnimation(ac.PUNCH.getAction());
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
            /*if(vX>1000&&vX>vY){
                player.setCurrentAction(ac.PROJECTILE.getAction());
            } //sinceramente no me da la vida
            return true;*/
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