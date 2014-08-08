package com.etong.canvasview_master.app;

/**
 * Created by Administrator on 14-8-8.
 */
public class AnimUtil {

    public static long calcBezier(float interpolatedTime, float p0, float p1, float p2) {
        return Math.round((Math.pow((1 - interpolatedTime), 2) * p0)
                + (2 * (1 - interpolatedTime) * interpolatedTime * p1)
                + (Math.pow(interpolatedTime, 2) * p2));
    }

}
