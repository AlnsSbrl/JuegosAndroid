package com.example.march17th;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import static com.example.march17th.Constantes.FPS;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.emplearSFX;
import static com.example.march17th.Constantes.emplearVibracion;
import static com.example.march17th.Constantes.umbralSensibilidadX;
import static com.example.march17th.Constantes.umbralSensibilidadY;
import static com.example.march17th.Constantes.valorInicialInclinacionX;
import static com.example.march17th.Constantes.valorInicialInclinacionY;
import androidx.core.view.GestureDetectorCompat;
import java.util.ArrayList;

/**
 * Escena de un combate contra la IA
 */
public class EscenaPelea extends Escena implements SensorEventListener {

    /**
     * Personaje manejado por el jugador
     */
    Personaje player;
    /**
     * Personaje manejado por la IA
     */
    Personaje enemy;

    /**
     * Colección de proyectiles que se lanzan durante el juego
     */
    ArrayList<Proyectil> proyectiles;

    /**
     * El número que aparece en pantalla cuando golpean al jugador
     */
    int dmgTakenDisplay=0;
    /**
     * El número que muestra el daño que se recibe se hace grande y desvane, esto modifica el tamaño y la transparencia
     */
    int dmgTakenTextModifier=1;

    /**
     * El número que aparece en pantalla cuando el jugador hace daño
     */
    int dmgDoneDisplay=0;
    /**
     * Modificador de tamaño y transparencia del número en cuestión
     */
    int dmgDoneTextModifier=1;


    /**
     * Clase que detecta los gestos de la pantalla
     */
    public GestureDetectorCompat detectorDeGestos;
    /**
     * Valor devuelto por el giroscopio en el eje Y
     * @apiNote se hace valor de clase para hacer comprobaciones en actualizaFisica()
     */
    float rotacionEnY;



    /**
     * Fuente no nativa de android para mostrar el daño
     */
    Typeface dmgFont;

    /**
     * Efecto que ralentiza el combate cuando hay un golpeo
     */
    boolean slowHit=false;

    /**
     * variable auxiliar para cambiar el display que muestra el tiempo de combate
     */
    int contadorCambioSegundos=0;

    /**
     * Marco que muestra el tiempo y las victorias actuales de jugador e IA
     */
    Scoreboard scoreboard;

    Vibrator vibrator;//

    MediaPlayer mpParry;

    public EscenaPelea(int numEscena,EscenarioCombate escenario){
        super(numEscena,escenario);
    }

    /**
     * Inicia los valores según los parámetros
     * @param numEscena identificador escena actual
     * @param escenario el fondo y la música que se emplean
     * @param personajeJugador número que indica qué personaje es el jugador
     * @param personajeEnemigo número que indica qué personaje es el enemigo
     */
    public EscenaPelea(int numEscena,EscenarioCombate escenario, int personajeJugador, int personajeEnemigo) {
        this(numEscena,escenario);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dmgFont = context.getResources().getFont(R.font.reallyloveselviadesignpersonaluse);
        }
        ListaPersonajes perso = ListaPersonajes.values()[personajeJugador];
        if(personajeJugador== ListaPersonajes.RANDOM.getPersonaje()){
            int max=ListaPersonajes.values().length-1;
            personajeJugador = (int)(Math.random()*max+1);
            perso=ListaPersonajes.values()[personajeJugador];
        }
        switch (perso){
            case TERRY:
                player = new Terry(anchoPantalla/3,altoPantalla*11/23,500,true);
                break;
            case RYU:
                player = new Ryu(anchoPantalla/3,altoPantalla*11/23,500,true);
                break;
            default:
                Log.i("scn", "CUIDADO EL RANDOM TIENE MAL EL RANGO ");
                break;
        }
        mpParry= MediaPlayer.create(context,R.raw.parry);
        perso = ListaPersonajes.values()[personajeEnemigo];
        if(personajeEnemigo== ListaPersonajes.RANDOM.getPersonaje()){
            int max=ListaPersonajes.values().length-1;
            personajeEnemigo = (int)(Math.random()*max+1);
            perso=ListaPersonajes.values()[personajeEnemigo];
        }
        switch (perso){
            case TERRY:
                enemy = new Terry(anchoPantalla*2/3,altoPantalla*11/23,500,false);
                break;
            case RYU:
                enemy = new Ryu(anchoPantalla*2/3,altoPantalla*11/23,500,false);
                break;
            default:
                Log.i("scn", "CUIDADO EL RANDOM TIENE MAL EL RANGO ");
                break;
        }

        returnEscene= EscenasJuego.ELEGIR_PERSONAJES.getEscena();
        scoreboard = new Scoreboard(player.name,enemy.name,false);
        proyectiles= new ArrayList<>();
        SensorManager sensorManager;
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
        if(Build.VERSION.SDK_INT>=26){
            vibrator = (Vibrator)context.getSystemService(VIBRATOR_SERVICE);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==TYPE_ROTATION_VECTOR){
            float []R = new float[9];
            SensorManager.getRotationMatrixFromVector(R,event.values);
            float[] YPR = new float[3];
            SensorManager.getOrientation(R,YPR);
            rotacionEnY = YPR[2]*-1.0f;// este es el valor que servirá para protegerse
            //final float y = YPR[0]*-1.0f; // estos valores varian de 0 a 1.5 mas o menos
            final float rotacionEnX = YPR[1]*-1.0f;// este es el valor que serviria para mover adelante atras en landscape
            //Toast.makeText(context, "ee "+rotacionEnY, Toast.LENGTH_SHORT).show();
            if(!player.isDoingAMove){
                if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY){
                    if(player.getCurrentAction()!= AccionesPersonaje.PROTECT.getAction()){
                        player.setCurrentAnimation(AccionesPersonaje.PROTECT.getAction());
                    }
                }else if(rotacionEnY-valorInicialInclinacionY>umbralSensibilidadY){
                    if(player.currentAction!=AccionesPersonaje.CROUCH.getAction()){
                        player.setCurrentAnimation(AccionesPersonaje.CROUCH.getAction());
                    }
                }else if(valorInicialInclinacionX-rotacionEnX>umbralSensibilidadX){

                    if(player.getCurrentAction()!= AccionesPersonaje.MOVE_BACKWARDS.getAction()) {
                        player.setCurrentAnimation(AccionesPersonaje.MOVE_BACKWARDS.getAction());
                    }
                }else if(rotacionEnX-valorInicialInclinacionX>umbralSensibilidadX){

                    if(player.getCurrentAction()!= AccionesPersonaje.MOVE_FORWARD.getAction()) {
                        player.setCurrentAnimation(AccionesPersonaje.MOVE_FORWARD.getAction());
                    }
                }else if(player.getCurrentAction()!= AccionesPersonaje.IDDLE.getAction()){
                    player.setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
                }
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
            enemy.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
            dmgDoneDisplay=player.damageMov;
            dmgDoneTextModifier=1;
            player.damageBoost=1;
            if(emplearVibracion) vibrator.vibrate(250);

        }

        if(enemy.golpea(player.hurtbox)&&!player.isInvulnerable&&!player.isBlocking){
            player.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
            slowHit=true;
            player.setVidaActual(enemy.damageMov);
            dmgTakenDisplay=enemy.damageMov;
            dmgTakenTextModifier=1;
            enemy.damageBoost=1;
            if(emplearVibracion) vibrator.vibrate(250);

        }

        if(enemy.golpea(player.hurtbox)&&player.isBlocking){
            if(emplearSFX&&mpParry!=null)mpParry.start();
            player.setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
            enemy.damageBoost=1;
            dmgTakenTextModifier=1;
            slowHit=true;
            enemy.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
            enemy.isInvulnerable=false;
        }

        if(player.golpea(enemy.hurtbox)&&enemy.isBlocking){
            if(emplearSFX&&mpParry!=null)mpParry.start();
            player.damageBoost=1;
            enemy.setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
            dmgDoneTextModifier=1;
            slowHit=true;
            player.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
            player.isInvulnerable=false;
        }
    
        if(player.getCurrentAction()== AccionesPersonaje.PROJECTILE.getAction()&&player.getCurrentAnimationFrame()==player.throwingProjectileFrame){
            Proyectil pr = new Proyectil(player.projectile,player.projectileFinished,true,player.posXProyectil,player.posYProyectil);
            proyectiles.add(pr);
        }
        if(player instanceof Terry &&player.getCurrentAction()== AccionesPersonaje.LOWKICK.getAction() && player.getCurrentAnimationFrame()==player.throwingProjectileFrame){
            PowerGeyser pr = new PowerGeyser(((Terry) player).powerGeyser,((Terry) player).powerGeyser,true,player.posXProyectil,player.posYProyectil);
            proyectiles.add(pr);
        }
        //todo: checkear si los proyectiles se golpean entre sí (sudo, que sean intangibles)
        if(enemy.getCurrentAction()== AccionesPersonaje.PROJECTILE.getAction()&&enemy.getCurrentAnimationFrame()==enemy.throwingProjectileFrame){
            Proyectil pr = new Proyectil(enemy.projectile,enemy.projectileFinished,false,enemy.posXProyectil-enemy.width,enemy.posYProyectil);
            proyectiles.add(pr);
        }
        if(enemy instanceof Terry &&enemy.getCurrentAction()==AccionesPersonaje.LOWKICK.getAction() && enemy.getCurrentAnimationFrame()==enemy.throwingProjectileFrame){
            PowerGeyser pr = new PowerGeyser(((Terry) enemy).powerGeyser,((Terry) enemy).powerGeyser,false,enemy.posXProyectil,enemy.posYProyectil);
            proyectiles.add(pr);
        }

        for(Proyectil p: proyectiles){
            p.moverEnX(p.speed);
        }

        for(int i=proyectiles.size();i>0;i--){
            Proyectil pr = proyectiles.get(i-1);
            if(pr.golpea(pr.isFromPlayer?enemy.hurtbox:player.hurtbox)){
                if(emplearVibracion) vibrator.vibrate(250);
                if(pr.isFromPlayer && !enemy.currentMoveAnimation[enemy.getCurrentAnimationFrame()%enemy.currentMoveAnimation.length].esGolpeo){
                    enemy.setVidaActual(pr.damageMov);
                    player.damageBoost=1;
                    enemy.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
                    slowHit=true;
                    dmgDoneDisplay=pr.damageMov;
                    dmgDoneTextModifier=1;
                }else if(!pr.isFromPlayer && !player.currentMoveAnimation[player.getCurrentAnimationFrame()%player.currentMoveAnimation.length].esGolpeo){
                    player.setVidaActual(pr.damageMov);
                    enemy.damageBoost=1;
                    player.setCurrentAnimation(AccionesPersonaje.TAKING_LIGHT_DAMAGE.getAction());
                    slowHit=true;
                    dmgTakenDisplay=pr.damageMov;
                    dmgTakenTextModifier=1;
                }
                proyectiles.remove(pr);
            }
        }
        if(!enemy.isDoingAMove){
            int a = (int)(Math.random()*4);
            if(a==1)tomaDecisionDeLaIA();
        }
        //TODO COMPROBAR EL ORDEN DE DECISION DE MOVER PROYECTILES Y CHECKEAR CUANDO GOLPEAN, IGUAL PETA POR ESO

        if(enemy.getCurrentAnimationFrame()==enemy.parryFrame && enemy.getCurrentAction()== AccionesPersonaje.PROTECT.getAction()){
            int a = (int)(Math.random()*5);
            if(a!=1)enemy.setDeltaCurrentAnimationFrame(-1);
            enemy.isBlocking=true;
        }

        if(valorInicialInclinacionY-rotacionEnY>umbralSensibilidadY && player.getCurrentAnimationFrame()==player.parryFrame &&player.getCurrentAction()== AccionesPersonaje.PROTECT.getAction()){
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
        boolean estanCerca =enemy.posX-enemy.posEnemigo<enemy.width*3/2;
        //y si esta acorralado haga otra cosa tmb
        if(isCornered)
        {//todo aqui PROHIBIDO IR PARA ATRAS, que se me buggea el bicho lmaoo
            if(!isWinning &&estanCerca){
                comportamientoCercanoArrinconado(25); //esto haria que fuese mas probable que atacase
            }else if(isWinning &&estanCerca){
                comportamientoCercanoArrinconado(50); //esto hace que sea mas probable que se defienda
            }else if(isWinning){
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
            }else if(isWinning){
                comportamientoCercano(40); //todo menos bloq, se queda pillado
                //comportamiento algo mas pasivo, incluso se echa para atras
            }else{
                comportamientoCercano(15);//todo tbh me falta incluir más veces el iddle animation
            }
        }

    }
    public void comportamientoCercanoArrinconado(int max){
        int queHacer = (int)(Math.random()*max+1);
        switch (queHacer){
            case 1:
            case 2:
            case 3:
                enemy.setCurrentAnimation(AccionesPersonaje.UPPERCUT.getAction());
                break;
            case 4:
                enemy.setCurrentAnimation(AccionesPersonaje.CROUCH.getAction());
                break;
            case 5:
            case 6:
            case 7:
                enemy.setCurrentAnimation(AccionesPersonaje.PUNCH.getAction());
                break;
            case 8:
            case 9:
            case 10:
                enemy.setCurrentAnimation(AccionesPersonaje.STRONG_PUNCH.getAction());
                break;
            case 11:
            case 12:
                enemy.setCurrentAnimation(AccionesPersonaje.LOWKICK.getAction());
                break;
            case 13:
            case 14:
            case 15:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_FORWARD.getAction());
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                enemy.setCurrentAnimation(AccionesPersonaje.MOVE_FORWARD.getAction());
                break;
            case 22:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_BACKWARDS.getAction());
                break;
            default:
                if(enemy.getCurrentAction()!= AccionesPersonaje.PROTECT.getAction()){
                    enemy.setCurrentAnimation(AccionesPersonaje.PROTECT.getAction());
                }
                break;
        }
    }
    public void comportamientoADistanciaArrinconado(int max){
        int accion = (int)(Math.random()*max+1);
        switch (accion){
            case 1:
                enemy.setCurrentAnimation(AccionesPersonaje.CROUCH.getAction());
                break;
            case 2:
            case 3:
            case 4:
                enemy.setCurrentAnimation(AccionesPersonaje.PROJECTILE.getAction());
                break;
            case 5:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_BACKWARDS.getAction());
                break;
            default:
                enemy.setCurrentAnimation(AccionesPersonaje.MOVE_FORWARD.getAction());
                break;
        }
    }
    public void comportamientoMantenerDistancia(int max){
        int num= (int)(Math.random()*max+1);
        switch (num){
            case 1:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_FORWARD.getAction());
                break;
            case 2:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_BACKWARDS.getAction());
                break;
            case 3:
            case 4:
                enemy.setCurrentAnimation(AccionesPersonaje.CROUCH.getAction());
            case 5:
            case 6:
            case 7:
                enemy.setCurrentAnimation(AccionesPersonaje.PROJECTILE.getAction());
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                enemy.setCurrentAnimation(AccionesPersonaje.MOVE_BACKWARDS.getAction());
                break;
            case 13:
            default:
                enemy.setCurrentAnimation(AccionesPersonaje.MOVE_FORWARD.getAction());
                break;
        }
    }
    public void comportamientoCercano(int max){

        int accion = (int)(Math.random()*max+1);
        if(enemy.getCurrentAction()== AccionesPersonaje.PROTECT.getAction()){
            accion=(int)(Math.random()*100+1);
        }
        switch (accion){
            case 1:
            case 2:
                enemy.setCurrentAnimation(AccionesPersonaje.PUNCH.getAction());
                break;
            case 3:
            case 4:
                enemy.setCurrentAnimation(AccionesPersonaje.UPPERCUT.getAction());
                break;

            case 5:
                enemy.setCurrentAnimation(AccionesPersonaje.PROJECTILE.getAction());
                break;
            case 6:
            case 7:
                enemy.setCurrentAnimation(AccionesPersonaje.STRONG_PUNCH.getAction());
                break;
            case 8:
            case 9:
                enemy.setCurrentAnimation(AccionesPersonaje.LOWKICK.getAction());
                break;
            case 10:
            case 11:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_FORWARD.getAction());
                break;
            case 12:
            case 13:
                enemy.setCurrentAnimation(AccionesPersonaje.ATTACK_BACKWARDS.getAction());
                break;
            case 14:
                enemy.setCurrentAnimation(AccionesPersonaje.CROUCH.getAction());
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                enemy.setCurrentAnimation(AccionesPersonaje.MOVE_BACKWARDS.getAction());
                break;
            default:
                if(enemy.getCurrentAction()!= AccionesPersonaje.PROTECT.getAction()){
                    enemy.setCurrentAnimation(AccionesPersonaje.PROTECT.getAction());
                }
        }
    }

    /**
     * Método que dibuja los elementos del juego en pantalla
     * @param c canvas
     */
    public void dibuja(Canvas c){
        c.drawBitmap(escenario.fondo,0,0,null);
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

        /**
         * Establece la animación del jugador a golpeo fuerte
         * @param motionEvent evento
         * @return true
         */
        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if(!player.isDoingAMove || (player.getCurrentAction() == AccionesPersonaje.PUNCH.getAction())){
                player.setCurrentAnimation(AccionesPersonaje.STRONG_PUNCH.getAction());
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

        /**
         * Establece la animación del jugador al puñetazo básico
         * @param motionEvent evento
         * @return true
         */
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if(!player.isDoingAMove) {
                player.setCurrentAnimation(AccionesPersonaje.PUNCH.getAction());
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
            return false;
        }

        /**
         * Lanza un proyectil
         * @param motionEvent evento
         */
        @Override
        public void onLongPress(MotionEvent motionEvent) {
            if(!player.isDoingAMove) {
                player.setCurrentAnimation(AccionesPersonaje.PROJECTILE.getAction());
            }
        }

        /**
         * Según la dirección hacia donde se desplace el dedo establecerá una posición u otra
         * @param me datos del evento al pulsar la pantalla
         * @param me1 datos del evento al dejar de pulsar la pantalla
         * @param vX velocidad en x
         * @param vY velocidad en y
         * @return true
         */
        @Override
        public boolean onFling(MotionEvent me, MotionEvent me1, float vX, float vY) {

            if(!player.isDoingAMove) {
                if (Math.abs( me.getX()-me1.getX())>Math.abs(me.getY()-me1.getY())) {
                    if(me.getX()<me1.getX()){
                        player.setCurrentAnimation(AccionesPersonaje.ATTACK_FORWARD.getAction());
                    }else{
                        player.setCurrentAnimation(AccionesPersonaje.ATTACK_BACKWARDS.getAction());
                    }
                }else{
                    if(me.getY()<me1.getY()){
                        player.setCurrentAnimation(AccionesPersonaje.LOWKICK.getAction());
                    }else{
                        player.setCurrentAnimation(AccionesPersonaje.UPPERCUT.getAction());
                    }
                }
            }
            return true;
        }
    }
}


