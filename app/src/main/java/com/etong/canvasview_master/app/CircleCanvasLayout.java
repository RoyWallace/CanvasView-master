package com.etong.canvasview_master.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 14-8-11.
 */
public class CircleCanvasLayout extends RelativeLayout {

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

    public final static int LEFT_TOP = 1;

    public final static int CENTER_TOP = 2;

    public final static int RIGHT_TOP = 3;

    public final static int LEFT_CENTER = 4;

    public final static int CENTER = 5;

    public final static int RIGHT_CENTER = 6;

    public final static int LEFT_BOTTOM = 7;

    public final static int CENTER_BOTTOM = 8;

    public final static int RIGHT_BOTTOM = 9;

    private int circlePosition;

    public final static int DF_DURATION = 800;

    public int paintColor;

    /**
     * 颜色orange
     */
    public int orange;

    /**
     * 圆圈初始半径
     */
    private int minRadius;

    private float maxRadius;

    /**
     * 圆圈半径
     */
    private float radius;

    /**
     * 圈圈圆心x轴坐标
     */
    private float cx;

    /**
     * 圈圈圆心y轴坐标
     */
    private float cy;

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

    private int duration;

    public final static int refreshTime = 20;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getCx() {
        return cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public CircleCanvasLayout(Context context) {
        super(context);
        init();
    }

    public CircleCanvasLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.CircleCanvasLayout);
        maxRadius = typeArray.getDimension(R.styleable.CircleCanvasLayout_maxRadius, 0);
        cx = typeArray.getDimension(R.styleable.CircleCanvasLayout_cx, 0);
        cy = typeArray.getDimension(R.styleable.CircleCanvasLayout_cy, 0);
        circlePosition = typeArray.getInteger(R.styleable.CircleCanvasLayout_circlePosition, 1);
        paintColor = typeArray.getColor(R.styleable.CircleCanvasLayout_circleColor, white);
        init();
    }

    public CircleCanvasLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {

        duration = DF_DURATION;

        setWillNotDraw(false);

        density = getResources().getDisplayMetrics().density;

        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);//抗锯齿

    }

    public void setCirclePosition(int position) {
        this.circlePosition = position;
        setCenterPosition();
    }

    public void setPosition(int x, int y) {
        this.cx = x;
        this.cy = y;
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

    private void setCenterPosition(){
        //设置圈圈圆心位置
        switch (circlePosition) {
            case LEFT_TOP:
                cx = 0;
                cy = 0;
                break;
            case CENTER_TOP:
                cx = getWidth() / 2;
                cy = 0;
                break;
            case RIGHT_TOP:
                cx = getWidth();
                cy = 0;
                break;
            case LEFT_CENTER:
                cx = 0;
                cy = getHeight() / 2;
                break;
            case CENTER:
                cx = getWidth() / 2;
                cy = getHeight() / 2;
                break;
            case RIGHT_CENTER:
                cx = getWidth();
                cy = getHeight() / 2;
                break;
            case LEFT_BOTTOM:
                cx = 0;
                cy = getHeight();
                break;
            case CENTER_BOTTOM:
                cx = getWidth() / 2;
                cy = getHeight();
                break;
            case RIGHT_BOTTOM:
                cx = getWidth();
                cy = getHeight();
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //设置圈圈最大半径
        maxRadius = getDiagonal();
        setCenterPosition();
        //确保view必须数据初始化完成，然后才开始动画
        animAble = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了UI编辑界面拖动的控件可以看，xml编辑模式下，此部分代码不执行。
        if (!isInEditMode()) {
            canvas.drawCircle(cx, cy, radius, paint);
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
     * 放大
     */
    public void ZoomIn() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(minRadius, (int) maxRadius);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                radius = minRadius + value;
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 缩小
     */
    public void ZoomOut() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(minRadius, (int) maxRadius);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                radius = maxRadius - value;
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void starFall() {

    }

    /**
     * 自定义圈圈半径
     *
     * @param radius
     */
    public void setMaxRadius(int radius) {
        this.maxRadius = radius;
    }

    /**
     * 获取圈圈最大半径
     *
     * @return
     */
    public float getMaxRadius() {
        return maxRadius;
    }

    /**
     * 计算画布对角线长度
     *
     * @return
     */
    public float getDiagonal() {
        return (float) Math.sqrt(Math.pow((double) getHeight(), 2) + Math.pow((double) getWidth(), 2));
    }

}
