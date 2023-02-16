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

import static com.example.tutorial.Constantes.FPS;
import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;
import static com.example.tutorial.Constantes.emplearMusicaFondo;
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
    long lastTick;
    long tickTimer;
    int duracionCombate;
    int dmgTakenDisplay=0;
    int dmgTakenTextModifier=1;
    int dmgDoneDisplay=0;
    int dmgDoneTextModifier=1;
    long nanosEnUnSegundo=1000000000;
    int contadorCambioSegundos=0;
    long sleepTime=0;//
    private SensorManager sensorManager;
    GestureDetectorCompat detectorDeGestos;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    AudioManager audioManager;
    private boolean pause;
    Typeface dmgFont;
    boolean slowHit=false;
    HashMap<Integer,Integer> accionesRelizadas=new HashMap<>();
    float rotacionEnY;
    Fondo fondo;

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
        int v=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        this.surfaceHolder=getHolder();
        this.surfaceHolder.addCallback(this);
        player = new Ryu(anchoPantalla*0,altoPantalla*11/23,10000,true);
        enemy = new Ryu(anchoPantalla*2/3,altoPantalla*11/23,200,false);
        proyectiles= new ArrayList<>();
        hiloDibuja =new DrawingThread();
        setFocusable(true);
        lastTick=System.nanoTime();
        tickTimer=System.nanoTime();
        fondo = new Fondo(R.drawable.mishimadojo,R.raw.spearofjustice,v);
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
        if(fondo.mp==null) fondo.mp=MediaPlayer.create(context,R.raw.battleteamgalacticgrunt8bitremixthezame);
        if(emplearMusicaFondo) fondo.mp.start();
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
        if(emplearMusicaFondo) fondo.mp.pause();
    }

    /**
     * Detecta las colisiones y actualiza el estado del juego al siguiente frame
     */
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
        }

        if(player.getCurrentAction()==ac.PROJECTILE.getAction()&&player.getCurrentAnimationFrame()==player.throwingProjectileFrame){
            Proyectil pr = new Proyectil(player.projectile,player.projectileFinished,true,player.posXProyectil,player.posYProyectil);
            proyectiles.add(pr);
        }
        //todo: checkear si los proyectiles se golpean entre sí
        if(enemy.getCurrentAction()==ac.PROJECTILE.getAction()&&enemy.getCurrentAnimationFrame()==enemy.throwingProjectileFrame){
            Proyectil pr = new Proyectil(enemy.projectile,enemy.projectileFinished,false,enemy.posXProyectil-enemy.width,enemy.posYProyectil);
            proyectiles.add(pr);
        }

        for (Proyectil pr:proyectiles) {
            pr.moverEnX(pr.speed);
        }

        for(int i=proyectiles.size();i>0;i--){
            Proyectil pr = proyectiles.get(i-1);
            if(pr.golpea(pr.isFromPlayer?enemy.hurtbox:player.hurtbox)){
                proyectiles.remove(pr);
                if(pr.isFromPlayer){
                    enemy.setVidaActual(pr.damageMov);
                    enemy.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
                }else{
                    player.setVidaActual(pr.damageMov);
                    player.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
                }
                slowHit=true;
                dmgDoneDisplay=pr.damageMov;
                dmgDoneTextModifier=1;
            }
        }

        if(!enemy.isDoingAMove){
            tomaDecisionDeLaIA();
        }

        if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY && player.getCurrentAnimationFrame()==player.parryFrame &&player.getCurrentAction()==ac.PROTECT.getAction()){
            player.setDeltaCurrentAnimationFrame(-1);
            player.isBlocking=true;
        }
        contadorCambioSegundos++;
        player.setDeltaCurrentAnimationFrame(1);
        enemy.setDeltaCurrentAnimationFrame(1);
    }

    public void tomaDecisionDeLaIA(){
        //todo QUE DEPENDA DE LA VIDA DE LA GENTE TMB
        boolean isWinning=enemy.vidaActual/enemy.vidaMaxima>player.vidaActual/player.vidaMaxima;
        boolean isCornered=enemy.posX>anchoPantalla*8/10;
        boolean estanSeparados=enemy.posX-enemy.posEnemigo>anchoPantalla/3;
        boolean estanCerca =enemy.posX-enemy.posEnemigo<enemy.width*3/2;
        //y si esta acorralado haga otra cosa tmb
        if(isCornered)
        {
            if(!isWinning &&estanCerca){
                //todo salir de esquina SUPER AGRESIVO
            }else if(isWinning &&estanCerca){
                //todo: superDEFENSIVO
            }else if(isWinning && !estanCerca){
                //todo: spam proyectiles, PERO no se aleja
            }else{
                //todo: intenta recuperar STAGE Control -> attack backwards/mover adelante NUNCA ATRAS
            }
        }else{
            //aqui el enemigo NO está arrinconado
            if(isWinning && !estanCerca){
                //comportamiento spammer y manteniendo distancia
            }
            comportamientoCercano();
        }
            comportamientoMiddleDistance();
            //mas raro que haga un movimiento, pero lo hace a "reaccion"

    }
    public void comportamientoADistancia(){
        int accion = (int)(Math.random()*25+1);
        if(enemy.vidaActual/enemy.vidaMaxima>player.vidaActual/player.vidaMaxima){
            accion=(int)(Math.random()*20+1);
        }
        switch (accion){
            case 1:
            case 2:
            case 3:
            case 4:
                enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                enemy.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                break;
            case 10:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                enemy.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                break;
            default:
                break;
        }
    }

    public void comportamientoCercano(){

        int accion = (int)(Math.random()*25+1);
        if(enemy.getCurrentAction()==ac.PROTECT.getAction()){
            accion=(int)(Math.random()*100+1);
        }
        switch (accion){
            case 1:
            case 2:
            case 3:
                enemy.setCurrentAnimation(ac.UPPERCUT.getAction());
                break;
            case 4:
                enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                break;
            case 5:
            case 6:
            case 7:
                enemy.setCurrentAnimation(ac.PUNCH.getAction());
                break;
            case 8:
            case 9:
            case 10:
                enemy.setCurrentAnimation(ac.STRONG_PUNCH.getAction());
                break;
            case 11:
            case 12:
                enemy.setCurrentAnimation(ac.LOWKICK.getAction());
                break;
            case 13:
            case 14:
            case 15:
                enemy.setCurrentAnimation(ac.ATTACK_FORWARD.getAction());
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                enemy.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                break;
            case 22:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            default:
                enemy.setCurrentAnimation(ac.PROTECT.getAction());
                break;
        }
    }

    public void comportamientoMiddleDistance(){
        int r = (int)(Math.random()*10+1);
        boolean vaGanando = enemy.vidaActual/enemy.vidaMaxima>player.vidaActual/player.vidaMaxima;
        switch (r){
            case 1:
                if(player.isBlocking){
                    enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                }
                else if(player.getCurrentAction()==ac.MOVE_FORWARD.getAction()){
                    int rndAction = (int)(Math.random()*7+1);
                    enemy.setCurrentAnimation(rndAction);
                }else if(player.isDoingAMove){
                    enemy.setCurrentAnimation(ac.PROTECT.getAction());
                }
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                enemy.setCurrentAnimation(vaGanando?ac.MOVE_BACKWARDS.getAction():ac.MOVE_FORWARD.getAction());
                break;
            case 7:
            case 8:
                enemy.setCurrentAnimation(vaGanando?ac.MOVE_FORWARD.getAction():ac.MOVE_BACKWARDS.getAction());
                break;
            case 9:
            case 10:
                break;

        }
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

    /**
     * Método que dibuja los elementos del juego en pantalla
     * @param c
     */
    public void dibujar(Canvas c){
        c.drawBitmap(fondo.fondo,0,0,null);
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

        for (Proyectil pr:proyectiles) {
            pr.dibuja(c);
        }
        /*if(lastTick-tickTimer>=nanosEnUnSegundo){
            duracionCombate--;
            tickTimer=lastTick;
        }*/

    }

    /**
     * Gestor del giroscopio. Establece la animación del personaje en función del evento
     * @param event
     */
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
                        if(contadorCambioSegundos%FPS==0){
                            duracionCombate--;
                        }
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
            fondo.mp.stop(); //esto LO PARA, pero lo de abajo, aunque le ponga un nuevo archivo, lo reanuda???? what
            //mp=MediaPlayer.create(context,R.raw.medievalfanfare);
            //mp.start();
            if(player.vidaActual/player.vidaMaxima>enemy.vidaActual/enemy.vidaMaxima){
                //todo winning
            }else{
                //todo losing
            }
        }
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