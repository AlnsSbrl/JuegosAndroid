package com.example.march17th;

import java.util.ArrayList;

public class MapaSelector {

    static ArrayList<EscenarioCombate> mapasDisponibles;

    private static int[] bitmaps = new int[]{R.drawable.schweppes,R.drawable.mezquita,R.drawable.montana,R.drawable.mishimadojo,R.drawable.swamp};
    private static int[] musicas = new int[]{R.raw.battleteamgalacticgrunt8bitremixthezame,R.raw.megalovania,R.raw.spearofjustice,R.raw.thezameteamgalacticremastered, R.raw.subnauticaexosuit};

    public static void Init(){

        mapasDisponibles= new ArrayList<>();
        for(int i=0;i< bitmaps.length;i++){
            mapasDisponibles.add(new EscenarioCombate(bitmaps[i],musicas[i]));
        }
    }
    public static EscenarioCombate consigueMapa(int num){
        return mapasDisponibles.get(num);
    }
}
