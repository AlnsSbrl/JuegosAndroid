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

/**
 * Representa el marco que existe en el combate donde se muestran las victorias de cada personaje y el tiempo de combate
 */
public class Scoreboard {

    /**
     * Imagen del recuadro
     */
    Bitmap marco;

    /**
     * Victorias actuales del jugador
     */
    int playerWins;

    /**
     * Nombre del personaje del jugador
     */
    String playerName;

    /**
     * Victorias actuales de la CPU
     */
    int cpuWins;

    /**
     * Nombre del personaje de la CPU
     */
    String cpuName;

    /**
     * Tiempo restante del combate
     */
    int currentTime;

    /**
     * Parámetros de alto y ancho que ocupa el componente
     */
    int height;
    int width;

    /**
     * Indica si el marco se pondrá en el combate o en el modo entrenamiento (donde no habrá límite de tiempo ni victorias/derrotas)
     */
    boolean isTutorial;

    /**
     * Paints para dibujar
     */
    Paint pCountDown;
    Paint pPerso;

    /**
     * Fuente de texto que se usa
     */
    Typeface clockFont;

    /**
     * Inicializa el componente según los parámetros
     * @param playerName nombre del personaje del jugador
     * @param cpuName nombre del personaje de la cpu
     * @param isTutorial indica si estará representado en el training mode o en el combate
     */
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

    /**
     * Dibuja los componentes en pantalla
     * @param c canvas
     */
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
