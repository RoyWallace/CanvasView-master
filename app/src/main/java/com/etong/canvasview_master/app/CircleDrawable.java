package com.etong.canvasview_master.app;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2014/8/27.
 */
public class CircleDrawable extends Drawable implements Animatable {

    public int cx;
    public int cy;
    public int radius;
    public Paint paint;

    private boolean running = false;


    public CircleDrawable(){

    }

    @Override
    public void start() {
        running = true;

    }

    @Override
    public void stop() {
        running = false;

    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(cx,cy,radius,paint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
