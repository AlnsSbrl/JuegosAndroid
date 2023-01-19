package com.example.tutorial;

import static com.example.tutorial.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Terry extends Personaje{
    public Terry(int posX, int posY, int vida) {
        super(posX, posY, vida);
        IniciaAnimaciones();
    }

    private void IniciaAnimaciones(){
        iddleAnimation= new Frame[11];



        iddleAnimation[0]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle1),width,height,true),false,0,0);
        iddleAnimation[1]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle2),width,height,true),false,0,0);
        iddleAnimation[2]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle3),width,height,true),false,0,0);
        iddleAnimation[3]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle4),width,height,true),false,0,0);
        iddleAnimation[4]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle5),width,height,true),false,0,0);
        iddleAnimation[5]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle6),width,height,true),false,0,0);
        iddleAnimation[6]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle7),width,height,true),false,0,0);
        iddleAnimation[7]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle8),width,height,true),false,0,0);
        iddleAnimation[8]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle9),width,height,true),false,0,0);
        iddleAnimation[9]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle10),width,height,true),false,0,0);
        iddleAnimation[10]=new Frame(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terryiddle11),width,height,true),false,0,0);

    }
}
