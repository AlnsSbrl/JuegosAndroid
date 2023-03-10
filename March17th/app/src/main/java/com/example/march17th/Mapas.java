package com.example.march17th;

import org.jetbrains.annotations.Contract;

/**
 * Lista de mapas donde se puede combatir
 */
public enum Mapas {

    EDIFICIO(0),
    MEZQUITA(1),
    SNOW(2),
    DOJO(3),
    SWAMP(4);

    private int mapa;

    Mapas(int mapa){
        this.mapa =mapa;
    }

    @Contract(pure = true)
    public int getMapa(){
        return mapa;
    }
}
