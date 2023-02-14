package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ryu extends Personaje{
    public Ryu(int posX, int posY, int vida, boolean isPlayer) {
        super(posX, posY, vida, isPlayer);
        iniciaAnimaciones();
    }
    public void iniciaAnimaciones(){
        iddleAnimation=new Frame[7];
        attackBackwards= new Frame[17];
        strongPunch= new Frame[9];
        crouch = new Frame[1];
        lowKick = new Frame[5];
        attackForward = new Frame[14];
        uppercut = new Frame[16];
        moveForward = new  Frame[8];
        moveBackwards = new Frame[8];
        punchAnimation = new Frame[5];
        takingLightDamage = new Frame[5];
        parry = new Frame[6];
        throwingProjectile = new Frame[13];

        attackBackwards[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback1),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback2),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback3),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback4),width,height,true),false,this.isPlayer,10,0);
        attackBackwards[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback5),width,height,true),false,this.isPlayer,30,0);
        attackBackwards[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback6),width,height,true),true,this.isPlayer,30,0);
        attackBackwards[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback7),width,height,true),true,this.isPlayer,30,0);
        attackBackwards[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback8),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback9),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback10),width,height,true),true,this.isPlayer,10,0);
        attackBackwards[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback11),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback12),width,height,true),true,this.isPlayer,10,0);
        attackBackwards[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback13),width,height,true),true,this.isPlayer,10,0);
        attackBackwards[13]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback14),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[14]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback15),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[15]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback16),width,height,true),false,this.isPlayer,0,0);
        attackBackwards[16]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingback17),width,height,true),false,this.isPlayer,0,0);

        attackForward[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad1),width,height,true),false,this.isPlayer,0,0);
        attackForward[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad2),width,height,true),false,this.isPlayer,0,0);
        attackForward[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad3),width,height,true),false,this.isPlayer,0,0);
        attackForward[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad4),width,height,true),false,this.isPlayer,0,0);
        attackForward[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad5),width,height,true),false,this.isPlayer,0,0);
        attackForward[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad6),width,height,true),false,this.isPlayer,0,0);
        attackForward[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad7),width,height,true),true,this.isPlayer,40,0);
        attackForward[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad8),width,height,true),true,this.isPlayer,40,0);
        attackForward[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad9),width,height,true),true,this.isPlayer,20,0);
        attackForward[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad10),width,height,true),false,this.isPlayer,0,0);
        attackForward[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad11),width,height,true),false,this.isPlayer,0,0);
        attackForward[11]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad12),width,height,true),false,this.isPlayer,0,0);
        attackForward[12]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad13),width,height,true),false,this.isPlayer,0,0);
        attackForward[13]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingfwad14),width,height,true),false,this.isPlayer,0,0);


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

        lowKick[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown1),width,height,true),false,this.isPlayer,0,0);
        lowKick[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown2),width*7/5,height,true),true,this.isPlayer,10,0);
        lowKick[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown3),width*7/5,height,true),true,this.isPlayer,10,0);
        lowKick[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown4),width*7/5,height,true),false,this.isPlayer,0,0);
        lowKick[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown5),width,height,true),false,this.isPlayer,0,0);

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


        currentMoveAnimation=iddleAnimation;
    }

    @Override
    public void actualizaFisica(int action) {
        super.actualizaFisica(action);
        AccionesPersonaje ac = AccionesPersonaje.values()[action];
        switch (ac){
            case UPPERCUT:
                actualizaUpperCut();
                break;
            case ATTACK_BACKWARDS:
                actualizaTatsu();
                break;
        }
    }
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
    public void actualizaTatsu(){

    }
}
