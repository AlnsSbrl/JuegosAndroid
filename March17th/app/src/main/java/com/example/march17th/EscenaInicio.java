package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Locale;

public class EscenaInicio extends Escena{

    Typeface fuente;
    int a=altoPantalla;
    Paint pImagenes;
    int transparecy=0;
    Paint pTexto;
    boolean canSkip=false;
    Bitmap terry=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.terrypantallaso),anchoPantalla*2/3,altoPantalla*2/3,true);
    Bitmap ryu=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ryupantallote),altoPantalla,altoPantalla,true);
    Bitmap triangle=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.trianglenext),altoPantalla/10,altoPantalla/10,true);
    float posX;
    float posY;
    public EscenaInicio(int numEscena, EscenarioCombate escenario) {
        super(numEscena, escenario);
        hasFinished=false;
        returnEscene=EscenasJuego.MENU_PRINCIPAL.getEscena();
        pImagenes = new Paint();
        pImagenes.setAlpha(100);
        pTexto = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fuente= context.getResources().getFont(R.font.sportfieldvarsitystacked);
        }
        pTexto.setTypeface(fuente);
        pTexto.setTextSize(altoPantalla/2);
        pTexto.setTextAlign(Paint.Align.CENTER);
        pTexto.setColor(Color.WHITE);
        posX=anchoPantalla*9/10;
        posY=altoPantalla*8/10;
        this.escenario.mp.setLooping(true);
    }

    @Override
    public void actualizaFisica() {
        transparecy+=3;
        pTexto.setAlpha(Math.min(255,transparecy));
        //todo actualizar arriba abajo el triangulito
        if(transparecy>=255){
            canSkip=true;
            posY++;
            if(transparecy%30==0){
                posY=altoPantalla*8/10;
            }
        }

    }

    @Override
    int onTouchEvent(MotionEvent e) {
        if(canSkip) {
            this.escenario.mp.setLooping(false);
            //hasFinished = true;
            return EscenasJuego.MENU_PRINCIPAL.getEscena();
        }
        return this.numEscena;
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        c.drawBitmap(ryu,anchoPantalla-altoPantalla,0,pImagenes);
        c.drawBitmap(terry,0,altoPantalla/3,pImagenes);
        c.drawText(context.getString(R.string.app_name).toUpperCase(Locale.ROOT),anchoPantalla/2,altoPantalla/2,pTexto);
        //todo dibujar triangulito si hasFinished=true
        if(canSkip){
            c.drawBitmap(triangle,posX,posY,null);
        }
    }
}
