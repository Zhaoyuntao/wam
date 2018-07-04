package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;


public class FingerControlImpl_control extends FingerControl implements Runnable {


    private boolean flag = true;
    private Thread thread;

    class Sleeper {
        public boolean flag;
    }

    private final Sleeper sleeper_control = new Sleeper();

    public FingerControlImpl_control(Context context) {
        super(context);
        init(context);
    }

    public FingerControlImpl_control(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public FingerControlImpl_control(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        thread = new Thread(this);
        thread.start();
    }

    public void close() {
        flag = false;
        thread.interrupt();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void run() {
        while (flag) {
            long time_start = System.currentTimeMillis();

            if (!sleeper_control.flag) {
                if (callBack != null) {
                    callBack.send(0, 0);
                }
                synchronized (sleeper_control) {
                    try {
                        sleeper_control.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            if (callBack != null) {
                callBack.send(getX_move(), getY_move());
            }
            long time_end = System.currentTimeMillis();
            long during = time_end - time_start;
            if (during < (1000d / GameParams.frame_sendControlCommand)) {//限帧操作
                try {
                    //如果在规定的执行时间内完成了操作,则多余的时间必须消耗完
                    Thread.sleep((long) (1000d / GameParams.frame_sendControlCommand - during));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            time_end = System.currentTimeMillis();
            during = time_end - time_start;
            GameParams.fps_send = "Calculate FPS:" + (int) (((double) (int) ((double) 1000 / during * 10)) / 10);
        }
    }

    @Override
    public void whenPress() {
        synchronized (sleeper_control) {
            sleeper_control.flag = true;
            sleeper_control.notifyAll();
        }
        if (callBack != null) {
            callBack.whenTouch();
        }
    }

    @Override
    public void whenUp() {
        sleeper_control.flag = false;
    }
}
