package com.melodyxxx.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Author:      Melodyxxx
 * Email:       95hanjie@gmail.com
 * Created at:  17/03/06.
 * Description:
 */

public class RainTypeImpl extends BaseType {

    // 背景
    private Drawable mBackground;
    // 雨滴集合
    private ArrayList<RainHolder> mRains;
    // 画笔
    private Paint mPaint;

    public RainTypeImpl(Context context, DynamicWeatherView dynamicWeatherView) {
        super(context, dynamicWeatherView);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        // 这里雨滴的宽度统一为3
        mPaint.setStrokeWidth(3);
        mRains = new ArrayList<>();
    }

    @Override
    public void generate() {
        mBackground = getContext().getResources().getDrawable(R.drawable.rain_sky_night);
        mBackground.setBounds(0, 0, getWidth(), getHeight());
        for (int i = 0; i < 60; i++) {
            RainHolder rain = new RainHolder(
                    getRandom(1, getWidth()),
                    getRandom(1, getHeight()),
                    getRandom(dp2px(9), dp2px(15)),
                    getRandom(dp2px(5), dp2px(9)),
                    getRandom(20, 100)
            );
            mRains.add(rain);
        }
    }

    private RainHolder r;

    @Override
    public void onDraw(Canvas canvas) {
        clearCanvas(canvas);
        // 画背景
        mBackground.draw(canvas);
        // 画出集合中的雨点
        for (int i = 0; i < mRains.size(); i++) {
            r = mRains.get(i);
            mPaint.setAlpha(r.a);
            canvas.drawLine(r.x, r.y, r.x, r.y + r.l, mPaint);
        }
        // 将集合中的点按自己的速度偏移
        for (int i = 0; i < mRains.size(); i++) {
            r = mRains.get(i);
            r.y += r.s;
            if (r.y > getHeight()) {
                r.y = -r.l;
            }
        }
    }

    private class RainHolder {
        /**
         * 雨点 x 轴坐标
         */
        int x;
        /**
         * 雨点 y 轴坐标
         */
        int y;
        /**
         * 雨点长度
         */
        int l;
        /**
         * 雨点移动速度
         */
        int s;
        /**
         * 雨点透明度
         */
        int a;

        public RainHolder(int x, int y, int l, int s, int a) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.s = s;
            this.a = a;
        }

    }

}
