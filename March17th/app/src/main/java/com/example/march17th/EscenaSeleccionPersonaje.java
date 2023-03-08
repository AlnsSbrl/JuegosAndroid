package com.example.march17th;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import static com.example.march17th.Constantes.*;
import androidx.core.view.GestureDetectorCompat;
import java.util.ArrayList;

/**
 * Escena donde se escoge el personaje a utilizar y el mapa donde se combate
 */
public class EscenaSeleccionPersonaje extends Escena{


    //todo -> dtap elige al enemigo, tap
    /**
     * Indica el personaje seleccionado por el jugador
     */
    int selectedCharacter=0;

    /**
     * Marcos de los distintos personajes y colección donde se guardan
     */
    RecuadroSeleccionPersonaje selecRyu;
    RecuadroSeleccionPersonaje selecTerry;
    RecuadroSeleccionPersonaje selecRandom;
    ArrayList<RecuadroSeleccionPersonaje> plantelLuchadores; //todo ponerlos en un array normal lol
    /**
     * Colección de los distintos mapas donde se puede luchar
     */
    ArrayList<Bitmap> selectorDeMapas= new ArrayList<>();

    /**
     * Marco que se dibuja alrededor del mapa para que quede más bonito eono
     */
    Bitmap marcoMapa;

    /**
     * Índice del mapa elegido
     */
    int indexMapa=0;
    /**
     * Detector de gestos
     */
    public GestureDetectorCompat detectorDeGestos;

    /**
     * Botones para ir al combate o volver al menú
     */
    Boton aceptar;
    Boton atras;

    Paint p;
    //todo agregar el marcador, donde se ponen los nombres y los recuadros del seleccionado (tanto para player como para enemigo) en grande

    /**
     * Inicia los valores según los parámetros
     * @param numEscena Id de la escena
     * @param escenarioCombate fondo + música
     */
    public EscenaSeleccionPersonaje(int numEscena, EscenarioCombate escenarioCombate) {
        super(numEscena, escenarioCombate);
        for(int i = 0; i<MapaSelector.bitmapsCombate.length; i++){
            selectorDeMapas.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),MapaSelector.bitmapsCombate[i]),anchoPantalla*9/20,altoPantalla*9/30,true));
        }
        marcoMapa= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.marcodisplaymapa),anchoPantalla*9/19,altoPantalla/3,true);
        plantelLuchadores = new ArrayList<>();
        selecRandom = new RecuadroSeleccionPersonaje(anchoPantalla/2-anchoPantalla/20,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RANDOM.getPersonaje());
        selecRyu= new RecuadroSeleccionPersonaje(anchoPantalla/2-(int)(anchoPantalla*1.5/10),altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RYU.getPersonaje());
        selecTerry = new RecuadroSeleccionPersonaje(anchoPantalla/2+(int)(anchoPantalla*0.5/10),altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.TERRY.getPersonaje());
        selecRandom.isSelected=true;
        plantelLuchadores.add(selecRandom);
        plantelLuchadores.add(selecRyu);
        plantelLuchadores.add(selecTerry);
        atras= new Boton(0,altoPantalla*7/10, anchoPantalla*3/10,altoPantalla/10, context.getResources().getString(R.string.Atras).toUpperCase(), EscenasJuego.MENU_PRINCIPAL.getEscena(), true);
        detectorDeGestos = new GestureDetectorCompat(context,new EscenaSeleccionPersonaje.MultiTouchHandler());
        escenarioCombate.mp.setLooping(true);
        p = new Paint();
        p.setAlpha(125);
    }

    /**
     * Inicia los valores según los parámetros
     * @param numEscena id de la escena
     * @param escenarioCombate fondo + música
     * @param isTutorial la escena siguiente pertenece al modo entrenamiento
     */
    public EscenaSeleccionPersonaje(int numEscena,EscenarioCombate escenarioCombate, boolean isTutorial){
        this(numEscena, escenarioCombate);
        aceptar= new Boton(anchoPantalla*7/10,altoPantalla*7/10, anchoPantalla*3/10,altoPantalla/10, context.getResources().getString(R.string.Empezar).toUpperCase(),isTutorial?scn.TUTORIAL.getEscena():scn.COMBATE_REAL.getEscena(), true);

    }

    /**
     * Maneja los eventos de pulsacion que no se manejan con la clase MultitouchHandler
     * @param e evento
     * @return número de la escena a la que se cambia
     */
    int onTouchEvent(MotionEvent e) {

        int x = (int) e.getX();
        int y = (int) e.getY();
        int aux = super.onTouchEvent(e);
        if (aux != this.numEscena && aux != -1) {
            return aux;
        }

        if(aceptar.hitbox.contains(x,y)){
            Log.i("scn", "onTouchEvent??: "+aceptar.numEscena);
            return aceptar.numEscena;
        }
        if(atras.hitbox.contains(x,y)){
            Log.i("scn", "onTouchEvent: "+atras.numEscena);
            return atras.numEscena;
        }
        return this.numEscena;
    }

    /**
     * Dibuja los componentes de la escena en pantalla
     * @param c canvas
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        selecRyu.dibujar(c);
        selecTerry.dibujar(c);
        selecRandom.dibujar(c);
        selecRandom.dibujar(c);
        aceptar.dibujar(c);
        atras.dibujar(c);

        c.drawBitmap(selectorDeMapas.get(indexMapa),(int)(anchoPantalla*2.5/10),altoPantalla/10,null);
        c.drawBitmap(marcoMapa,(int)((anchoPantalla*2.5/10)*95/100),altoPantalla*9/100,p);
    }

    /**
     * Clase que gestiona los eventos de pantalla. Le asigna una animación al jugador según el evento.
     */
    class MultiTouchHandler implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {


            return false;
        }


        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
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

        /**
         * Si se clicka dentro de los límites de un recuadro, selecciona ese personaje y deselecciona cualquiera que estuviese seleccionado previamente
         * @param motionEvent evento
         * @return true
         */
        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            for(RecuadroSeleccionPersonaje r:plantelLuchadores){
                if(r.hitbox.contains(x,y)){
                    for (RecuadroSeleccionPersonaje rr:plantelLuchadores){
                        rr.isSelected=false;
                    }
                    r.isSelected=true;
                    selectedCharacter=r.personaje;
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        /**
         * Cambia de map
         * @param me
         * @param me1
         * @param vX
         * @param vY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent me, MotionEvent me1, float vX, float vY) {

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
