package com.example.march17th;

import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import java.util.Locale;

/**
 * Escena donde se cambian los ajustes de usuario
 */
public class EscenaConfiguracion extends Escena{

    /**
     * Distintos botones de configuracion (uso de musica, uso de sfx, giroscopio, volver al menu)
     */
    Boton[] botones;

    /**
     * Imágenes que se muestran cuando se cambian los botones de idioma
     */
    Bitmap[] loading;
    /**
     * frame que indica qué imagen se dibuja mientras cambian los botones de idioma
     */
    int frameDibuja=0;

    /**
     * Fondo + musica
     */
    EscenarioCombate escn;

    /**
     * Botones de los distintos idiomas
     */
    Boton[] btnIdiomas;

    /**
     * variable auxiliar para determinar que boton está "pulsado" según el idioma
     */
    String lang;
    /**
     * auxiliar para decir que idioma tiene cada boton
     */
    String[] languages= new String[]{"es","en","pt","de","zh"};

    /**
     * emojiis de selección de idioma
     */
    int[] resBtnIdioma= new int[]{R.string.ES,R.string.EN,R.string.GL,R.string.DE,R.string.ZH};

    /**
     * permite al usuario cambiar si se usan o no los sonidos
     */
    boolean useMusic;
    boolean useSFX;

    /**
     * cuando se cambia el idioma deja de dibujar los botones mientras se generan de nuevo
     */
    boolean dibuja=false;


    /**
     * Inicia los valores según los parámetros
     * @param numEscena id de la escena
     * @param escen fondo y música
     */
    public EscenaConfiguracion(int numEscena, EscenarioCombate escen) {
        super(numEscena,escen);
        escn=new EscenarioCombate(R.drawable.snow,R.raw.megalovania);
        useMusic=Constantes.emplearMusicaFondo;
        useSFX=Constantes.emplearSFX;
        iniciaLoad();
        Init();
    }

    /**
     * Crea los botones e impide que se dibujen mientras se están actualizando
     * @apiNote esto se pone fuera del constructor para actualizar los botones al cambiar de idioma (cambiar los textos y el botón del idioma seleccionado)
     */
    public void Init(){
        dibuja=false;
        lang = Constantes.getIdioma().split("_")[0];
        btnIdiomas=new Boton[5];
        for (int i =0; i<languages.length;i++){
            btnIdiomas[i]= new Boton(anchoPantalla/4+(i*anchoPantalla/10),5*altoPantalla/6-altoPantalla/10,anchoPantalla*2/30,altoPantalla/10, context.getString(resBtnIdioma[i]), EscenasJuego.SETTINGS.getEscena(),!lang.equals(languages[i]));
        }
        botones=new Boton[4];
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic), EscenasJuego.SETTINGS.getEscena(),useMusic);
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT), EscenasJuego.SETTINGS.getEscena(),useSFX);
        botones[2]= new Boton(anchoPantalla/4,3*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.GyroOption).toUpperCase(Locale.ROOT), EscenasJuego.CALIBRACION.getEscena(), true);
        botones[3]= new Boton(anchoPantalla/4,4*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.SaveOptions).toUpperCase(Locale.ROOT), EscenasJuego.MENU_PRINCIPAL.getEscena(),true);
        dibuja=true;
    }

    /**
     * Inicia los valores de la imagen que aparece cuando no se dibujan los botones y estos se crean
     */
    public void iniciaLoad(){
        loading = new Bitmap[4];
        loading[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cagando),anchoPantalla/15,anchoPantalla/15,true);
        loading[1]=volteaImagen(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cagando),anchoPantalla/15,anchoPantalla/15,true),false,false);
        loading[2]=volteaImagen(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cagando),anchoPantalla/15,anchoPantalla/15,true),true,false);
        loading[3]=volteaImagen(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cagando),anchoPantalla/15,anchoPantalla/15,true),true,true);
    }

    /**
     * Gira la imagen que se pasa como parámetro cierto número de grados. Por defecto la gira 90º.
     * @param frameMov imagen que se voltea
     * @param giraUnPoco true: dobla los grados que se gira la imagen
     * @param giraUnPocoMas true: dobla los grados que se gira la imagen
     * @return la imagen volteada
     */
    public Bitmap volteaImagen(Bitmap frameMov,boolean giraUnPoco,boolean giraUnPocoMas){
        Matrix matrix = new Matrix();
        float degrees=90;
        if(giraUnPoco) degrees=degrees*2;
        if(giraUnPocoMas) degrees = degrees*2;
        matrix.setRotate(degrees);
        return Bitmap.createBitmap(frameMov,0,0,frameMov.getWidth(),frameMov.getHeight(), matrix,true);
    }

    /**
     * Dibuja los botones cuando están creados. Si están en proceso de creación dibuja un símbolo de carga que da vueltas hasta que termine
     * @param c canvas
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
            frameDibuja++;
            c.drawBitmap(loading[frameDibuja%loading.length],0,0,null);

        }
    }

    /**
     * Gestiona la pulsación de los botones
     * @param e evento
     * @return numero de la escena a la que se cambia
     */
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
                    Constantes.emplearSFX=this.useSFX;
                    Constantes.emplearMusicaFondo= this.useMusic;
                    Constantes.guardarValores();
                    return b.numEscena;
                }
            }
        }
        for(Boton b:btnIdiomas){
            if(b.hitbox.contains(x,y)){
                if(b==btnIdiomas[0]){
                    Constantes.setIdioma("es_ES");
                }
                if(b==btnIdiomas[1]){
                    Constantes.setIdioma("en");
                }
                if(b==btnIdiomas[2]){
                    Constantes.setIdioma("pt_PT");
                }
                if(b==btnIdiomas[3]){
                    Constantes.setIdioma("de_DE");
                }
                if(b==btnIdiomas[4]){
                    Constantes.setIdioma("zh_CN");
                }
                Init();
            }
        }
        return this.numEscena;
    }

    /**
     * cambia el valor useMusic y cambia el color del botón para indicar su valor
     * @param useMusic true: se usa música. False: no se usa música
     */
    public void setUseMusic(boolean useMusic) {
        this.useMusic = useMusic;
        botones[0]=new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.UseMusic).toUpperCase(Locale.ROOT), EscenasJuego.SETTINGS.getEscena(),this.useMusic);
        if(useMusic){
            this.escenario.Reproduce();
        }else{
            this.escenario.Pausa();
        }
    }

    /**
     * cambia el valor de useSFX y cambia el color del botón para indicar su valor
     * @param useSFX True: se usan los sfx. False: no se usan los sfx
     */
    public void setUseSFX(boolean useSFX) {
        this.useSFX = useSFX;
        botones[1]= new Boton(anchoPantalla/4,2*altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10,context.getResources().getString(R.string.UseSFX).toUpperCase(Locale.ROOT), EscenasJuego.SETTINGS.getEscena(),useSFX);
    }
}
