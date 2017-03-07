package com.melodyxxx.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Author:      Melodyxxx
 * Email:       95hanjie@gmail.com
 * Created at:  17/03/06.
 * Description:
 */

public class DynamicWeatherView extends SurfaceView implements SurfaceHolder.Callback {

    public interface WeatherType {
        void onDraw(Canvas canvas);

        void onSizeChanged(Context context, int w, int h);
    }

    private Context mContext;
    private DrawThread mDrawThread;
    private SurfaceHolder mHolder;
    private WeatherType mType;
    private int mViewWidth;
    private int mViewHeight;

    public void setType(WeatherType type) {
        mType = type;
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public DynamicWeatherView(Context context) {
        this(context, null);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        if (mType != null) {
            mType.onSizeChanged(mContext, w, h);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawThread = new DrawThread();
        mDrawThread.setRunning(true);
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDrawThread.setRunning(false);
    }

    /**
     * 绘制线程
     */
    private class DrawThread extends Thread {

        // 用来停止线程的标记
        private boolean isRunning = false;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            // 无限循环绘制
            while (isRunning) {
                if (mType != null && mViewWidth != 0 && mViewHeight != 0) {
                    canvas = mHolder.lockCanvas();
                    if (canvas != null) {
                        mType.onDraw(canvas);
                        if (isRunning) {
                            mHolder.unlockCanvasAndPost(canvas);
                        } else {
                            // 停止线程
                            break;
                        }
                        // sleep
                        SystemClock.sleep(1);
                    }
                }
            }
        }
    }


}
