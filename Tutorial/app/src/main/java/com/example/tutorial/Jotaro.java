package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Jotaro extends Personaje{

    public Jotaro(int posX, int posY, int vida) {
        super(posX, posY, vida);
        IniciaAnimaciones();
    }

    private void IniciaAnimaciones(){
        punchAnimation =new Frame[10];
        iddleAnimation = new Frame[10];
        moveForward = new Frame[16];
        takingLightDamage = new Frame[4];

        punchAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch1),width,height,true),false,0,10);
        punchAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch2),width,height,true),false,0,0);
        punchAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch3),width,height,true),false,0,0);
        punchAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch4),width,height,true),true,200,0);
        punchAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch5),width,height,true),true,20,0);
        punchAnimation[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch6),width,height,true),false,0,0);
        punchAnimation[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch7),width,height,true),false,0,0);
        punchAnimation[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch8),width,height,true),false,0,0);
        punchAnimation[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch9),width,height,true),false,0,0);
        punchAnimation[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotaropunch10),width,height,true),false,0,0);

        takingLightDamage[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotarogetshurt1),width,height,true),false,0,0);
        takingLightDamage[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotarogetshurt2),width,height,true),false,0,0);
        takingLightDamage[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotarogetshurt3),width,height,true),false,0,0);
        takingLightDamage[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jotarogetshurt4),width,height,true),false,0,0);

        iddleAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro1),width,height,true),false,0,10);
        iddleAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro2),width,height,true),false,0,0);
        iddleAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro3),width,height,true),false,0,0);
        iddleAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro4),width,height,true),false,0,0);
        iddleAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro5),width,height,true),false,0,0);
        iddleAnimation[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro6),width,height,true),false,0,0);
        iddleAnimation[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro7),width,height,true),false,0,0);
        iddleAnimation[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro8),width,height,true),false,0,0);
        iddleAnimation[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro9),width,height,true),false,0,0);
        iddleAnimation[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro10),width,height,true),false,0,0);

        Bitmap conjuntoDeFrames=BitmapFactory.decodeResource(context.getResources(),R.drawable.iddlejotaro);

        Bitmap aux;
        conjuntoDeFrames=BitmapFactory.decodeResource(context.getResources(),R.drawable.walkingforwardjotaro);
        for (int i = 0; i < moveForward.length; i++) {
            aux=Bitmap.createBitmap(conjuntoDeFrames,i*conjuntoDeFrames.getWidth()/16,0,conjuntoDeFrames.getWidth()/16,conjuntoDeFrames.getHeight());
            moveForward[i]=new Frame(Bitmap.createScaledBitmap(aux,width,height,true),false,0,0);
        }

        currentMoveAnimation=iddleAnimation;
    }

}
