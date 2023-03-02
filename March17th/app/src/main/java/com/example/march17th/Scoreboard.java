package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.currentPlayerWins;
import static com.example.march17th.Constantes.currentCPUWins;
import static com.example.march17th.Constantes.tiempoCombate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

public class Scoreboard {
    Bitmap marco;
    int playerWins;
    String playerName;
    int cpuWins;
    String cpuName;
    int currentTime;
    int height;
    int width;
    boolean isTutorial;
    Paint pCountDown;
    Paint pPerso;
    Typeface clockFont;

    public Scoreboard(String playerName,String cpuName,boolean isTutorial){
        this.marco=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.scoreboard),anchoPantalla*8/10,altoPantalla/4,true);
        this.playerWins=currentPlayerWins;
        this.cpuWins=currentCPUWins;
        this.currentTime=tiempoCombate;
        this.playerName=playerName;
        this.cpuName=cpuName;
        this.isTutorial=isTutorial;
        height= altoPantalla/4;
        width=anchoPantalla*2/10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            clockFont= context.getResources().getFont(R.font.attfshingoproultra);
        }
        pPerso = new Paint();
        pPerso.setTextSize(height*2/7);
        pPerso.setColor(Color.WHITE);
        pPerso.setTypeface(clockFont);
        pPerso.setTextAlign(Paint.Align.CENTER);
        pCountDown = new Paint();
        pCountDown.setTextSize((height/2)*95/100);
        pCountDown.setColor(Color.WHITE);
        pCountDown.setTypeface(clockFont);
        pCountDown.setTextAlign(Paint.Align.CENTER);
    }

    public void dibuja(Canvas c){
        c.drawBitmap(marco,anchoPantalla/10,0,null);
        //esta condicion es para luego poder poner esto en el tutorial y no mostrar ningún número ahí
        if(!isTutorial){
            c.drawText(String.valueOf(currentTime),anchoPantalla/2,(height)/2+ pCountDown.getTextSize()/2, pCountDown);
            c.drawText(String.valueOf(playerWins),anchoPantalla*7/44,(height)/2+pPerso.getTextSize()/2,pPerso);
            c.drawText(String.valueOf(cpuWins), anchoPantalla*37/44, (height)/2+pPerso.getTextSize()/2,pPerso);
        }else{
            c.drawText("∞",anchoPantalla/2,(height)/2+ pCountDown.getTextSize()/2, pCountDown);

        }
    }

    public void actualizaTiempo(){
        currentTime--;
    }
}
