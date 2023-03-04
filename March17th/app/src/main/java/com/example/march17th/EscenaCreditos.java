package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.R.string.*;
import static android.graphics.Color.*;

import android.graphics.Canvas;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;


public class EscenaCreditos extends Escena {

    /**
     * Clase que detecta los gestos de la pantalla
     */
    public GestureDetectorCompat detectorDeGestos;
    int a=altoPantalla;
    TextoCreditos[] textosCreditos;
    int[] recursosDeLosTextos= new int[]{app_name,StaffCredits,
            LeadProgramming,pablo,
            GameDesign,pablo,
            MusicArtists,
            TobyFox,Megalovania,Spear,
            AndrewJeremy,coffe1,cof2,cof3,
            Simon,Exosuit,

    };
    int[] textSizes = new int[]{a/4,a/6,
            a/7,a/12,
            a/7,a/12,
            a/6,
            a/7,a/12,a/12,
            a/7,a/12,a/12,a/12,
            a/7,a/12
    };
    int[] coloresTexto = new int[]{YELLOW,WHITE,
            WHITE,GRAY,
            WHITE,GRAY,
            WHITE,
            WHITE,GRAY,GRAY,
            WHITE,GRAY,GRAY,GRAY,
            WHITE,GRAY};
    boolean moveFaster=false;
     //???????????????????????

    /*
*  <string name="StaffCredits" translatable="false">Staff Credits</string>
    <string name="pablo" translatable="false">Pablo Alonso Sobral</string>
    <string name="LeadProgramming" translatable="false">Lead Programming</string>
    <string name="GameDesign" translatable="false">Game Design</string>
    <string name="MusicArtists" translatable="false">Music Artists</string>
    <string name="TheZame" translatable="false">The Zame</string>
    <string name="TobyFox" translatable="false">Toby Fox</string>
    <string name="AndrewJeremy" translatable="false">Andrew Jeremy</string>
    <string name="Simon" translatable="false">Simon Chylinski</string>
    <string name="Canciones" translatable="false">Songs</string>
    <string name="Megalovania" translatable="false">\"Megalovania\" from Undertale</string>
    <string name="Spear" translatable="false">\"Spear of Justice\" from Undertale</string>
    <string name="Exosuit" translatable="false">\"Exosuit\" from Subnautica</string>
    <string name="coffe1" translatable="false">\"A day with Coffee\" from Coffee Talk</string>
    <string name="cof2" translatable="false">\"Sunset in the city\" from Coffee Talk</string>
    <string name="cof3"*/
    public EscenaCreditos(int numEscena, EscenarioCombate escenario) {
        super(numEscena, escenario);
        textosCreditos = new TextoCreditos[recursosDeLosTextos.length];
        for (int i = 0; i< recursosDeLosTextos.length; i++){
            textosCreditos[i]=new TextoCreditos(altoPantalla+i*altoPantalla/5+(i!=0?textSizes[i]:0), recursosDeLosTextos[i],textSizes[i],coloresTexto[i]);
        }
        detectorDeGestos = new GestureDetectorCompat(context,new EscenaCreditos.MultiTouchHandler());
        hasFinished=false;
        returnEscene=EscenasJuego.MENU_PRINCIPAL.getEscena();
    }

    @Override
    public void actualizaFisica() {
        for(TextoCreditos t: textosCreditos){
            t.mover(moveFaster);
        }

        if(textosCreditos[textosCreditos.length-1].posY<0){
            hasFinished=true;
        }
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        for(TextoCreditos t: textosCreditos){
            t.dibujar(c);
        }
    }

    class MultiTouchHandler implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{


        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        /**
         * Cambia la velocidad a la que se mueven los créditos al hacer doble tap
         * @param motionEvent evento
         * @return false
         */
        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            moveFaster=!moveFaster;
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }
}
