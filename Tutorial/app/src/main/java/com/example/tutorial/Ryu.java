package com.example.tutorial;

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

        lowKick[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown1),width,height/2,true),false,this.isPlayer,0,0);
        lowKick[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown2),width,height/2,true),true,this.isPlayer,10,0);
        lowKick[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown3),width,height/2,true),true,this.isPlayer,10,0);
        lowKick[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown4),width,height/2,true),false,this.isPlayer,0,0);
        lowKick[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryuflingdown5),width,height/2,true),false,this.isPlayer,0,0);

        currentMoveAnimation=iddleAnimation;
    }
}
