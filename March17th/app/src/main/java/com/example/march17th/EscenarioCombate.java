package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.emplearMusicaFondo;
import static com.example.march17th.Constantes.volume;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Clase que representa un escenario, con un fondo y una canción asociados
 */
public class EscenarioCombate {

    /**
     * Imagen del escenario de combate
     */
    Bitmap fondo;

    /**
     * Canción multimedia que suena de fondo
     */
    MediaPlayer mp;
    int resourceAudio;
    int resourceBitmap;
    /**
     * Inicia el escenario
     * @param resourceBitmap imagen del escenario
     * @param resourceAudio sonido de la música
     */
    public EscenarioCombate(int resourceBitmap, int resourceAudio){
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),resourceBitmap),(int)(anchoPantalla*1.1),(int)(altoPantalla*1.1),true);
        mp=MediaPlayer.create(context,resourceAudio);
        this.resourceAudio=resourceAudio;
        this.resourceBitmap=resourceBitmap;
        // mp.prepareAsync();
        mp.setVolume(volume/2,volume/2);
    }
    public void Reproduce(){
        if(mp==null){
            mp=MediaPlayer.create(context,resourceAudio);
        }
        if(emplearMusicaFondo){
            mp.start();
            Log.i("scn", "toca otra vez Sam: "+mp.isPlaying());
        }
    }

    public void Pausa(){
        mp.pause();
    }

    public void QuitarCancion(){
        this.Pausa();
        mp=null; //o lo pongo a null?
    }
}
