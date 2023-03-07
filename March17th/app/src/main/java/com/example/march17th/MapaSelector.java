package com.example.march17th;

public class MapaSelector {

    public static int[] bitmapsCombate = new int[]{R.drawable.schweppes,R.drawable.mezquita,R.drawable.montana,R.drawable.mishimadojo,R.drawable.swamp};
    private static final int[] musicasCombates = new int[]{R.raw.battleteamgalacticgrunt8bitremixthezame,R.raw.megalovania,R.raw.spearofjustice,R.raw.thezameteamgalacticremastered, R.raw.subnauticaexosuit};

    private static final int[] bitmapsMenu = new int[]{
            R.drawable.characterselection,
            R.drawable.mezquita,
            R.drawable.mishimadojo,
            R.drawable.backgroundpantallote,
            R.drawable.backgroundpantallote
    };
    private static final int[] musicasMenu = new int[]{
            R.raw.coffeetalkadaywithcoffee,
            R.raw.coffeetalksunsetinthecity,
            R.raw.coffeetalkwaytoosoon,
            R.raw.coffeetalkadaywithcoffee,
            R.raw.thezamepressstarttitlescreen
    };
    //todo meter la cancion del karmaggan???????
    //seleccion perso = driftveil
    /*MENU_PRINCIPAL(0),
    CHAR_SELECT(1),
    OPCIONES(2),
    CREDITOS(3),
    RECORDS(4),
    INICIO(5);*/
    public static EscenarioCombate consigueMapa(int num){
        return new EscenarioCombate(bitmapsCombate[num], musicasCombates[num]);
    }
    public static EscenarioCombate consigueMenu(int num){
        return new EscenarioCombate(bitmapsMenu[num], musicasMenu[num]);
    }
}
