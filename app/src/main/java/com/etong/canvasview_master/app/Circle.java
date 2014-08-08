package com.etong.canvasview_master.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 14-8-8.
 */
public class Circle {


    private float cx;

    private float cy;

    private float radius;

    private Paint paint;

    public Circle(float cx, float cy, float radius, Paint paint) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.paint = paint;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    public void moveTo(float interpolatedTime, float startX, float endX, float startY, float endY) {
        this.cx = AnimUtil.calcBezier(interpolatedTime, startX, startX, endX);
        this.cy = AnimUtil.calcBezier(interpolatedTime, startY, endY, endY);
        Log.i("etong","cx:　"+cx);
        Log.i("etong","cy:　"+cy);
    }

    public void Zoom(float fromR,float toR,float time) {
        float increase = Math.abs(toR-fromR)/time;
        this.radius = radius + increase;
        Log.i("etong","increase: "+increase);
        Log.i("etong","r: "+radius);
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
