package com.etong.canvasview_master.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 14-8-7.
 */
public class CircleCanvasView extends RelativeLayout implements Animation.AnimationListener {

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 颜色black
     */
    public final static int black = 0x70000000;//黑色

    /**
     * 颜色white
     */
    public final static int white = 0xddffffff;//白色

    /**
     * 颜色orange
     */
    public int orange;

    /**
     * 圆圈初始半径
     */
    private int minRadius;

    /**
     * 圆圈半径
     */
    private int radius;

    /**
     * 画布中心x轴坐标
     */
    private int cx;

    /**
     * 画布中心y轴坐标
     */
    private int cy;

    /**
     * 扩散量单位
     */
    public int increase;

    /**
     * 动画是否可以启用
     */
    private boolean animAble = false;

    /**
     * 屏幕分辨率密度比
     */
    private float density;

    /**
     * 流星
     */
    private View meteor;

    /**
     * 流星撞击点x轴坐标
     */
    private int kx;

    /**
     * 流星撞击点y轴坐标
     */
    private int ky;

    /**
     * 是否自定义流星撞击点
     */
    private boolean UseCustomPonit = false;

    /**
     * 贝塞尔曲线动画对象
     */
    private ArcTranslateAnimation arcAnim;

    /**
     * 流星数量
     */
    private int starNumber;


    public CircleCanvasView(Context context) {
        super(context);
        init();
    }

    public CircleCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CircleCanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CircleCanvasView(Context context, View view) {
        super(context);

        this.meteor = view;

        init();

        initMeteorAnim();

    }

    /**
     * 设置流星
     *
     * @param view
     */
    public void setMeteor(View view) {
        this.meteor = view;
    }

    /**
     * 设置流星撞击点，相对画布的坐标
     *
     * @param kx
     * @param ky
     */
    public void setKnockPoint(int kx, int ky) {
        if (kx < 0 || ky < 0) {
            throw new IllegalArgumentException("kx or ky can not be less than zero");
        }
        this.kx = kx;
        this.ky = ky;
        this.UseCustomPonit = true;
    }

    /**
     * 获得撞击点x轴坐标
     *
     * @return
     */
    public int getKnockPointX() {
        return UseCustomPonit ? (kx > getWidth() ? getWidth() : kx) : getCx();
    }

    /**
     * 获得撞击点y轴坐标
     *
     * @return
     */
    public int getKnockPointY() {
        return UseCustomPonit ? (ky > getHeight() ? getHeight() : ky) : getCy();
    }

    /**
     * @param number
     */
    public void setStarNumber(int number) {
        this.starNumber = number;
    }

    /**
     * 获取流星x轴偏移量
     *
     * @return
     */
    private int getMeteorTranslateX() {
        return getMeteorTranslate(getKnockPointX() + getLeft(), meteor.getLeft(), meteor.getWidth());
    }

    /**
     * 获取流星y轴偏移量
     *
     * @return
     */
    private int getMeteorTranslateY() {
        return getMeteorTranslate(getKnockPointY() + getTop(), meteor.getTop(), meteor.getWidth());
    }

    /**
     * 计算流星偏移量
     *
     * @param end   结束坐标
     * @param start 初始坐标
     * @param dia   流星直径
     * @return
     */
    private int getMeteorTranslate(int end, int start, int dia) {
        return end - (start + dia / 2);
    }


    /**
     * 初始化流星的动画
     */
    private void initMeteorAnim() {

        arcAnim = new ArcTranslateAnimation(0, getMeteorTranslateX(), 0, getMeteorTranslateY());
        arcAnim.setDuration(500);
        arcAnim.setAnimationListener(this);
    }

    public void startMeteorAnim() {
        meteor.startAnimation(arcAnim);
    }


    private void init() {

        orange = getResources().getColor(R.color.orange);

        setWillNotDraw(false);

        density = getResources().getDisplayMetrics().density;

        minRadius = 0;

        increase = (int) (10 * density);

        paint = new Paint();
        paint.setColor(orange);
        paint.setAntiAlias(true);//抗锯齿

    }

    /**
     * 设置画笔颜色
     *
     * @param color
     */
    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    /**
     * 获取画笔颜色
     *
     * @return
     */
    public int getPaintColor() {
        return paint.getColor();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //确保view的高宽和定位已经初始化完成，然后才开始动画
        animAble = true;
        initMeteorAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了UI编辑界面拖动的控件可以看，xml编辑模式下，此部分代码不执行。
        if (!isInEditMode()) {
            if (starNumber < 2) {//画出单个圈圈的动画
                canvas.drawCircle(getKnockPointX(), getKnockPointY(), radius, paint);
            } else {//画出多个圈圈的动画
                for (int i = 0; i < starNumber; i++) {
                    canvas.drawCircle(randomCx(), randomCy(), radius, paint);
                }
            }
        }
    }

    /**
     * 随机产生圆点x轴坐标
     *
     * @return
     */
    private int randomCx() {
        return randomCenter(getWidth());
    }

    /**
     * 随机产生圆点x轴坐标
     *
     * @return
     */
    private int randomCy() {
        return randomCenter(getHeight());
    }

    /**
     * 随机产生圆点坐标
     *
     * @return
     */
    private int randomCenter(int scope) {
        return (int) (Math.random() * scope);
    }

    /**
     * 获取画布中心点x坐标
     *
     * @return
     */
    private int getCx() {
        return (getRight() - getLeft()) / 2;
    }

    /**
     * 获取画布中心点y坐标
     *
     * @return
     */
    private int getCy() {
        return (getBottom() - getTop()) / 2;
    }

    /**
     * 用线程改变圈圈大小，通知主线程画出圈圈
     */
    public void drawCircle() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //如果View高宽位置未初始化完成，线程等待
                while (!animAble) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //开始动画
                for (radius = minRadius; radius < getMaxRadius(); radius = radius + increase) {
                    try {
                        Thread.sleep(20);
                        postInvalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    public void starFall() {

    }

    /**
     * 获取圈圈最大半径
     *
     * @return
     */
    public int getMaxRadius() {
        return UseCustomPonit ? getDiagonal() : getDiagonal() / 2;
    }

    /**
     * 计算画布对角线长度
     * @return
     */
    public int getDiagonal() {
        return (int) Math.sqrt(Math.pow((double) getHeight(), 2) + Math.pow((double) getWidth(), 2));
    }


    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        meteor.setVisibility(View.GONE);
        drawCircle();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
