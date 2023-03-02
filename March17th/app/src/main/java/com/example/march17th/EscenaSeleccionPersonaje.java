package com.example.march17th;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import static com.example.march17th.Constantes.*;

import androidx.core.view.GestureDetectorCompat;

import java.util.ArrayList;

public class EscenaSeleccionPersonaje extends Escena{


    //todo -> dtap elige al enemigo, tap
    //todo -> hacer que si no se selecciona ningún personaje se escoja aleatoriamente (es un motivo por el que peta)
    int selectedCharacter=-1;
    int selectedMap=3;
    RecuadroSeleccionPersonaje selecRyu;
    RecuadroSeleccionPersonaje selecTerry;
    RecuadroSeleccionPersonaje selecRandom;
    ArrayList<RecuadroSeleccionPersonaje> plantelLuchadores;
    ArrayList<Bitmap> selectorDeMapas= new ArrayList<>();
    int indexMapa=0;
    public GestureDetectorCompat detectorDeGestos;
    Boton aceptar;
    Boton atras;
    Bitmap marcoMapa;
    //todo agregar el marcador, donde se ponen los nombres y los recuadros del seleccionado (tanto para player como para enemigo) en grande

    public EscenaSeleccionPersonaje(int numEscena, EscenarioCombate escenarioCombate) {
        super(numEscena, escenarioCombate);
        for(int i = 0; i<MapaSelector.bitmapsCombate.length; i++){
            selectorDeMapas.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),MapaSelector.bitmapsCombate[i]),anchoPantalla/2,altoPantalla/3,true));
        }
        marcoMapa= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.marcoseleccionmapa),anchoPantalla/2,altoPantalla/3,true);
        plantelLuchadores = new ArrayList<>();
        selecRandom = new RecuadroSeleccionPersonaje(anchoPantalla/2,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RANDOM.getPersonaje());
        selecRyu= new RecuadroSeleccionPersonaje(anchoPantalla/2-anchoPantalla/10,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RYU.getPersonaje());
        selecTerry = new RecuadroSeleccionPersonaje(anchoPantalla/2+anchoPantalla/10,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.TERRY.getPersonaje());
        plantelLuchadores.add(selecRandom);
        plantelLuchadores.add(selecRyu);
        plantelLuchadores.add(selecTerry);
        atras= new Boton(0,altoPantalla*7/10, anchoPantalla*3/10,altoPantalla/10, context.getResources().getString(R.string.Atras).toUpperCase(),scn.MENU_PRINCIPAL.getEscena(), true);

        detectorDeGestos = new GestureDetectorCompat(context,new EscenaSeleccionPersonaje.MultiTouchHandler());
    }
    public EscenaSeleccionPersonaje(int numEscena,EscenarioCombate escenarioCombate, boolean isTutorial){
        this(numEscena, escenarioCombate);
        aceptar= new Boton(anchoPantalla*7/10,altoPantalla*7/10, anchoPantalla*3/10,altoPantalla/10, context.getResources().getString(R.string.Empezar).toUpperCase(),isTutorial?scn.TUTORIAL.getEscena():scn.COMBATE_REAL.getEscena(), true);

    }
    int onTouchEvent(MotionEvent e) {

        int x = (int) e.getX();
        int y = (int) e.getY();
        int aux = super.onTouchEvent(e);
        if (aux != this.numEscena && aux != -1) {
            return aux;
        }

        /*
        //todo gestionar el fling desde aqui????
        if(!detectorDeGestos.onTouchEvent(e)){
            int accion=e.getActionMasked();
            switch (accion){

            }
        }*/

        if(aceptar.onTouch(x,y)){
            Log.i("scn", "onTouchEvent: "+aceptar.numEscena);
            return aceptar.numEscena;
        }
        if(atras.onTouch(x,y)){
            Log.i("scn", "onTouchEvent: "+atras.numEscena);
            return atras.numEscena;
        }
        return this.numEscena;
    }
    @Override
    public void actualizaFisica() {
        super.actualizaFisica();
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        //c.drawBitmap(this.escenarioCombate.fondo, 0,0,null);
        selecRyu.dibujar(c);
        selecTerry.dibujar(c);
        selecRandom.dibujar(c);
        selecRandom.dibujar(c);
        aceptar.dibujar(c);
        atras.dibujar(c);

        c.drawBitmap(selectorDeMapas.get(indexMapa),(int)(anchoPantalla*2.5/10),altoPantalla/10,null);
        c.drawBitmap(marcoMapa,(int)(anchoPantalla*2.5/10),altoPantalla/10,null);
    }

    /**
     * Clase que gestiona los eventos de pantalla. Le asigna una animación al jugador según el evento.
     */
    class MultiTouchHandler implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            for(RecuadroSeleccionPersonaje r:plantelLuchadores){
                r.isSelected=false;
                if(r.onTouch(x,y)){
                    r.isSelected=true;
                    selectedCharacter=r.personaje;
                }
            }
            return false;
        }
        //todo asignar a player


        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            //todo asignar a enemigo
            return true;
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
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent me, MotionEvent me1, float vX, float vY) {

            Log.i("scn", "cambia mapa: "+indexMapa);
            if(me.getX()<me1.getX()){
                if(indexMapa==0){
                    indexMapa=selectorDeMapas.size()-1;
                }else{
                    indexMapa--;
                }
            }else{
                if(indexMapa==selectorDeMapas.size()-1){
                    indexMapa=0;
                }else{
                    indexMapa++;
                }
            }
            return true;
        }
    }
}
