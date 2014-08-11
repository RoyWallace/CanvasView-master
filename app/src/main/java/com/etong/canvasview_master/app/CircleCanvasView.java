package com.etong.canvasview_master.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
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

    private int maxRadius;


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
    public float increase;

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

    private Circle mCircle;


    public final static int arcAnimTime = 5000;

    public final static int refreshTime = 50;

    public long arcAnimStartTime;


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
        this.kx = kx;
        this.ky = ky;
        this.UseCustomPonit = true;
    }

    public float getMeteorCx() {
        return ((ArcTranslateAnimation) meteor.getAnimation()).getDx() + meteor.getLeft() - getLeft() + meteor.getWidth() / 2;
    }

    public float getMeteorCy() {
        return ((ArcTranslateAnimation) meteor.getAnimation()).getDy() + meteor.getTop() - getTop() + meteor.getHeight() / 2;
    }

    /**
     * 获得撞击点x轴坐标
     *
     * @return
     */
    public int getKnockPointX() {
        return UseCustomPonit ? kx : getCx();
    }

    /**
     * 获得撞击点y轴坐标
     *
     * @return
     */
    public int getKnockPointY() {
        return UseCustomPonit ? ky : getCy();
    }

    /**
     * @param number
     */
    public void setStarNumber(int number) {
        this.starNumber = number;
    }


    public float getMeteorStartX() {
        return (meteor.getRight() + meteor.getLeft()) / 2 - getLeft();
    }

    public float getMeteorStartY() {
        return (meteor.getTop() + meteor.getBottom()) / 2 - getTop();
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
        minRadius = meteor.getWidth() / 2;
        maxRadius = UseCustomPonit ? getDiagonal() : getDiagonal() / 2;
        arcAnim = new ArcTranslateAnimation(0, getMeteorTranslateX(), 0, getMeteorTranslateY());
        arcAnim.setDuration(arcAnimTime);
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

        increase = (int) (8 * density);

        paint = new Paint();
        paint.setColor(white);
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
        if (!isInEditMode()) {
            initMeteorAnim();
            mCircle = new Circle(getMeteorStartX(), getMeteorStartY(), meteor.getWidth() / 2, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了UI编辑界面拖动的控件可以看，xml编辑模式下，此部分代码不执行。
        if (!isInEditMode()) {
            if (starNumber < 2) {//画出单个圈圈的动画
//                canvas.drawCircle(getKnockPointX(), getKnockPointY(), radius, paint);
                mCircle.onDraw(canvas);
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

    public void boom(){
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

                float input = arcAnimTime/refreshTime;
                //开始动画
                for (int i = refreshTime; i < arcAnimTime; i = i + refreshTime) {
                    try {
                        increase = new AccelerateDecelerateInterpolator().getInterpolation(input);
                        Thread.sleep(refreshTime);
                        //执行circle放大动画
                        mCircle.Zoom(increase);
                        postInvalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
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


                float increase = (float)Math.abs(maxRadius-minRadius) / arcAnimTime * refreshTime;
                Log.i("etong","maxRadius: "+maxRadius);
                //开始动画
                for (int i = 100; i < arcAnimTime; i = i + refreshTime) {
                    try {
                        Thread.sleep(refreshTime);
                        //执行circle放大动画
//                        mCircle.Zoom(increase);
                        float interpolatedTime = (float)i / arcAnimTime;
                        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
                        interpolatedTime = interpolator.getInterpolation(interpolatedTime);

//                        mCircle.moveTo(((ArcTranslateAnimation)(meteor.getAnimation())).getInterpolatorTime(), getMeteorStartX(), getKnockPointX(), getMeteorStartY(), getKnockPointY());
                        mCircle.moveTo(interpolatedTime, getMeteorStartX(), getKnockPointX(), getMeteorStartY(), getKnockPointY());
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
        return maxRadius;
    }

    /**
     * 计算画布对角线长度
     *
     * @return
     */
    public int getDiagonal() {
        return (int) Math.sqrt(Math.pow((double) getHeight(), 2) + Math.pow((double) getWidth(), 2));
    }


    @Override
    public void onAnimationStart(Animation animation) {
        drawCircle();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        meteor.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
