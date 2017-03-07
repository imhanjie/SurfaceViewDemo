package com.melodyxxx.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import java.util.Random;

/**
 * Author:      Melodyxxx
 * Email:       95hanjie@gmail.com
 * Created at:  17/03/06.
 * Description:
 */

public abstract class BaseType implements DynamicWeatherView.WeatherType {

    private Context mContext;
    private int mWidth;
    private int mHeight;

    public Context getContext() {
        return mContext;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * 生成元素
     */
    public abstract void generate();

    public BaseType(Context context, DynamicWeatherView dynamicWeatherView) {
        mContext = context;
        mWidth = dynamicWeatherView.getViewWidth();
        mHeight = dynamicWeatherView.getViewHeight();
    }

    @Override
    public void onSizeChanged(Context context, int w, int h) {
        mWidth = w;
        mHeight = h;
        // SurfaceView 的大小改变时需要根据宽高重新生成元素(例如雨滴)
        generate();
    }

    /**
     * 清空画布
     *
     * @param canvas
     */
    protected void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    /**
     * 获取给定两数之间的一个随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 介于最大值和最小值之间的一个随机数
     */
    protected int getRandom(int min, int max) {
        if (max < min) {
            return 1;
        }
        return min + new Random().nextInt(max - min);
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
