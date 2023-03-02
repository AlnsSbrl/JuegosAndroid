package com.example.march17th;

public class MapaSelector {

    public static int[] bitmapsCombate = new int[]{R.drawable.schweppes,R.drawable.mezquita,R.drawable.montana,R.drawable.mishimadojo,R.drawable.swamp};
    private static int[] musicasCombates = new int[]{R.raw.battleteamgalacticgrunt8bitremixthezame,R.raw.megalovania,R.raw.spearofjustice,R.raw.thezameteamgalacticremastered, R.raw.subnauticaexosuit};

    private static int[] bitmapsMenu = new int[]{R.drawable.characterselection,R.drawable.mezquita,R.drawable.mishimadojo};
    private static int[] musicasMenu = new int[]{R.raw.coffeetalkadaywithcoffee,R.raw.coffeetalksunsetinthecity,R.raw.coffeetalkwaytoosoon};
    //todo meter la cancion del karmaggan???????

    public static EscenarioCombate consigueMapa(int num){
        return new EscenarioCombate(bitmapsCombate[num], musicasCombates[num]);
    }
    public static EscenarioCombate consigueMenu(int num){
        return new EscenarioCombate(bitmapsMenu[num], musicasMenu[num]);
    }
}
