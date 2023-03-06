package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class EscenaEntrenamiento extends EscenaPelea{

    Boton bSalir;

    public EscenaEntrenamiento(int numEscena, EscenarioCombate escenario, int personajeJugador, int personajeEnemigo) {
        super(numEscena, escenario, personajeJugador, personajeEnemigo);
        bSalir= new Boton(0,0,anchoPantalla/10,altoPantalla/10, context.getResources().getString(R.string.Atras),EscenasJuego.ELEGIR_PERSONAJES.getEscena(), true);
        this.escenario.mp.setLooping(true);
        this.scoreboard= new Scoreboard(player.name, scoreboard.cpuName,true);
    }

    @Override
    public void tomaDecisionDeLaIA() {

    }

    @Override
    public void actualizaFisica() {
        super.actualizaFisica();
        player.vidaActual= player.vidaMaxima;
        enemy.vidaActual=enemy.vidaMaxima;
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        int x = (int)e.getX();
        int y = (int)e.getY();
        if(bSalir.hitbox.contains(x,y)){
            hasFinished=true;
            return bSalir.numEscena;
        }
        return this.numEscena;
    }

    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        bSalir.dibujar(c);
    }
}
