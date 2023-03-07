package com.example.march17th;

import org.jetbrains.annotations.Contract;

/**
 * Enumerado de las distintas escenas
 */
public enum EscenasJuego {

    MENU_PRINCIPAL(0),
    COMBATE_REAL(1),
    SETTINGS(2),
    ELEGIR_PERSONAJES(3),
    TUTORIAL(4),
    VICTORIA(5),
    DERROTA(6),
    CREDITOS(7),
    CALIBRACION(8),
    RECORDS(9),
    INICIO(10);

    private final int escena;

    EscenasJuego(int escena){
        this.escena =escena;
    }

    @Contract(pure = true)
    public int getEscena(){
        return escena;
    }
}
