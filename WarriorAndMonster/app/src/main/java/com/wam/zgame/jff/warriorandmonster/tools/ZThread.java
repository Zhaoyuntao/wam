package com.wam.zgame.jff.warriorandmonster.tools;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;

/**
 * Created by zhaoyuntao on 2018/7/4.
 */

public abstract class ZThread extends Thread {
    //每秒循环帧数
    private float frame = 1;
    //实际帧数
    private float frame_real;
    //是否停止线程的标志
    private boolean flag = true;

    private boolean pause;

    private Sleeper sleeper = new Sleeper();

    public ZThread(float frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (flag) {
            if (pause) {
                synchronized (sleeper) {
                    try {
                        sleeper.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            long time_start = System.currentTimeMillis();
            todo();
            long time_end = System.currentTimeMillis();
            long during = time_end - time_start;
            double during2 = (1000d / frame);
            long rest = (long) (during2 - during);
            if (rest > 0) {//限帧操作
                try {
                    //如果在规定的执行时间内完成了操作,则多余的时间必须消耗完
                    Thread.sleep(rest);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            time_end = System.currentTimeMillis();
            during = time_end - time_start;
            frame_real = (float) (((double) (int) ((double) 1000 / during * 10)) / 10);
        }
    }

    /**
     * 获取实际帧数
     *
     * @return
     */
    public float getFrame_real() {
        return frame_real;
    }

    // TODO: 2018/7/4
    protected abstract void todo();

    public void close() {
        flag = false;
        interrupt();
        pause=false;
    }

    public void pauseThread() {
        pause=true;
    }

    public void resumeThread(){
        pause=false;
        sleeper.notifyAll();
    }
}
