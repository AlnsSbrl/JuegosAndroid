package com.example.march17th;

import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import static com.example.march17th.Constantes.FPS;
import static com.example.march17th.Constantes.ac;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.scn;
import static com.example.march17th.Constantes.umbralDefaultEnY;
import static com.example.march17th.Constantes.umbralDefaultEnX;
import static com.example.march17th.Constantes.umbralSensibilidadX;
import static com.example.march17th.Constantes.umbralSensibilidadY;
import static com.example.march17th.Constantes.valorDefaultInclinacionX;
import static com.example.march17th.Constantes.valorDefaultInclinacionY;
import static com.example.march17th.Constantes.valorInicialInclinacionX;
import static com.example.march17th.Constantes.valorInicialInclinacionY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.Locale;

/**
 * Escena donde se pueden configurar los valores que se usan para manejar el evento onSensorChanged del giroscopio en las escenas de combate
 */
public class EscenaCalibracionGyro extends Escena implements SensorEventListener {

    /**
     * Si se pulsa el botón empieza a "calibrar", coge datos durante un tiempo para luego hacer la media.
     * Indica si está calibrando o no
     */
    boolean isCalibrating=false;
    /**
     * colección de las medidas del giroscopio cuando entra en el modo calibración
     */
    ArrayList<float[]> calibraciones;

    /**
     * Botón que inicia la acción de calibrar
     */
    Boton btnCalibrar;


    /**
     * Botones que cambian la sensibilidad (el valor mínimo para que el evento se registre)
     */
    Boton[] btnesSensibilidadX; //eje X
    Boton[] btnesSensibilidadY; //eje Y

    /**
     * Strings de los botones creados de forma dinámica
     */
    int[] textoBtnesSensibilidad= new int[]{R.string.LOW,R.string.MEDIUM,R.string.HIGH};

    /**
     * Valores de las sensibilidades de dichos botones
     */
    float[] valoresSensibilidad = new float[]{ 0.75f,0.5f,0.25f };

    /**
     * Botones que indican al usuario qué hacen los botones creados de forma dinámica (los de arriba, de sensibilidad)
     */
    Boton btnIndicaSenseX;
    Boton btnIndicaSenseY;

    /**
     * Botón que vuelve al menú de settings guardando los valores en constantes y en sharedPrefferences
     */
    Boton btnConfirmar;
    /**
     * Botón que devuelve al menú de settings, pero sin guardar
     */
    Boton btnSalirSinGuardar;

    /**
     * Botón que establece los valores actuales del giroscopios a los que hay por defecto
     */
    Boton btnRestablecer;

    /**
     * Personaje que cambia de animación en pantalla según los valores de posición del giroscopio y su sensibilidad
     */
    PersonajeCalibracion player;

    /**
     * Fondo que se dibuja en esta escena
     */
    Bitmap fondo;

    /**
     * Imágenes que se muestran mientras dura la calibración
     */
    Bitmap[] loading;

    /**
     * indica qué imagen del loading se dibuja
     */
    int dibujaLoad=0;

    /**
     * segundos que dura la calibracion del giroscopio (recogida y posterior tratado de los datos)
     */
    int segundosCalibrado=2;

    /**
     * auxiliar para contar los segundos que pasan
     */
    int contCalibre=0;

    /**
     * Clase para obtener el sensor de rotación
     */
    private SensorManager sensorManager;

    /**
     * Valores actuales de sensibilidad y posición que emplea el giroscopio
     */
    float umbrX;
    float umbrY;
    float valorIniX;
    float valorIniY;


    /**
     * Inicia valores y establece el sensor de rotación
     * @param numEscena
     */
    public EscenaCalibracionGyro(int numEscena) {
        super(numEscena);
        umbrX= umbralSensibilidadX;
        umbrY = umbralSensibilidadY;
        btnesSensibilidadY=new Boton[3];
        btnesSensibilidadX=new Boton[3];
        InicializaBotones();
        iniciaLoad();
        calibraciones = new ArrayList<>();
        isCalibrating=false;


        //estos valores son los elegidos por el usuario

        valorIniX= valorInicialInclinacionX;
        valorIniY=valorInicialInclinacionY;
        btnCalibrar= new Boton(anchoPantalla/6,altoPantalla/7-altoPantalla/10,anchoPantalla/3,altoPantalla/10, context.getString(R.string.GyroConfig).toUpperCase(Locale.ROOT),scn.CALIBRACION.getEscena(),true);
        btnRestablecer= new Boton(anchoPantalla/6+anchoPantalla/3,altoPantalla/7-altoPantalla/10,anchoPantalla/3,altoPantalla/10, context.getString(R.string.ReestablecerGyro).toUpperCase(Locale.ROOT),scn.CALIBRACION.getEscena(),true);

        btnIndicaSenseX= new Boton(0,5*altoPantalla/14-altoPantalla/10,anchoPantalla*2/12,altoPantalla*2/15, context.getString(R.string.GyroSensibilityOnX),scn.CALIBRACION.getEscena(),false);
        btnIndicaSenseY= new Boton(0,4*altoPantalla/7-altoPantalla/10,anchoPantalla*2/12,altoPantalla*2/15, context.getString(R.string.GyroSensibilityOnY),scn.CALIBRACION.getEscena(),false);

        btnSalirSinGuardar  =new Boton(anchoPantalla/4,6*altoPantalla/7-altoPantalla/10,anchoPantalla*3/12,altoPantalla/10, context.getString(R.string.CancelOptions),scn.SETTINGS.getEscena(),true);
        btnConfirmar =new Boton(anchoPantalla/4+anchoPantalla*3/12,6*altoPantalla/7-altoPantalla/10,anchoPantalla*3/12,altoPantalla/10, context.getString(R.string.SaveOptions),scn.SETTINGS.getEscena(),true);

        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.silithus),anchoPantalla*11/10,altoPantalla*11/10,true);
        player= new PersonajeCalibracion(anchoPantalla*8/10,altoPantalla/2,100,true);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor vectorRotacion = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR);
        if(vectorRotacion!=null){
            sensorManager.registerListener(this,vectorRotacion,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }
    }


    /**
     * Inicia las distintas imágenes que componen al array de bitmaps que se emplea cuando se calibra
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
     * @return
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
     * Inicia los botones de sensibilidad. El bitmap indica qué sensibilidad está seleccionada actualmente, se actualiza cuando se pulsa alguno de ellos
     */
    public void InicializaBotones(){
        int indexMarcado=-1;
        for (int i =0; i<btnesSensibilidadX.length;i++){
            if(valoresSensibilidad[i]==umbrX){
                indexMarcado=i;
            }
            btnesSensibilidadX[i]= new Boton(anchoPantalla/4+(i*anchoPantalla*2/12),5*altoPantalla/14-altoPantalla/10,anchoPantalla*2/12,altoPantalla/10, context.getString(textoBtnesSensibilidad[i]),scn.CALIBRACION.getEscena(),i==indexMarcado);
        }
        indexMarcado=-1;
        for (int i =0; i<btnesSensibilidadY.length;i++){
            if(valoresSensibilidad[i]==umbrY){
                indexMarcado=i;
            }
            btnesSensibilidadY[i]= new Boton(anchoPantalla/4+(i*anchoPantalla*2/12),4*altoPantalla/7-altoPantalla/10,anchoPantalla*2/12,altoPantalla/10, context.getString(textoBtnesSensibilidad[i]),scn.CALIBRACION.getEscena(),i==indexMarcado);
        }
    }

    /**
     * Entra o sale del modo calibración según el parámetro
     * True: empieza a calibrar, mientras se está en este modo el giroscopio va rellenando valores de un arraylist
     * False: deja de calibrar, consigue los valores de ese arraylist y hace una media para establecer la posición actual
     * @param calibrar
     */
    public void calibrar(boolean calibrar){
        if(calibrar){
            contCalibre=0;

            calibraciones.clear();
            isCalibrating=true;
        }else{
            float sumaEnX=0;
            float sumaEnY=0;
            for (int i=0;i<calibraciones.size();i++){
                sumaEnY+=calibraciones.get(i)[0];
                sumaEnX+=calibraciones.get(i)[1];
            }
            valorIniX=sumaEnX/calibraciones.size();
            valorIniY=sumaEnY/calibraciones.size();
            isCalibrating=false;
        }
    }

    @Override
    int onTouchEvent(MotionEvent e) {
        int x= (int)e.getX();
        int y=(int)e.getY();
        int aux=super.onTouchEvent(e);
        if(!isCalibrating) {

            for(Boton b:btnesSensibilidadX){
                if(b.hitbox.contains(x,y)){
                    cambiarSensibilidad(b);
                }
            }
            for(Boton b: btnesSensibilidadY){
                if(b.hitbox.contains(x,y)){
                    cambiarSensibilidad(b);
                }
            }

            if (aux != this.numEscena && aux != -1) {
                return aux;
            }

            if (btnCalibrar.hitbox.contains(x, y)) {
                calibrar(true);
            }
            if(btnRestablecer.hitbox.contains(x,y)){
                umbrX= umbralDefaultEnX;
                umbrY=umbralDefaultEnY;
                valorIniX=valorDefaultInclinacionX;
                valorIniY=valorDefaultInclinacionY;
            }
            if(btnConfirmar.hitbox.contains(x,y)){

                valorInicialInclinacionX = this.valorIniX;
                valorInicialInclinacionY= this.valorIniY;
                umbralSensibilidadX = this.umbrX;
                umbralSensibilidadY = this.umbrY;
                Constantes.guardarValores();
                return btnConfirmar.numEscena;
            }
            if(btnSalirSinGuardar.hitbox.contains(x,y)){
                return btnSalirSinGuardar.numEscena;
            }
        }
        return this.numEscena;
    }

    /**
     * Según que botón se pulse se cambia la sensibilidad del giroscopio y luego actualiza los botones para mostrar cuál es la sensibilidad actual
     * @param sender el botón que se pulsa
     */
    public void cambiarSensibilidad(Boton sender){

        if(sender==btnesSensibilidadY[0]){
            umbrY=valoresSensibilidad[0];
        }
        if(sender==btnesSensibilidadY[1]){
            umbrY=valoresSensibilidad[1];
        }
        if(sender==btnesSensibilidadY[2]){
            umbrY=valoresSensibilidad[2];
        }
        if(sender==btnesSensibilidadX[0]){
            umbrX=valoresSensibilidad[0];
        }
        if(sender==btnesSensibilidadX[1]){
            umbrX=valoresSensibilidad[1];
        }
        if(sender==btnesSensibilidadX[2]){
            umbrX=valoresSensibilidad[2];
        }
        InicializaBotones();
        Log.i("aber", "en X: "+umbrX+"     en Y"+umbrY);
    }

    /**
     * Si se está calibrando el giroscopio (cogiendo valores para hacer el promedio) cuenta cuánto tiempo ha pasado y si supera los segundos establecidos se sale del modo calibrar
     * Si no está calibrando cambia el frame del personaje
     */
    @Override
    public void actualizaFisica() {
        if(isCalibrating){
            contCalibre++;

            if(contCalibre%FPS==0){
                segundosCalibrado--;
            }
            if(segundosCalibrado<=0){
                calibrar(false);
            }
        }else{
            player.setDeltaCurrentAnimationFrame(1);
        }
    }



    /**
     * Si no está calibrando dibuja en pantalla los componentes y al moñeco (que muestra qué acción se ejecutaría con la posición del giroscopio y la sensibilidad actuales)
     * si está en modo calibración dibuja un circulo de carga
     * @param c canvas
     */
    @Override
    public void dibuja(Canvas c) {

        if(!isCalibrating){
            c.drawBitmap(fondo,0,0,null);
            btnCalibrar.dibujar(c);
            btnRestablecer.dibujar(c);
            btnIndicaSenseY.dibujar(c);
            btnIndicaSenseX.dibujar(c);
            btnSalirSinGuardar.dibujar(c);
            btnConfirmar.dibujar(c);
            player.dibuja(c);
            for(Boton b:btnesSensibilidadX){
                b.dibujar(c);
            }
            for(Boton b:btnesSensibilidadY){
               b.dibujar(c);
            }
        }else {
            c.drawBitmap(loading[dibujaLoad%loading.length],0,0,null);
            dibujaLoad++;
        }
    }

    /**
     * Cambia la animación del personaje según los valores del vector rotación
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==TYPE_ROTATION_VECTOR){
            float R[] = new float[9];
            SensorManager.getRotationMatrixFromVector(R,event.values);
            float[] YPR = new float[3];
            SensorManager.getOrientation(R,YPR);
            final float rotacionEnX = YPR[1]*-1.0f;//valor en eje X (moverse)
            final float rotacionEnY = YPR[2]*-1.0f;//valor en eje Y (protect/agacharse)

            if(!isCalibrating){

                if(valorIniY-rotacionEnY>umbrY){
                    if(player.getCurrentAction()!=ac.PROTECT.getAction()){
                        player.setCurrentAnimation(ac.PROTECT.getAction());
                    }
                }else if(rotacionEnY-valorIniY>umbrY){
                    if(!player.isDoingAMove){
                        player.setCurrentAnimation(ac.CROUCH.getAction());
                    }
                }else if(valorIniX-rotacionEnX>umbrX){

                    if(player.getCurrentAction()!=ac.MOVE_BACKWARDS.getAction()) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                    }
                }else if(rotacionEnX-valorIniX>umbrX){

                    if(player.getCurrentAction()!=ac.MOVE_FORWARD.getAction()) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAnimation(ac.MOVE_FORWARD.getAction());
                    }
                }else{
                    if(player.getCurrentAction()!=ac.IDDLE.getAction()) {
                        player.setCurrentAnimation(ac.IDDLE.getAction());
                    }
                }
            }else{
                float[]values = new float[2];
                values[0]=rotacionEnY;
                values[1]=rotacionEnX;
                calibraciones.add(values);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
