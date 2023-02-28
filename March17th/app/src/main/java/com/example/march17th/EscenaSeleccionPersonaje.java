package com.example.march17th;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    Boton aceptar;
    Boton atras;
    EscenarioCombate escenarioCombate;

    public EscenaSeleccionPersonaje(int numEscena) {
        super(numEscena);
        escenarioCombate=new EscenarioCombate(R.drawable.characterselection,R.raw.battleteamgalacticgrunt8bitremixthezame);
        selecRandom = new RecuadroSeleccionPersonaje(anchoPantalla/5,altoPantalla/5,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RANDOM.getPersonaje());
        selecRyu= new RecuadroSeleccionPersonaje(anchoPantalla/4,altoPantalla/5,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.RYU.getPersonaje());
        selecTerry = new RecuadroSeleccionPersonaje(anchoPantalla/6,altoPantalla/5,anchoPantalla/10,anchoPantalla/10,ListaPersonajes.TERRY.getPersonaje());
    }

    int onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int aux = super.onTouchEvent(e);
        if (aux != this.numEscena && aux != -1) {
            return aux;
        }
        if(selecRyu.onTouch(x,y)){
            selectedCharacter=selecRyu.personaje;
            selecRyu.isSelected=true;
            selecTerry.isSelected=false;
        }
        if(selecTerry.onTouch(x,y)){
            selectedCharacter=selecTerry.personaje;
            selecTerry.isSelected=true;
            selecRyu.isSelected=false;
        }

        if(aceptar.onTouch(x,y)){
            return aceptar.numEscena;
        }
        if(atras.onTouch(x,y)){
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
    }
}
