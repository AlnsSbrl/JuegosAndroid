package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import java.util.Locale;

/**
 * Menú principal de la aplicación donde se acceden al resto de escenas del juego
 */
public class EscenaMenu extends Escena{

    /**
     * Id de la escena
     */
    int numEscena=0;

    /**
     * Botones para acceder a distintas escenas
     */
    Boton[] botones=new Boton[5];

    /**
     * Indica si la escena siguiente es el entrenamiento (hay dos botones que mandan a la escena de seleccionar el personaje, pues se refiere a la escena posterior a esa selección si es entrenamiento o combate)
     */
    boolean goesToTutorial;

    /**
     * Inicia los valores según los parámetros
     * @param numEscena id de la escena
     * @param escen fondo y música
     */
    public EscenaMenu(int numEscena, EscenarioCombate escen) {
        super(numEscena,escen);
        escenario = escen;
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getResources().getString(R.string.Tutorial).toUpperCase(Locale.ROOT), EscenasJuego.ELEGIR_PERSONAJES.getEscena(),true);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.PlayAgainstComputer).toUpperCase(Locale.ROOT), EscenasJuego.ELEGIR_PERSONAJES.getEscena(),true);
        botones[2]= new Boton(anchoPantalla/4,3*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Settings).toUpperCase(Locale.ROOT), EscenasJuego.SETTINGS.getEscena(),true);
        botones[3]= new Boton(anchoPantalla/4,4*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Records).toUpperCase(Locale.ROOT),EscenasJuego.RECORDS.getEscena(), true);
        botones[4]= new Boton(anchoPantalla/4,5*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.Credits).toUpperCase(Locale.ROOT),EscenasJuego.CREDITOS.getEscena(), true);
    }

    /**
     * Dibuja los componentes de la escena en pantalla
     * @param canvas canvas
     */
    @Override
    public void dibuja(Canvas canvas) {
        canvas.drawBitmap(escenario.fondo,0,0,null);
        for (Boton boton: botones) {
            boton.dibujar(canvas);
        }
        canvas.save();
        canvas.restore();
    }

    /**
     * Gestiona el toque de los botones
     * @param e evento de toque
     * @return la escena a la que se va
     */
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
                goesToTutorial= (b== botones[0]);
                return b.numEscena;
            }
        }
        return this.numEscena;
    }
}