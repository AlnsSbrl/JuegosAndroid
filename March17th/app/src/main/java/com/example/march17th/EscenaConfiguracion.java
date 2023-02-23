package com.example.march17th;

import static com.example.march17th.Constantes.ac;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.scn;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Locale;

public class EscenaConfiguracion extends Escena{

    Boton[] botones;
    Bitmap loading=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.loading),anchoPantalla/10,anchoPantalla/10,true);

    EscenarioCombate escn;
    Boton[] btnIdiomas;
    String lang;
    String[] languages= new String[]{"es","en","pt","de","zh"};
    int[] resBtnIdioma= new int[]{R.string.ES,R.string.EN,R.string.GL,R.string.DE,R.string.ZH};
    boolean useMusic;//=true;
    boolean useSFX;//=true;
    boolean dibuja=false;
    Matrix matrix = new Matrix();

    public EscenaConfiguracion(int numEscena) {
        super(numEscena);
        escn=new EscenarioCombate(R.drawable.snow,R.raw.megalovania);
        useMusic=Constantes.emplearMusicaFondo;
        useSFX=Constantes.emplearSFX;
        Init();
    }

    /**
     * Crea los botones e impide que se dibujen mientras se están actualizando
     * @apiNote esto se pone fuera del constructor para actualizar los botones al cambiar de idioma (cambiar los textos y el botón del idioma seleccionado)
     */
    public void Init(){
        dibuja=false;
        Resources res = context.getResources();
        android.content.res.Configuration conf = res.getConfiguration();
        lang = conf.locale.toString().split("_")[0];
        btnIdiomas=new Boton[5];
        for (int i =0; i<languages.length;i++){
            btnIdiomas[i]= new Boton(anchoPantalla/4+(i*anchoPantalla/10),5*altoPantalla/6-altoPantalla/10,anchoPantalla*2/30,altoPantalla/10, context.getString(resBtnIdioma[i]),scn.SETTINGS.getEscena(),!lang.equals(languages[i]));
        }
        botones=new Boton[5];
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic),scn.SETTINGS.getEscena(),useMusic);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),useSFX);
        botones[2]= new Boton(anchoPantalla/4,4*altoPantalla/6-altoPantalla/10,anchoPantalla/4,altoPantalla/10,context.getResources().getString(R.string.CancelOptions).toUpperCase(Locale.ROOT),scn.MENU_PRINCIPAL.getEscena(),true);
        botones[3]= new Boton(anchoPantalla/2,4*altoPantalla/6-altoPantalla/10,anchoPantalla/4,altoPantalla/10,context.getResources().getString(R.string.SaveOptions).toUpperCase(Locale.ROOT),scn.MENU_PRINCIPAL.getEscena(),true);
        botones[4]= new Boton(anchoPantalla/4,3*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.GyroOption).toUpperCase(Locale.ROOT),scn.CALIBRACION.getEscena(), true);
        dibuja=true;
    }

    /**
     * Cambia el idioma
     * @param idioma
     * @author Javier Conde
     */
    public void CambiaIdioma(String idioma){
            Resources res=context.getResources();
            DisplayMetrics dm=res.getDisplayMetrics();
            android.content.res.Configuration conf=res.getConfiguration();
            //conf.locale=new Locale(idioma.toLowerCase());
            //conf.setLocale(new Locale(idioma.toLowerCase()));
            conf.setLocale(Locale.ENGLISH);
            res.updateConfiguration(conf, dm);
            //Init();
    }

    /**
     * Dibuja los botones cuando están creados. Si están en proceso de creación dibuja un símbolo de carga que da vueltas hasta que termine
     * @param c
     */
    @Override
    public void dibuja(Canvas c) {
        if(dibuja) {
            super.dibuja(c);
            c.drawBitmap(escn.fondo, 0, 0, null);
            for (Boton b : botones) {
                b.dibujar(c);
            }
            for (Boton b : btnIdiomas) {
                b.dibujar(c);
            }
        }else{
            //todo preguntar como hacer que rote bien la imagen
            ///podria hacer un array de bitmaps y tiraria pero bueh
            //basicamente solo me rota al crear el bitmap, pero es que me desplaza al bitmap a la otra punta??
            matrix.reset();
            //matrix.postRotate(30,loading.getWidth()/2,loading.getHeight()/2);
            matrix.preRotate(30,loading.getWidth()/2,loading.getWidth()/2);
            c.setMatrix(matrix);
            //loading = Bitmap.createBitmap(loading,0,0,loading.getWidth(),loading.getHeight(), matrix,true);
            c.drawBitmap(loading,0,0,null);
            c.setMatrix(null);
        }
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        int x= (int)e.getX();
        int y=(int)e.getY();
        int aux=super.onTouchEvent(e);
        if(aux!=this.numEscena &&aux!=-1){
            return aux;
        }
        for (Boton b:botones) {
            if(b.hitbox.contains(x,y)){
                if(b==botones[0]){
                    this.setUseMusic(!this.useMusic);
                }else if(b==botones[1]) {
                    this.setUseSFX(!useSFX);
                }else{
                    //todo guardar shared prefferences y constantes
                    return b.numEscena;
                }
            }
        }
        for(Boton b:btnIdiomas){
            if(b.hitbox.contains(x,y)){
                if(b==btnIdiomas[0]){
                    CambiaIdioma("es_ES");
                }
                if(b==btnIdiomas[1]){
                    CambiaIdioma("en");
                    Log.i(" en", "onTouchEvent: ");
                }
                if(b==btnIdiomas[2]){
                    CambiaIdioma("pt_PT");
                }
                if(b==btnIdiomas[3]){
                    CambiaIdioma("de_DE");
                }
                if(b==btnIdiomas[4]){
                    CambiaIdioma("zh_CN");
                }
            }
        }
        return this.numEscena;
    }

    public void setUseMusic(boolean useMusic) {
        this.useMusic = useMusic;
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),this.useMusic);

    }

    public void setUseSFX(boolean useSFX) {
        this.useSFX = useSFX;
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT),scn.SETTINGS.getEscena(),useSFX);
    }
}
