package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

/**
 * Representación concreta de un personaje
 */
public class Ryu extends Personaje{

    public Ryu(int posX, int posY, int vida, boolean isPlayer) {
        super(posX, posY, vida, isPlayer);
        this.name="Ryu"; //todo ponerlo en string mejor
        iniciaAnimaciones();
        iniciaSFX();
    }

    /**
     * Establece los valores de los efectos de sonido que emite al realizar ciertas acciones
     */
    private void iniciaSFX(){
        mpUpperCut= MediaPlayer.create(context,R.raw.ryushoryuken);
        mpBackwardsAttack= MediaPlayer.create(context, R.raw.ryutatsumaki);
        mpProjectile=MediaPlayer.create(context,R.raw.ryuhadouken);
    }

    /**
     * Inicia los valores iniciales de todas las animaciones del personaje
     */
    private void iniciaAnimaciones(){
        iddleAnimation=new Frame[7];
        backwardAttack = new Frame[17];
        strongPunch= new Frame[9];
        crouch = new Frame[1];
        downwardAttack = new Frame[5];
        forwardAttack = new Frame[14];
        uppercut = new Frame[16];
        moveForward = new  Frame[8];
        moveBackwards = new Frame[8];
        punchAnimation = new Frame[5];
        takingLightDamage = new Frame[5];
        parry = new Frame[6];
        throwingProjectile = new Frame[13];
        projectile = new Frame[5];

        backwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback1),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback2),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback3),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback4),width,height,true),false,this.isPlayer,10,0);
        backwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback5),width,height,true),false,this.isPlayer,30,0);
        backwardAttack[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback6),width,height,true),true,this.isPlayer,30,0);
        backwardAttack[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback7),width,height,true),true,this.isPlayer,30,0);
        backwardAttack[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback8),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback9),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback10),width,height,true),true,this.isPlayer,10,0);
        backwardAttack[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback11),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback12),width,height,true),true,this.isPlayer,10,0);
        backwardAttack[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback13),width,height,true),true,this.isPlayer,10,0);
        backwardAttack[13]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback14),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[14]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback15),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[15]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback16),width,height,true),false,this.isPlayer,0,0);
        backwardAttack[16]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback17),width,height,true),false,this.isPlayer,0,0);

        forwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad1),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad2),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad3),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad4),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad5),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad6),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad7),width,height,true),true,this.isPlayer,40,0);
        forwardAttack[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad8),width,height,true),true,this.isPlayer,40,0);
        forwardAttack[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad9),width,height,true),true,this.isPlayer,20,0);
        forwardAttack[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad10),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad11),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad12),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad13),width,height,true),false,this.isPlayer,0,0);
        forwardAttack[13]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad14),width,height,true),false,this.isPlayer,0,0);


        strongPunch[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap1),width,height,true),false,this.isPlayer,0,0);
        strongPunch[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap2),width,height,true),false,this.isPlayer,0,0);
        strongPunch[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap3),width,height,true),false,this.isPlayer,0,0);
        strongPunch[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap4),width,height,true),true,this.isPlayer,10,0);
        strongPunch[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap5),width,height,true),true,this.isPlayer,30,0);
        strongPunch[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap6),width,height,true),true,this.isPlayer,30,0);
        strongPunch[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap7),width,height,true),true,this.isPlayer,10,0);
        strongPunch[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap8),width,height,true),false,this.isPlayer,0,0);
        strongPunch[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryudtap9),width,height,true),false,this.isPlayer,0,0);

        crouch[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuagachao),width/2,height/2,true),false,this.isPlayer,0,0);


        iddleAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle1),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle2),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle3),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle4),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle5),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle6),width,height,true),false,this.isPlayer,0,0);
        iddleAnimation[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuiddle7),width,height,true),false,this.isPlayer,0,0);

        downwardAttack[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown1),width,height,true),false,this.isPlayer,0,0);
        downwardAttack[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown2),width*7/5,height,true),true,this.isPlayer,10,0);
        downwardAttack[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown3),width*7/5,height,true),true,this.isPlayer,10,0);
        downwardAttack[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown4),width*7/5,height,true),false,this.isPlayer,0,0);
        downwardAttack[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown5),width,height,true),false,this.isPlayer,0,0);

        uppercut[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup1),width,height,true),false,this.isPlayer,0,0);
        uppercut[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup2),width,height,true),false,this.isPlayer,0,0);
        uppercut[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup3),width,height,true),false,this.isPlayer,0,0);
        uppercut[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup4),width,height,true),false,this.isPlayer,0,0);
        uppercut[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup5),width,height,true),false,this.isPlayer,0,0);
        uppercut[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup6),width,height,true),false,this.isPlayer,0,0);
        uppercut[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup7),width,height,true),true,this.isPlayer,50,0);
        uppercut[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup8),width,height,true),true,this.isPlayer,50,0);
        uppercut[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup9),width,height,true),true,this.isPlayer,50,0);
        uppercut[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup10),width,height,true),false,this.isPlayer,0,0);
        uppercut[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup11),width,height,true),false,this.isPlayer,0,0);
        uppercut[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup12),width,height,true),false,this.isPlayer,0,0);
        uppercut[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup13),width,height,true),false,this.isPlayer,0,0);
        uppercut[13]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup14),width,height,true),false,this.isPlayer,0,0);
        uppercut[14]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup15),width,height,true),false,this.isPlayer,0,0);
        uppercut[15]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingup16),width,height,true),false,this.isPlayer,0,0);

        moveForward[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward1),width,height,true),false,this.isPlayer,0,0);
        moveForward[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward2),width,height,true),false,this.isPlayer,0,0);
        moveForward[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward3),width,height,true),false,this.isPlayer,0,0);
        moveForward[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward4),width,height,true),false,this.isPlayer,0,0);
        moveForward[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward5),width,height,true),false,this.isPlayer,0,0);
        moveForward[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward6),width,height,true),false,this.isPlayer,0,0);
        moveForward[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward7),width,height,true),false,this.isPlayer,0,0);
        moveForward[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveforward8),width,height,true),false,this.isPlayer,0,0);

        moveBackwards[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback1),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback2),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback3),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback4),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback5),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback6),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback7),width,height,true),false,this.isPlayer,0,0);
        moveBackwards[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryumoveback8),width,height,true),false,this.isPlayer,0,0);

        punchAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutap1),width,height,true),false,this.isPlayer,0,0);
        punchAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutap2),width,height,true),true,this.isPlayer,10,0);
        punchAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutap3),width,height,true),true,this.isPlayer,10,0);
        punchAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutap4),width,height,true),false,this.isPlayer,0,0);
        punchAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutap5),width,height,true),false,this.isPlayer,0,0);

        parry[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect1),width,height,true),false,this.isPlayer,0,0);
        parry[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect2),width,height,true),false,this.isPlayer,0,0);
        parry[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect3),width,height,true),false,this.isPlayer,0,0);
        parry[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect4),width,height,true),false,this.isPlayer,0,0);
        parry[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect5),width,height,true),false,this.isPlayer,0,0);
        parry[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprotect6),width,height,true),false,this.isPlayer,0,0);
        parryFrame=3;

        takingLightDamage[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutakingdmg1),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutakingdmg2),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutakingdmg3),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutakingdmg4),width,height,true),false,this.isPlayer,0,0);
        takingLightDamage[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryutakingdmg5),width,height,true),false,this.isPlayer,0,0);

        throwingProjectile[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress1),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress2),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress3),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress4),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress5),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress6),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress7),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress8),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress9),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress10),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress11),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress12),width,height,true),false,this.isPlayer,0,0);
        throwingProjectile[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuonlongpress13),width,height,true),false,this.isPlayer,0,0);

        throwingProjectileFrame=5;

        projectile[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprojectile1),width*2/3,height*2/5,true),true,this.isPlayer,15,0);
        projectile[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprojectile2),width*2/3,height*2/5,true),true,this.isPlayer,15,0);
        projectile[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprojectile3),width*2/3,height*2/5,true),true,this.isPlayer,15,0);
        projectile[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprojectile4),width*2/3,height*2/5,true),true,this.isPlayer,15,0);
        projectile[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuprojectile5),width*2/3,height*2/5,true),true,this.isPlayer,15,0);
        this.posXProyectil=this.posX+throwingProjectile[0].getFrameMov().getWidth()*3/5;
        this.posYProyectil=this.posY+throwingProjectile[0].getFrameMov().getHeight()/6;

        currentMoveAnimation=iddleAnimation;
    }

    /**
     * Actualiza la posición del personaje y su hitbox según el movimiento empleado
     * Amplía la función de la clase padre para actualizar los movimientos específicos de Ryu
     * @param action
     */
    @Override
    public void actualizaFisica(int action) {
        super.actualizaFisica(action);
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        //actualiza en cualquier momento la posición de donde se lanzaría el proyectil
        this.posXProyectil=this.posX+throwingProjectile[0].getFrameMov().getWidth()*3/5;
        this.posYProyectil=this.posY+throwingProjectile[0].getFrameMov().getHeight()/6;
        switch (ac){
            case UPPERCUT:
                actualizaUpperCut();//todo: aqui hay un problema cuando te golpean mientras estás airborne-> realizar una nueva accion de heavy damage y boolean de isAirborne
                //en caso de que te golpeen en el aire te quedas en esa animacion hasta que llegues al "suelo"
                break;
            case ATTACK_BACKWARDS:
                actualizaTatsu();
                break;
        }
    }

    /**
     * actualiza la física del movimiento Uppercut de Ryu, donde salta al aire cuando golpea y luego cae al suelo de nuevo
     */
    public void actualizaUpperCut(){
        switch (getCurrentAnimationFrame()){
            case 7:
            case 8:
            case 9:
                moverEnY(-altoPantalla*2/23);
                break;
            case 13:
            case 14:
            case 15:
                moverEnY(altoPantalla*2/23);
                break;
        }
    }

    /**
     * Actualiza la física del movimiento hacia atrás de Ryu, que lo desplaza hacia adelante (yeah, ironic isn't it)
     */
    public void actualizaTatsu(){
        moverEnX(anchoPantalla/50);
    }
}
