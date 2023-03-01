package com.example.march17th;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import static com.example.march17th.Constantes.*;

import java.util.ArrayList;

public class EscenaSeleccionPersonaje extends Escena{

    //todo implementar un cuadrado donde se vea el escenario y se cambia con fling
    //todo -> dtap elige al enemigo, tap
    int nextScene;
    int selectedCharacter=-1;
    int selectedMap=-1;
    RecuadroSeleccionPersonaje selecRyu;
    RecuadroSeleccionPersonaje selecTerry;
    RecuadroSeleccionPersonaje selecRandom;
    ArrayList<Bitmap> selectorDeMapas= new ArrayList<>(); //todo crear una clase (o metodo) al que le pasas un numero y segun ese numero te devuelve un escenario de combate (tb hacer enumerado)
    ArrayList<RecuadroSeleccionPersonaje> plantelLuchadores;

    Boton aceptar;
    Boton atras;
    EscenarioCombate escenarioCombate;
    //todo agregar el marcador, donde se ponen los nombres y los recuadros del seleccionado (tanto para player como para enemigo) en grande

    public EscenaSeleccionPersonaje(int numEscena) {
        super(numEscena);
        plantelLuchadores = new ArrayList<>();
        escenarioCombate=new EscenarioCombate(R.drawable.characterselection,R.raw.battleteamgalacticgrunt8bitremixthezame);
        selecRandom = new RecuadroSeleccionPersonaje(anchoPantalla/2,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RANDOM.getPersonaje());
        selecRyu= new RecuadroSeleccionPersonaje(anchoPantalla/2-anchoPantalla/10,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RYU.getPersonaje());
        selecTerry = new RecuadroSeleccionPersonaje(anchoPantalla/2+anchoPantalla/10,altoPantalla/2,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.TERRY.getPersonaje());
        plantelLuchadores.add(selecRandom);
        plantelLuchadores.add(selecRyu);
        plantelLuchadores.add(selecTerry);
    }
    public EscenaSeleccionPersonaje(int numEscena, boolean isTutorial){
        this(numEscena);
        if(isTutorial){
            nextScene=scn.TUTORIAL.getEscena();
        }else{
            nextScene=scn.COMBATE_REAL.getEscena();
        }
    }
    int onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int aux = super.onTouchEvent(e);
        if (aux != this.numEscena && aux != -1) {
            return aux;
        }

        //todo: si pulsas fuera te selecciona random, entonces compruebo ANTES si se pulsa para ir al combate
        for(RecuadroSeleccionPersonaje r:plantelLuchadores){
            r.isSelected=false;
            if(r.onTouch(x,y)){
                r.isSelected=true;
                selectedCharacter=r.personaje;
            }
        }

/*
        if(aceptar.onTouch(x,y)){
            return aceptar.numEscena;
        }
        if(atras.onTouch(x,y)){
            return atras.numEscena;
        }*/
        return this.numEscena;
    }
    @Override
    public void actualizaFisica() {
        super.actualizaFisica();
    }

    @Override
    public void dibuja(Canvas c) {
        c.drawBitmap(this.escenarioCombate.fondo, 0,0,null);
        selecRyu.dibujar(c);
        selecTerry.dibujar(c);
        selecRandom.dibujar(c);
        selecRandom.dibujar(c);
        Log.i("div", "dibuja: ");
    }
}
