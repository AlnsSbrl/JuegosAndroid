package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.R.string.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.MotionEvent;

import java.util.Locale;

public class EscenaRecords extends Escena{

    int[] datosAEnseñar = new int[]{victorias,derrotas,porcentaje,bestracha,rachaactual};
    String[] textosRecords = new String[datosAEnseñar.length];
    String[] datosRecords = new String[datosAEnseñar.length];
    Paint pTexto;
    Typeface fnt;


    public EscenaRecords(int numEscena, EscenarioCombate escenario) {
        super(numEscena, escenario);
        Constantes.guardarValores();
        Constantes.leerValores();
        escenario.fondo= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),escenario.resourceBitmap),anchoPantalla,altoPantalla,true);

        for (int i = 0; i < textosRecords.length; i++) {
            textosRecords[i]= context.getResources().getString(datosAEnseñar[i]).toUpperCase(Locale.ROOT);
        }

        datosRecords[0]= String.valueOf(Constantes.totalPlayerWins);
        datosRecords[1]= String.valueOf(Constantes.totalCPUWins);

        datosRecords[2]= (Constantes.totalPlayerWins*100/(Constantes.totalCPUWins+Constantes.totalPlayerWins))+"%";
        datosRecords[3]= String.valueOf(Constantes.recordVictoriasConsecutivas);
        datosRecords[4]= String.valueOf(Constantes.rachaActual);

        pTexto= new Paint();
        pTexto.setTextSize(altoPantalla/20);
        pTexto.setTextAlign(Paint.Align.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fnt= context.getResources().getFont(R.font.attfshingoproultra);
        }
        pTexto.setTypeface(fnt);
        pTexto.setColor(Color.WHITE);
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        return EscenasJuego.MENU_PRINCIPAL.getEscena();
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        for (int i=0;i<textosRecords.length;i++) {
            c.drawText(textosRecords[i], anchoPantalla/2,((int)(altoPantalla*2/10))+(int)(i*altoPantalla*1.455/10),pTexto);
        }
        for (int i=0;i<datosRecords.length;i++) {
            c.drawText(datosRecords[i], anchoPantalla/2,((int)(altoPantalla*2.6/10))+(int)(i*altoPantalla*1.455/10),pTexto);
        }
    }
}
