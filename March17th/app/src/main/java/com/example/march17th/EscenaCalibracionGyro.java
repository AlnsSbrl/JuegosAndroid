package com.example.march17th;

import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

import static com.example.march17th.Constantes.FPS;
import static com.example.march17th.Constantes.ac;
import static com.example.march17th.Constantes.altoPantalla;
import static com.example.march17th.Constantes.anchoPantalla;
import static com.example.march17th.Constantes.context;
import static com.example.march17th.Constantes.scn;
import static com.example.march17th.Constantes.umbralSensibilidadX;
import static com.example.march17th.Constantes.umbralSensibilidadY;
import static com.example.march17th.Constantes.valorInicialInclinacionX;
import static com.example.march17th.Constantes.valorInicialInclinacionY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class EscenaCalibracionGyro extends Escena implements SensorEventListener {

    ArrayList<float[]> calibraciones;
    Boton btnCalibrar;
    int[] textoBtnesSensibilidad= new int[]{R.string.LOW,R.string.MEDIUM,R.string.HIGH};
    float[] valoresSensibilidad = new float[]{ 0.75f,0.5f,0.25f };
    Boton[] btnesSensibilidadX;
    Boton[] btnesSensibilidadY;
    Boton btnRestablecer;
    Personaje player;

    Bitmap fondo;
    Bitmap blankScreen;
    int segundosCalibrado=10;
    int contCalibre=0;
    private SensorManager sensorManager;
    boolean isCalibrating=false;
    float umbrX;
    float umbrY;
    float valorIniX;
    float valorIniY;


    public EscenaCalibracionGyro(int numEscena) {
        super(numEscena);
        umbrX= umbralSensibilidadX;
        umbrY = umbralSensibilidadY;
        int indexMarcado=-1;
        btnesSensibilidadY=new Boton[3];
        btnesSensibilidadX=new Boton[3];
        for (int i =0; i<btnesSensibilidadX.length;i++){
            if(valoresSensibilidad[i]==umbrX){
                indexMarcado=i;
            }
            btnesSensibilidadX[i]= new Boton(anchoPantalla/4+(i*anchoPantalla*2/10),3*altoPantalla/6-altoPantalla/10,anchoPantalla*2/10,altoPantalla*2/15, context.getString(textoBtnesSensibilidad[i]),scn.CALIBRACION.getEscena(),i==indexMarcado);
        }
        indexMarcado=-1;
        for (int i =0; i<btnesSensibilidadY.length;i++){
            if(valoresSensibilidad[i]==umbrY){
                indexMarcado=i;
            }
            btnesSensibilidadY[i]= new Boton(anchoPantalla/4+(i*anchoPantalla/10),5*altoPantalla/6-altoPantalla/10,anchoPantalla*2/30,altoPantalla/10, context.getString(textoBtnesSensibilidad[i]),scn.CALIBRACION.getEscena(),i==indexMarcado);
        }
        //estos valores son los elegidos por el usuario

        valorIniX= valorInicialInclinacionX;
        valorIniY=valorInicialInclinacionY;
        btnCalibrar= new Boton(anchoPantalla/4,altoPantalla/6-altoPantalla/10,anchoPantalla/2,altoPantalla/10, context.getString(R.string.GyroConfig).toUpperCase(Locale.ROOT),scn.CALIBRACION.getEscena(),true);


        isCalibrating=false;
        fondo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.silithus),anchoPantalla*11/10,altoPantalla*11/10,true);
        blankScreen=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.blankscreendisplay),anchoPantalla*8/10,altoPantalla*8/10,true);
        player= new PersonajeCalibracion(anchoPantalla*8/10,altoPantalla/2,100,true);
        calibraciones = new ArrayList<>();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor vectorRotacion = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR);
        if(vectorRotacion!=null){
            sensorManager.registerListener(this,vectorRotacion,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void calibrar(boolean calibrar){
        if(calibrar){
            contCalibre=0;
            segundosCalibrado=2;
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
        }
        return this.numEscena;
    }

    public void cambiarSensibilidad(Boton sender){

        if(sender==btnesSensibilidadY[0]){
            umbrX=valoresSensibilidad[0];
        }
        if(sender==btnesSensibilidadY[1]){
            umbrX=valoresSensibilidad[1];
        }
        if(sender==btnesSensibilidadY[2]){
            umbrX=valoresSensibilidad[2];
        }
        if(sender==btnesSensibilidadX[0]){
            umbrY=valoresSensibilidad[0];
        }
        if(sender==btnesSensibilidadX[1]){
            umbrY=valoresSensibilidad[1];
        }
        if(sender==btnesSensibilidadX[2]){
            umbrY=valoresSensibilidad[2];
        }
        Log.i("aber", "en X: "+umbrX+"     en Y"+umbrY);
    }
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

    @Override
    public void dibuja(Canvas c) {
        c.drawBitmap(fondo,0,0,null);
        if(!isCalibrating){
            btnCalibrar.dibujar(c);
            player.dibuja(c);
            for(Boton b:btnesSensibilidadX){
                b.dibujar(c);
            }
            for(Boton b:btnesSensibilidadY){
               // b.dibujar(c);
            }
        }else {
            c.drawBitmap(blankScreen, anchoPantalla / 10, altoPantalla / 10, null);
        }
    }
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

                if(valorIniY-rotacionEnY>umbralSensibilidadY){
                    if(player.getCurrentAction()!=ac.PROTECT.getAction()){
                        player.setCurrentAnimation(ac.PROTECT.getAction());
                    }
                }else if(rotacionEnY-valorIniY>umbralSensibilidadY){
                    if(!player.isDoingAMove){
                        player.setCurrentAnimation(ac.CROUCH.getAction());
                    }
                }else if(valorIniX-rotacionEnX>umbralSensibilidadX){

                    if(player.getCurrentAction()!=ac.MOVE_BACKWARDS.getAction()) {
                        //tengo que hacer esta comprobacion dentro, ya que si no se va al else (y empieza a hacer el iddle)
                        player.setCurrentAnimation(ac.MOVE_BACKWARDS.getAction());
                    }
                }else if(rotacionEnX-valorIniX>umbralSensibilidadX){

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
