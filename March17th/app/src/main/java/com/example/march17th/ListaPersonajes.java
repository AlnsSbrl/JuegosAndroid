package com.example.march17th;

import org.jetbrains.annotations.Contract;

/**
 * Lista de personajes disponibles
 * @apiNote son muchos, lo sé :)
 */
public enum ListaPersonajes {
    RANDOM(0),
    RYU(1),
    TERRY(2);

    private int personaje;

    ListaPersonajes(int personaje){
        this.personaje =personaje;
    }

    @Contract(pure = true)
    public int getPersonaje(){
        return personaje;
    }
}
