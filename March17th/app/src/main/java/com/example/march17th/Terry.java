package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

/**
 * Representación de un personaje
 */
public class Terry extends Personaje{

    /**
     * animación de un proyectil exclusivo de este personaje
     */
    Frame[] powerGeyser;

    /**
     * Inicia los valores según los parámetros
     * @param posX posicion del personaje en X
     * @param posY posición del personaje en Y
     * @param vida puntos de vida totales
     * @param isPlayer si lo maneja el jugador o no
     */
    public Terry(int posX, int posY, int vida,boolean isPlayer) {
        super(posX, posY, vida,isPlayer);
        IniciaAnimaciones();
        iniciaSFX();
        this.name="Terry";
    }

    /**
     * Establece los valores de los efectos de sonido que emite al realizar ciertas acciones
     */
    private void iniciaSFX(){
        mpUpperCut= MediaPlayer.create(context,R.raw.terrypowerdank);
        mpBackwardsAttack= MediaPlayer.create(context, R.raw.terrycrackshoot);
        mpProjectile=MediaPlayer.create(context,R.raw.terryburnknucle);
        mpLowerAttack= MediaPlayer.create(context,R.raw.terrypowergeiser);
        mpProjectile = MediaPlayer.create(context,R.raw.terrypowerwave);
    }

    /**
     * Inicia los valores de todas las animaciones
     */
    private void IniciaAnimaciones(){
        parryFrame=1;
        throwingProjectileFrame=6;
        //todo crouch,

        iddleAnimation= new Frame[11];
        punchAnimation = new Frame[4];
        strongPunch = new Frame[5];
        takingLightDamage = new Frame[4];
        backwardAttack = new Frame[8];
        forwardAttack = new Frame[10];
        uppercut = new Frame[13];
        throwingProjectile= new Frame[11];
        downwardAttack = new Frame[11];
        powerGeyser = new Frame[9];
        moveBackwards= new Frame[7];
        moveForward = new Frame[7];
        parry = new Frame[2];
        projectile = new Frame[4];
        crouch = new Frame[4];

        crouch[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryagachao),width,height,true),false,this.isPlayer,0,0);
        crouch[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryagachao),width,height,true),false,this.isPlayer,0,0);
        crouch[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryagachao),width,height,true),false,this.isPlayer,0,0);
        crouch[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryagachao),width,height,true),false,this.isPlayer,0,0);

        parry[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryparry1),width,height,true),false,this.isPlayer,0,0);
        parry[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryparry2),width,height,true),false,this.isPlayer,0,0);

        projectile[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powerwave1),width*2/5,height*2/5,true),true,this.isPlayer,20,0);
        projectile[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powerwave2),width*2/5,height*2/5,true),true,this.isPlayer,20,0);
        projectile[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powerwave3),width*2/5,height*2/5,true),true,this.isPlayer,20,0);
        projectile[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powerwave4),width*2/5,height*2/5,true),true,this.isPlayer,20,0);


        powerGeyser[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser1),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser2),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser3),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser4),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser5),width,height,true),true,this.isPlayer,100,0);
        powerGeyser[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser6),width,height,true),true,this.isPlayer,100,0);
        powerGeyser[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser7),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser8),width,height,true),false,this.isPlayer,0,0);
        powerGeyser[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.powergeiser9),width,height,true),false,this.isPlayer,0,0);


        punchAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytap1),width,height,true),false,this.isPlayer,0,0);
        punchAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytap2),width,height,true),false,this.isPlayer,0,0);
        punchAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytap3),width,height,true),true,this.isPlayer,20,0);
        punchAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytap4),width,height,true),false,this.isPlayer,0,0);

        downwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown1),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown2),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown3),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown4),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown5),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown6),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown7),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown8),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown9),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown10),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown11),width,height,true),false,this.isPlayer,0,0);


        iddleAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle1),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle2),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle3),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle4),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle5),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle6),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle7),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle8),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle9),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle10),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle11),width,height,true),false,this.isPlayer,0,0);

        strongPunch[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrykick1),width,height,true),false,this.isPlayer,0,20);
        strongPunch[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrykick2),width,height,true),false,this.isPlayer,0,0);
        strongPunch[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrykick3),width,height,true),true,this.isPlayer,20,0);
        strongPunch[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrykick4),width,height,true),true,this.isPlayer,20,0);
        strongPunch[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrykick5),width,height,true),false,this.isPlayer,0,0);

        takingLightDamage[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytakinglightdamage1),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytakinglightdamage2),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytakinglightdamage3),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrytakinglightdamage4),width,height,true),false,this.isPlayer,0,0);

        backwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback1),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback2),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback3),width,height,true),true,this.isPlayer,20,0);
        backwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback4),width,height,true),true,this.isPlayer,30,0);
        backwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback5),width,height,true),true,this.isPlayer,20,0);
        backwardAttack[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback6),width,height,true),true,this.isPlayer,20,0);
        backwardAttack[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback7),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingback8),width,height,true),false,this.isPlayer,0,0);

        forwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward1),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward2),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward3),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward4),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward5),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward6),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward7),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward8),width,height,true),true,this.isPlayer,70,0);
        forwardAttack[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward9),width,height,true),true,this.isPlayer,70,0);
        forwardAttack[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingforward10),width,height,true),false,this.isPlayer,0,0);

        uppercut[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup1),width,height,true),false,this.isPlayer,0,0);
        uppercut[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup2),width,height,true),false,this.isPlayer,0,0);
        uppercut[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup3),width,height,true),false,this.isPlayer,0,0);
        uppercut[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup4),width,height,true),true,this.isPlayer,40,0);
        uppercut[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup5),width,height,true),false,this.isPlayer,0,0);
        uppercut[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup6),width,height,true),false,this.isPlayer,0,0);
        uppercut[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup7),width,height,true),false,this.isPlayer,0,0);
        uppercut[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup8),width,height,true),false,this.isPlayer,0,0);
        uppercut[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup9),width,height,true),true,this.isPlayer,50,0);
        uppercut[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup10),width,height,true),true,this.isPlayer,50,0);
        uppercut[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup11),width,height,true),true,this.isPlayer,50,0);
        uppercut[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup13),width,height,true),false,this.isPlayer,0,0);
        uppercut[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingup13),width,height,true),false,this.isPlayer,0,0);


        moveForward[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward1),width,height,true),false,this.isPlayer,0,0);
        moveForward[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward2),width,height,true),false,this.isPlayer,0,0);
        moveForward[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward3),width,height,true),false,this.isPlayer,0,0);
        moveForward[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward4),width,height,true),false,this.isPlayer,0,0);
        moveForward[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward5),width,height,true),false,this.isPlayer,0,0);
        moveForward[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward6),width,height,true),false,this.isPlayer,0,0);
        moveForward[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymoveforward7),width,height,true),false,this.isPlayer,0,0);

        moveBackwards[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards1),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards2),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards3),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards4),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards5),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards6),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrymovebackwards7),width,height,true),false,this.isPlayer,0,0);

        throwingProjectile[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown1),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown2),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown3),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown4),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown5),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown6),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown7),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown8),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown9),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown10),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryflingdown11),width,height,true),false,this.isPlayer,0,0);

        this.posXProyectil=this.posX+(isPlayer?width:0);
        this.posYProyectil=this.posY+height;
        currentMoveAnimation=iddleAnimation;
    }

    /**
     * Actualiza la posición del personaje y su hitbox según el movimiento empleado
     * Amplía la función de la clase padre para actualizar los movimientos específicos de Ryu
     * @param action acción que está realizando el personaje
     */
    @Override
    public void actualizaFisica(int action) {
        super.actualizaFisica(action);
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        //actualiza en cualquier momento la posición de donde se lanzaría el proyectil
        this.posXProyectil=this.posX+(isPlayer?width:0);
        this.posYProyectil=this.posY;
        switch (ac){
            case UPPERCUT:
                actualizaUpperCut();//todo: aqui hay un problema cuando te golpean mientras estás airborne-> realizar una nueva accion de heavy damage y boolean de isAirborne
                //en caso de que te golpeen en el aire te quedas en esa animacion hasta que llegues al "suelo"
                //(al final hice una solución...menos elegante)
                break;
            case ATTACK_BACKWARDS:
                actualizaCrackShoot();
                break;
            case ATTACK_FORWARD:
                actualizaBurningKnucle();
        }
    }

    /**
     * actualiza la física del movimiento Uppercut de Ryu, donde salta al aire cuando golpea y luego cae al suelo de nuevo
     */
    public void actualizaUpperCut(){
        switch (getCurrentAnimationFrame()){
            case 3:
            case 4:
            case 5:
                isInvulnerable=true;
                moverEnY(-altoPantalla*2/23);
                moverEnX(anchoPantalla/23);
                break;
            case 8:
            case 9:
                isInvulnerable=true;
                moverEnY(altoPantalla*2/23);
                moverEnX(anchoPantalla/23);
                break;
            case 10:
                isInvulnerable=false;
                moverEnY(altoPantalla*2/23);
                moverEnX(anchoPantalla/23);
                break;
        }
    }

    public void actualizaCrackShoot(){
        switch (getCurrentAnimationFrame()){
            case 1:
            case 2:
            case 3:
            case 4:
                moverEnX(anchoPantalla*2/30);
                break;
        }
    }

    public void actualizaBurningKnucle(){
        switch (getCurrentAnimationFrame()){

            case 7:
            case 8:
                moverEnX(anchoPantalla*2/23);
                break;
        }
    }
}
