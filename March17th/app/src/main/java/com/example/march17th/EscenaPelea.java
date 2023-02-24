package com.example.march17th;

import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import static com.example.march17th.Constantes.FPS;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.umbralSensibilidadX;
import static com.example.march17th.Constantes.umbralSensibilidadY;
import static com.example.march17th.Constantes.valorInicialInclinacionX;
import static com.example.march17th.Constantes.valorInicialInclinacionY;
import static com.example.march17th.Constantes.ac;


import androidx.core.view.GestureDetectorCompat;

import java.util.ArrayList;

public class EscenaPelea extends Escena implements SensorEventListener {

    AudioManager audioManager;
    Personaje player;
    Personaje enemy;
    ArrayList<Proyectil> proyectiles;
    int dmgTakenDisplay=0;
    int dmgTakenTextModifier=1;
    int dmgDoneDisplay=0;
    int dmgDoneTextModifier=1;
    private SensorManager sensorManager;
    public GestureDetectorCompat detectorDeGestos;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    float rotacionEnY;
    EscenarioCombate fondo;
    Typeface dmgFont;
    boolean slowHit=false;
    int contadorCambioSegundos=0;
    Scoreboard scoreboard;

    public EscenaPelea(int numEscena /*,Personaje personajeJugador, Personaje personajeEnemigo, EscenarioCombate escenario */) {
        super(numEscena);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dmgFont = context.getResources().getFont(R.font.reallyloveselviadesignpersonaluse);
        }
        returnEscene=0;
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int v=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        umbralSensibilidadX=0.5f;
        umbralSensibilidadY=0.5f;
        valorInicialInclinacionX=0;
        valorInicialInclinacionY=0;
        Log.i("hola", "EscenaPelea: ");
        player = new Terry(anchoPantalla*0,altoPantalla*11/23,500,true);
        enemy = new Ryu(anchoPantalla*2/3,altoPantalla*11/23,500,false);
        scoreboard = new Scoreboard(player.name,enemy.name,false);
        proyectiles= new ArrayList<>();
        fondo = new EscenarioCombate(R.drawable.mishimadojo,R.raw.spearofjustice);
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
            //Toast.makeText(context, "ee "+rotacionEnY, Toast.LENGTH_SHORT).show();
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
     * Detecta las colisiones y actualiza el estado del juego al siguiente frame
     */
    public void actualizaFisica(){
        player.actualizaFisica(player.getCurrentAction());
        enemy.actualizaFisica(enemy.getCurrentAction());

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
        if(player instanceof Terry &&player.getCurrentAction()==ac.LOWKICK.getAction() && player.getCurrentAnimationFrame()==player.throwingProjectileFrame){
            PowerGeyser pr = new PowerGeyser(((Terry) player).powerGeyser,((Terry) player).powerGeyser,true,player.posXProyectil,player.posYProyectil);

            Log.i("geyser", "lo deberia crear: "+((Terry) player).powerGeyser.toString());
            proyectiles.add(pr);
        }
        //todo: checkear si los proyectiles se golpean entre sí
        if(enemy.getCurrentAction()==ac.PROJECTILE.getAction()&&enemy.getCurrentAnimationFrame()==enemy.throwingProjectileFrame){
            Proyectil pr = new Proyectil(enemy.projectile,enemy.projectileFinished,false,enemy.posXProyectil-enemy.width,enemy.posYProyectil);
            proyectiles.add(pr);
        }
        /*if(enemy instanceof Terry &&enemy.getCurrentAction()==ac.LOWKICK.getAction() && enemy.getCurrentAnimationFrame()==enemy.throwingProjectileFrame){
            PowerGeyser pr = new PowerGeyser(((Terry) enemy).powerGeyser,((Terry) enemy).powerGeyser,false,enemy.posXProyectil,enemy.posYProyectil);
            proyectiles.add(pr);
        }*/

        for(Proyectil p: proyectiles){
            p.moverEnX(p.speed);
        }

        for(int i=proyectiles.size();i>0;i--){
            Proyectil pr = proyectiles.get(i-1);
            if(pr.golpea(pr.isFromPlayer?enemy.hurtbox:player.hurtbox)){
                proyectiles.remove(pr);
                if(pr.isFromPlayer && !enemy.currentMoveAnimation[enemy.getCurrentAnimationFrame()].esGolpeo){
                    enemy.setVidaActual(pr.damageMov);
                    enemy.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
                    slowHit=true;
                    dmgDoneDisplay=pr.damageMov;
                    dmgDoneTextModifier=1;
                }else if(!pr.isFromPlayer && !player.currentMoveAnimation[player.getCurrentAnimationFrame()].esGolpeo){
                    player.setVidaActual(pr.damageMov);
                    player.setCurrentAnimation(ac.TAKING_LIGHT_DAMAGE.getAction());
                    slowHit=true;
                    dmgTakenDisplay=pr.damageMov;
                    dmgTakenTextModifier=1;
                }
            }
        }
        //TODO clase enemigo, que contiene un personaje
        if(!enemy.isDoingAMove){
            //tomaDecisionDeLaIA();
        }

        if(enemy.getCurrentAnimationFrame()==enemy.parryFrame && enemy.getCurrentAction()==ac.PROTECT.getAction()){
            enemy.setDeltaCurrentAnimationFrame(-1);
            enemy.isBlocking=true;
        }

        if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY && player.getCurrentAnimationFrame()==player.parryFrame &&player.getCurrentAction()==ac.PROTECT.getAction()){
            player.setDeltaCurrentAnimationFrame(-1);
            player.isBlocking=true;
        }
        contadorCambioSegundos++;
        if(contadorCambioSegundos%FPS==0){
            scoreboard.actualizaTiempo();
        }
        player.setDeltaCurrentAnimationFrame(1);
        enemy.setDeltaCurrentAnimationFrame(1);

        if(enemy.vidaActual<=0||player.vidaActual<=0||scoreboard.currentTime<=0){
            hasFinished=true;
        }
    }

    public void tomaDecisionDeLaIA(){
        //todo QUE DEPENDA DE LA VIDA DE LA GENTE TMB
        boolean isWinning=enemy.vidaActual/enemy.vidaMaxima>player.vidaActual/player.vidaMaxima;
        boolean isCornered=enemy.posX>anchoPantalla*8/10;
        boolean estanSeparados=enemy.posX-enemy.posEnemigo>anchoPantalla/3;
        boolean estanCerca =enemy.posX-enemy.posEnemigo<enemy.width*3/2;
        //y si esta acorralado haga otra cosa tmb
        if(isCornered)
        {//todo aqui PROHIBIDO IR PARA ATRAS, que se me buggea el bicho lmaoo
            if(!isWinning &&estanCerca){
                comportamientoCercanoArrinconado(25); //esto haria que fuese mas probable que atacase
            }else if(isWinning &&estanCerca){
                comportamientoCercanoArrinconado(50); //esto hace que sea mas probable que se defienda
            }else if(isWinning && !estanCerca){
                //todo: spam proyectiles
                comportamientoADistanciaArrinconado(15);
            }else{ //es decir, está perdiendo y no estáis cerca el uno del otro
                //intenta recuperar STAGE Control -> attack backwards/mover adelante NUNCA ATRAS
                comportamientoADistanciaArrinconado(25);
            }
        }else{
            //aqui el enemigo NO está arrinconado
            if(isWinning && !estanCerca){
                comportamientoMantenerDistancia(15);
                //comportamiento spammer y manteniendo distancia
            }else if(!isWinning && !estanCerca){
                //intenta acercarse
                comportamientoADistanciaArrinconado(10);
            }else if(isWinning && estanCerca){
                comportamientoCercano(40); //todo menos bloq, se queda pillado
                //comportamiento algo mas pasivo, incluso se echa para atras
            }else{
                comportamientoCercano(15);//todo tbh me falta incluir más veces el iddle animation
            }
            //comportamientoCercano();
        }

    }
    public void comportamientoCercanoArrinconado(int max){
        int queHacer = (int)(Math.random()*max+1);
        switch (queHacer){
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
                enemy.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                break;
            case 22:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            default:
                if(enemy.getCurrentAction()!=ac.PROTECT.getAction()){
                    enemy.setCurrentAnimation(ac.PROTECT.getAction());
                }
                break;
        }
    }
    public void comportamientoADistanciaArrinconado(int max){
        int accion = (int)(Math.random()*max+1);
        switch (accion){
            case 1:
            case 2:
            case 3:
            case 4:
                enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                break;
            case 5:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            default:
                enemy.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                break;
        }
    }
    public void comportamientoMantenerDistancia(int max){
        int num= (int)(Math.random()*max+1);
        switch (num){
            case 1:
                enemy.setCurrentAnimation(ac.ATTACK_FORWARD.getAction());
                break;
            case 2:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                enemy.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                break;
            case 13:
            default:
                enemy.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                break;
        }
    }
    public void comportamientoCercano(int max){

        int accion = (int)(Math.random()*max+1);
        if(enemy.getCurrentAction()==ac.PROTECT.getAction()){
            accion=(int)(Math.random()*100+1);
        }
        switch (accion){
            case 1:
            case 2:
                enemy.setCurrentAnimation(ac.PUNCH.getAction());
                break;
            case 3:
            case 4:
                enemy.setCurrentAnimation(ac.UPPERCUT.getAction());
                break;

            case 5:
                enemy.setCurrentAnimation(ac.PROJECTILE.getAction());
                break;
            case 6:
            case 7:
                enemy.setCurrentAnimation(ac.STRONG_PUNCH.getAction());
                break;
            case 8:
            case 9:
                enemy.setCurrentAnimation(ac.LOWKICK.getAction());
                break;
            case 10:
            case 11:
                enemy.setCurrentAnimation(ac.ATTACK_FORWARD.getAction());
                break;
            case 12:
            case 13:
                enemy.setCurrentAnimation(ac.ATTACK_BACKWARDS.getAction());
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                enemy.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                break;
            default:
                if(enemy.getCurrentAction()!=ac.PROTECT.getAction()){
                    enemy.setCurrentAnimation(ac.PROTECT.getAction());
                }
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


    public boolean onTouchEvent(MotionEvent event,SurfaceHolder surfaceHolder) {
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
    public void dibuja(Canvas c){
        c.drawBitmap(fondo.fondo,0,0,null);
        player.dibuja(c);
        enemy.dibuja(c);
        //TextPaint p= new TextPaint();
        Paint pDmgDone=new Paint();
        Paint pDmgTaken=new Paint();
        pDmgDone.setTypeface(dmgFont);
        pDmgTaken.setTypeface(dmgFont);
        pDmgDone.setTextSize(dmgDoneTextModifier*30);
        pDmgTaken.setTextSize(dmgTakenDisplay*30);
        pDmgDone.setARGB(255/dmgDoneTextModifier*30,255,255,255);
        pDmgTaken.setARGB(255/dmgTakenTextModifier*30,255,255,255);

        scoreboard.dibuja(c);
        //p.setUnderlineText(true);
        //todo scoreboard class

        //c.drawText((String.valueOf(duracionCombate)),anchoPantalla/2,player.displayMarcoVidaTotal.bottom,p);

        if(enemy.isInvulnerable){
            c.drawText(String.valueOf(dmgDoneDisplay),enemy.posX+enemy.width,enemy.height,pDmgDone);
            dmgDoneTextModifier++;
        }
        if(player.isInvulnerable){
            c.drawText(String.valueOf(dmgTakenDisplay),player.posX,enemy.height,pDmgDone);
            dmgTakenTextModifier++;
        }

        for (Proyectil pr:proyectiles) {
            pr.dibuja(c);
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


