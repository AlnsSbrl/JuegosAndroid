package com.example.march17th;

import org.jetbrains.annotations.Contract;

public enum EscenasJuego {

    MENU_PRINCIPAL(0),
    COMBATE_REAL(1),
    SETTINGS(2),
    ELEGIR_PERSONAJES(6),
    TUTORIAL(3),
    VICTORIA(4),
    DERROTA(5),
    CREDITOS(7),
    CALIBRACION(8);

    private int escena;

    EscenasJuego(int escena){
        this.escena =escena;
    }

    @Contract(pure = true)
    public int getEscena(){
        return escena;
    }
}
