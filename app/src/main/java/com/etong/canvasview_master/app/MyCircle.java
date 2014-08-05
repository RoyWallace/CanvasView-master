package com.etong.canvasview_master.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static java.lang.Math.*;

/**
 * Created by Administrator on 14-8-5.
 */
public class MyCircle extends View {

    private Paint paint1;

    private Paint paint2;

    private Paint paint;

    public final static int black = 0x70000000;//黑色

    public final static int white = 0xddffffff;//白色

    private int minRadius;

    private int radius;

    public int increase;

    public MyCircle(Context context) {
        super(context);
        init();
    }

    public MyCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void init() {

        float density = getResources().getDisplayMetrics().density;

        minRadius = 0;

        increase = (int) (5 * density);

        paint = new Paint();
        paint.setColor(black);
        paint.setAntiAlias(true);//抗锯齿

    }

    public void setPaint(int color) {
        paint.setColor(color);
    }

    public int getPaint() {
        return paint.getColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("etong","width: "+getWidth());
        Log.i("etong","heigth: "+getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(0,300,480,400,paint1);
        if (!isInEditMode()) {
            canvas.drawCircle(getCX(), getCY(), radius, paint);
        }

    }

    public int getMwidth() {
        return getRight() - getLeft();
    }


    public int getCX() {
        return (getRight() - getLeft()) / 2;
    }

    public int getCY() {
        return (getBottom() - getTop()) / 2;
    }

    public int getMheight() {
        return getBottom() - getTop();
    }


    public void drawCircle() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("etong","left: "+getLeft());
                Log.i("etong","right: "+getRight());
                Log.i("etong","top: "+getTop());
                Log.i("etong","bottom: "+getBottom());
                Log.i("etong","x: "+getCX());
                Log.i("etong","y: "+getCY());

                for (int i = minRadius; i < getDiagonal(); i = i + increase) {

                    try {
                        Thread.sleep(20);
                        radius = i;
                        postInvalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public int getDiagonal() {
        return getMheight() + getMwidth();
    }
}
