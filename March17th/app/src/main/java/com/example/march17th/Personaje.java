package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.emplearSFX;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * Representa la estructura base de cada personaje que aparece en el juego
 */
public class Personaje {


    String name;
    /**
     * animación del personaje cuando está quieto
     */
    Frame[] iddleAnimation;

    /**
     * Animación del personaje cuando camina hacia adelante
     */
    Frame[] moveForward;

    /**
     * Animación del personaje cuando camina hacia atrás
     */
    Frame[] moveBackwards;

    /**
     * Animación del personaje cuando se agacha
     */
    Frame[] crouch;

    /**
     * Animación del personaje cuando bloquea un ataque
     */
    Frame[] parry;

    /**
     * Animación del personaje cuando golpea (tap)
     */
    Frame[] punchAnimation;

    /**
     * Animación del personaje cuando ejecuta un ataque fuerte (double tap)
     */
    Frame[] strongPunch;

    /**
     * Animación del personaje cuando realiza el ataque hacia atrás (fling atrás)
     */
    Frame[] backwardAttack;

    /**
     * Animación del personaje cuando realiza el ataque hacia arriba (fling arriba)
     */
    Frame[] uppercut;

    /**
     * Animación del personaje cuando realiza el ataque hacia adelante (fling adelante)
     */
    Frame[] forwardAttack;

    /**
     * Animación del personaje cuando realiza el ataque hacia abajo (fling abajo)
     */
    Frame[] downwardAttack;

    /**
     * Animación del personaje cuando recibe daño
     */
    Frame[] takingLightDamage;

    /**
     * Animación del movimiento que está realizando el personaje
     */
    Frame[] currentMoveAnimation;

    /**
     * Animación del personaje cuando lanza un proyectil (on long press)
     */
    Frame[] throwingProjectile;

    /**
     * Animación del proyectil moviéndose
     */
    Frame[] projectile;

    /**
     * Animación del proyectil cuando impacta
     */
    Frame[] projectileFinished;
    //hubiese sido mucho mejor condensar los frames+mediaplayer en una clase? pues sí, pero te jodes meu, xa é demasiado tarde


    Bitmap boost= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.boost),altoPantalla/15,altoPantalla/15,true);
    /**
     * Sonido que realiza al hacer el ataque hacia arriba
     */
    MediaPlayer mpUpperCut;
    /**
     * Sonido que realiza al lanzar el proyectil
     */
    MediaPlayer mpProjectile;
    /**
     * Sonido que realiza al hacer el ataque hacia atrás
     */
    MediaPlayer mpBackwardsAttack;
    /**
     * Sonido que realiza al hacer el ataque hacia adelante
     */
    MediaPlayer mpForwardAttack;

    /**
     * Sonido que realiza al hacer el ataque hacia abajo
     */
    MediaPlayer mpLowerAttack;

    /**
     * Dimensiones del rectángulo que es el marco que muestra la vida del personaje
     */
    Rect displayMarcoVidaTotal;

    /**
     * Dimensiones del rectángulo que es una barra que muestra la vida actual del personaje
     */
    Rect displayVidaActual;

    /**
     * pincel de la barra de vida actual
     */
    Paint pVida;

    /**
     * pincel del marco de vida total
     */
    Paint pMarcoVida;
    /**
     * Indica si el personaje está controlado por el jugador
     */
    boolean isPlayer;

    /**
     * Indica si el personaje está realizando un movimiento (que no se puede cancelar)
     */
    boolean isDoingAMove;

    /**
     * Indica si el personaje es inmune al daño (para no recibir el impacto 3 veces seguidas, por ejemplo)
     */
    boolean isInvulnerable;

    /**
     * indica si el personaje está en el estado *bloqueando*
     */
    boolean isBlocking;

    /**
     * Indica si el personaje sigue vivo
     */
    boolean isAlive=true;

    /**
     * dimensión del personaje en el eje Y
     */
    int height = altoPantalla*2/5;

    /**
     * dimensión del personaje en el eje X
     */
    int width= height*2/3;

    /**
     * Posición del personaje en pantalla
     */
    int posX,posY;

    /**
     * Posición inicial de dónde lanza el personaje su proyectil
     */
    int posXProyectil, posYProyectil;

    /**
     * Puntos de vida máximos del personaje
     */
    int vidaMaxima;

    /**
     * Puntos de vida actuales del personaje
     */
    int vidaActual;

    /**
     * Posición del personaje al que te enfrentas. Sirve para evitar atravesar al otro personaje
     */
    int posEnemigo;

    /**
     * Frame del movimiento actual del personaje
     */
    int currentAnimationFrame=0;

    /**
     * Daño que está haciendo en este instante el movimiento del personaje
     */
    int damageMov=0;

    /**
     * Frame en la que el personaje entra en el estado *bloqueando* en la acción PROTECT
     */
    int parryFrame;

    /**
     * Frame en la que el personaje crea el proyectil dentro de la acción PROJECTILE
     */
    int throwingProjectileFrame;

    /**
     * Indica la acción actual que está ejecutando el personaje
     */
    int currentAction;

    /**
     * Hurtbox del personaje
     */
    Rect hurtbox;

    /**
     * @deprecated se planeaba poder cambiar las animaciones según dónde estuviese cada personaje, pero igual tiraría demasiado de memoria al tener que voltear +200 imagenes
     */
    boolean invierteAnimacion;

    /**
     * Multiplicador de daño
     */
    float damageBoost=1;

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }

    /**
     * aumenta o disminuye el valor del frame en el que está el personaje y actualiza la hitbox
     * @param interval intervalo de cambio del frame
     */
    public void setDeltaCurrentAnimationFrame(int interval) {
        this.currentAnimationFrame += interval;
        actualizaHitbox();
    }


    /**
     * Establece los valores iniciales del personaje
     * @param posX posición en el eje X
     * @param posY posición en el eje Y
     * @param vida vida del personaje
     * @param isPlayer indica si está controlado por el jugador
     */
    public Personaje(int posX, int posY,int vida, boolean isPlayer) {

        setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
        pVida = new Paint();
        pVida.setStyle(Paint.Style.FILL);
        pMarcoVida = new Paint();
        pMarcoVida.setColor(Color.BLACK);
        pMarcoVida.setStyle(Paint.Style.STROKE);
        this.posX = posX;
        this.posY = posY;
        this.vidaMaxima =vida;
        this.vidaActual=vidaMaxima;
        isDoingAMove=false;
        isInvulnerable=false;
        this.isPlayer=isPlayer;
        displayMarcoVidaTotal = new Rect(isPlayer?anchoPantalla*8/52:anchoPantalla*28/52,altoPantalla*6/25,isPlayer?anchoPantalla*8/52+anchoPantalla*16/52:anchoPantalla*28/52+anchoPantalla*16/53,altoPantalla*7/25);
        displayVidaActual = new Rect(isPlayer?anchoPantalla*8/52:anchoPantalla*28/52,altoPantalla*6/25,(vidaActual/vidaMaxima)*(isPlayer?anchoPantalla*8/52+anchoPantalla*16/52:anchoPantalla*28/52+anchoPantalla*16/53),altoPantalla*7/25);
        hurtbox=new Rect(posX,posY,posX+width,posY+height);
    }

    /**
     * Dibuja los componentes del personaje en pantalla (la vida, el marco de la vida total y el frame del movimiento actual)
     * @param canvas canvas
     */
    public void dibuja(Canvas canvas){
        //estos cálculos son para darle un color a la barra de vida según cuánto le queda (en %)
        int compR =(int)(255-Math.round((vidaActual/(vidaMaxima/2))-1)*255/2);
        if(vidaActual<=vidaMaxima/2){
            compR=255;
        }
        int compG =255;
        if(vidaActual<=vidaMaxima/2){
            compG=0;
        }
        pVida.setARGB(255,compR,compG,0);
        canvas.drawRect(displayVidaActual,pVida);
        if(damageBoost>1){
            canvas.drawBitmap(boost,isPlayer?anchoPantalla*6/52:anchoPantalla*28/52+anchoPantalla*16/53,altoPantalla*6/27,null);
        }

        pMarcoVida.setColor(Color.WHITE);
        pMarcoVida.setStrokeWidth(15);
        canvas.drawRect(displayMarcoVidaTotal,pMarcoVida);
        canvas.drawBitmap(currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov(),posX,posY,null);
        //canvas.drawRect(this.hurtbox,pMarcoVida); esto es para la hitbox, para debuggear
    }

    /**
     * Detecta si ha habido una colisión con el enemigo y actualiza dónde se encuentra el enemigo
     * @param hurtboxEnemigo hurtbox del enemigo
     * @return true si ha habido contacto, false si no
     */
    public boolean golpea(Rect hurtboxEnemigo){
        this.posEnemigo=(hurtboxEnemigo.left);
        damageMov=(int)(currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].damage*damageBoost);
        return (currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].esGolpeo && hurtbox.intersect(hurtboxEnemigo));
    }

    public int getCurrentAction() {
        return currentAction;
    }

    /**
     * Cambia la vida actual del personaje, establece si sigue vivo y lo desplaza ligeramente hacia atrás por el hostión recibido
     * @param damageTaken vida que se le resta al personaje
     */
    public void setVidaActual(int damageTaken) {
        this.vidaActual = Math.max(vidaActual - damageTaken, 0);
        this.moverEnX(-anchoPantalla/35);
        displayVidaActual = new Rect(isPlayer?anchoPantalla*8/52:anchoPantalla*28/52,altoPantalla*6/25,(isPlayer?anchoPantalla*8/52:anchoPantalla*28/52)+(vidaActual*anchoPantalla*16/52/vidaMaxima),altoPantalla*7/25);
        if(vidaActual<=0){
            isAlive=false;
        }
    }

    /**
     * Algunos movimientos, por el mero hecho de estar ejecutando esa animación, mueven al personaje
     * Este método realiza ese movimiento según qué acción se esté ejecutando
     * Además, si se termina una acción establece que se vuelva a la iddle animation
     * @param action acción que se está ejecutando
     */
    public void actualizaFisica(int action){
        if(currentAnimationFrame>=currentMoveAnimation.length && currentAction!= AccionesPersonaje.IDDLE.getAction()){
            if(currentAction==AccionesPersonaje.CROUCH.getAction()){
                damageBoost=1.5f; //si hace un crouch se boostea el daño que se realiza un 50% en el próximo movimiento
            }

                setCurrentAnimation(AccionesPersonaje.IDDLE.getAction());
        }
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            //todos los personajes se mueven para adelante y para detrás de la misma manera
            //esto tendrá sobrecargas en las clases hijas para ampliar movimientos
            case MOVE_FORWARD:
                moverEnX(anchoPantalla/50);
                break;
            case MOVE_BACKWARDS:
                moverEnX(-anchoPantalla/50);
                break;
        }
    }

    /**
     * Cambia el movimiento del personaje, así como las características especiales que puedan tener estos. También reproduce el audio
     * @param action la acción que se va a realizar
     */
    public void setCurrentAnimation(int action){
        currentAnimationFrame=0;
        isDoingAMove=true;
        isInvulnerable=false;
        isBlocking=false;
        this.currentAction = action;
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case PUNCH:
                currentMoveAnimation=punchAnimation;
                break;
            case MOVE_FORWARD:
                currentMoveAnimation=moveForward;
                isDoingAMove=false;
                break;
            case IDDLE:
                currentMoveAnimation=iddleAnimation;
                isDoingAMove=false;
                break;
            case ATTACK_FORWARD:
                currentMoveAnimation= forwardAttack;
                if(mpForwardAttack!=null && emplearSFX){
                    mpForwardAttack.start();
                }
                break;
            case TAKING_LIGHT_DAMAGE:
                currentMoveAnimation=takingLightDamage;
                isInvulnerable=true;
                break;
            case STRONG_PUNCH:
                currentMoveAnimation=strongPunch;
                break;
            case ATTACK_BACKWARDS:
                currentMoveAnimation= backwardAttack;
                if(mpBackwardsAttack!=null && emplearSFX){
                    mpBackwardsAttack.start();
                }
                break;
            case UPPERCUT:
                currentMoveAnimation=uppercut;
                if(mpUpperCut!=null && emplearSFX){
                    mpUpperCut.start();
                }
                break;
            case LOWKICK:
                currentMoveAnimation= downwardAttack;
                if(mpLowerAttack!=null && emplearSFX){
                    mpLowerAttack.start();
                }
                break;
            case PROJECTILE:
                currentMoveAnimation=throwingProjectile;
                if(mpProjectile!=null && emplearSFX){
                    mpProjectile.start();
                }
                break;
            case CROUCH:
                currentMoveAnimation=crouch;
                break;
            case TAUNT:
                break;
            case MOVE_BACKWARDS:
                currentMoveAnimation=moveBackwards;
                isDoingAMove=false;
                break;
            case PROTECT:
                currentMoveAnimation=parry;
                break;
        }
    }

    /**
     * Mueve al personaje si este movimiento no rompe las condiciones establecidas(que esté dentro de la pantalla y que el player esté a la izquierda y el enemigo a la derecha)
     * @param posX cantidad que se desplaza en horizontal
     */
    public void moverEnX(int posX) {

        if(this.posX+(isPlayer?posX:-posX)<anchoPantalla-width && this.posX+(isPlayer?posX:-posX)>0&&((isPlayer&&this.posX+posX<posEnemigo-this.width/3)||!isPlayer&&this.posX-posX>posEnemigo+this.width/3)) {
            this.posX += isPlayer?posX:-posX;
        }else if(this.posX+(isPlayer?posX:-posX)>anchoPantalla-width){
            this.posX=anchoPantalla-width;
        }else if(this.posX+(isPlayer?posX:-posX)<0){
            this.posX=0;
        }
        actualizaHitbox();
    }

    /**
     * Mueve al personaje si este movimiento no hace que el personaje se salga por pantalla
     * @param posY cantidad que se desplaza en vertical
     */
    public void moverEnY(int posY) {
        if(this.posY+posY<altoPantalla+height && this.posY+posY>0){
            this.posY += posY;
            actualizaHitbox();
        }
    }

    /**
     * Actualiza el valor de la hitbox del personaje al valor que le corresponderia segun la animación actual
     */
    public void actualizaHitbox(){
        int ancho = currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov().getWidth();
        int alto = currentMoveAnimation[currentAnimationFrame%currentMoveAnimation.length].getFrameMov().getHeight();
        hurtbox=new Rect(this.posX,this.posY,ancho+this.posX,alto+this.posY);
    }
}
