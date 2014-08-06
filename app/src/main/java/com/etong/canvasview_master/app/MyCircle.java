package com.etong.canvasview_master.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 14-8-5.
 */
public class MyCircle extends RelativeLayout {

    private Paint paint;

    public final static int black = 0x70000000;//黑色

    public final static int white = 0xddffffff;//白色

    private int minRadius;

    private int radius;

    private int cx;

    private int cy;

    public int increase;

    private boolean animAble = false;

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
        setWillNotDraw(false);

        float density = getResources().getDisplayMetrics().density;

        minRadius = 0;

        increase = (int) (5 * density);

        paint = new Paint();
        paint.setColor(black);
        paint.setAntiAlias(true);//抗锯齿

    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setColor(int color) {
        paint.setColor(color);
    }

    /**
     * 获取画笔颜色
     * @return
     */
    public int getColor() {
        return paint.getColor();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //确保view的高宽和定位已经初始化完成，然后才开始动画
        animAble = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            canvas.drawCircle(getCx(), getCy(), radius, paint);
        }
    }

    /**
     * 获取圈圈圆点x坐标
     * @return
     */
    public int getCx() {
        return (getRight() - getLeft()) / 2;
    }

    /**
     * 获取圈圈圆点y坐标
     * @return
     */
    public int getCy() {
        return (getBottom() - getTop()) / 2;
    }

    public void setCX(int cx){
        this.cx=cx;
    }

    public void setCY(int cy){
        this.cy=cy;
    }


    public void drawCircle() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //如果View高宽位置未初始化完成，线程等待
                while (!animAble) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //开始动画
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

    /**
     * 获取对角线粗略估算
     * @return
     */
    public int getDiagonal() {
        return (getHeight() + getWidth()) / 2;
    }

}
