package com.example.tutorial;

import static com.example.tutorial.Constantes.altoPantalla;
import static com.example.tutorial.Constantes.anchoPantalla;
import static com.example.tutorial.Constantes.context;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class Fondo {
    Bitmap fondo;
    MediaPlayer mp;

    public Fondo(int resourceBitmap, int resourceAudio, int volume){
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),resourceBitmap),(int)(anchoPantalla*1.1),(int)(altoPantalla*1.1),true);
        mp=MediaPlayer.create(context,resourceAudio);
        mp.setVolume(volume/2,volume/2);

    }
}
