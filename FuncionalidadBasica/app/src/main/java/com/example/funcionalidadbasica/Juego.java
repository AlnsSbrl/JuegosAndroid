package com.example.funcionalidadbasica;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;

public class Juego extends View {

    int anchoPantalla, altoPantalla;
    Paint paint;
    TextPaint textPaint;
    Rect hitboxPersonaje;

    StaticLayout textLayout;
    Personaje player;



    public Juego(Context context) {
        super(context);
        paint = new Paint();
        paint.setAlpha(255);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        player = new Personaje(anchoPantalla,altoPantalla);
        hitboxPersonaje=new Rect(player.posX,player.posY,player.posX+player.anchoPersonaje,player.posY+player.altoPersonaje);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(hitboxPersonaje,paint);
        canvas.drawRect(new Rect(0,0,50,50),paint);
        canvas.drawRect(player.hurtboxPersonaje,paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int accion=event.getAction();
        switch (accion){
            case MotionEvent.ACTION_UP:
                player.Moverse(50);
                break;
            case MotionEvent.ACTION_SCROLL:
                player.Moverse(-100);
                break;
        }
        invalidate();
        return super.onTouchEvent(event);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        anchoPantalla=w;
        altoPantalla=h;
    }
}
