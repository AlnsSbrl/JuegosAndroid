package com.example.march17th;

import static com.example.march17th.Constantes.ac;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.scn;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Locale;

public class EscenaConfiguracion extends Escena{

    Boton[] botones;
    EscenarioCombate escn;



    boolean useMusic;//=true;
    boolean useSFX;//=true;

    public EscenaConfiguracion(int numEscena) {
        super(numEscena);
        useMusic=true;
        useSFX=true;
        botones=new Boton[5];
        escn=new EscenarioCombate(R.drawable.snow,R.raw.megalovania);
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),true);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),true);
        botones[2]= new Boton(anchoPantalla/4,4*altoPantalla/6-altoPantalla/10,anchoPantalla/4,altoPantalla/10,context.getResources().getString(R.string.CancelOptions).toUpperCase(Locale.ROOT),scn.MENU_PRINCIPAL.getEscena(),true);
        botones[3]= new Boton(anchoPantalla/8,4*altoPantalla/6-altoPantalla/10,anchoPantalla/4,altoPantalla/10,context.getResources().getString(R.string.SaveOptions).toUpperCase(Locale.ROOT),scn.MENU_PRINCIPAL.getEscena(),true);
        botones[4]= new Boton(anchoPantalla/4,3*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.GyroOption).toUpperCase(Locale.ROOT),5,true);
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawBitmap(escn.fondo,0,0,null);
        for (Boton b:botones) {
            b.dibujar(c);
        }
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        int x= (int)e.getX();
        int y=(int)e.getY();
        int aux=super.onTouchEvent(e);
        if(aux!=this.numEscena &&aux!=-1){
            return aux;
        }

        for (Boton b:botones) {

            if(b.hitbox.contains(x,y)){
                if(b==botones[0]){
                    this.setUseMusic(!this.useMusic);
                    //Toast.makeText(context, useMusic?"Activada":"Desactivada", Toast.LENGTH_SHORT).show();
                }else if(b==botones[1]) {
                    this.setUseSFX(!useSFX);
                    //Toast.makeText(context, useSFX ? "ole,orgasms" : "cobarde", Toast.LENGTH_SHORT).show();
                }else{
                    return b.numEscena;
                }
            }
        }
        return this.numEscena;
    }

    public void setUseMusic(boolean useMusic) {
        this.useMusic = useMusic;
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),this.useMusic);

    }

    public void setUseSFX(boolean useSFX) {
        this.useSFX = useSFX;
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),useSFX);
    }
}
