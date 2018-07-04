package com.wam.zgame.jff.warriorandmonster.controller;

import com.wam.zgame.jff.warriorandmonster.model.base.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class GameCalculator implements Runnable {


    List<GameObject>list;
    public GameCalculator() {
        init();
    }

    private  void flush() {
        for(GameObject gameObject:list){
            gameObject.roll();
        }
    }

    private boolean keepWorking = true;
    private Thread thread;

    public void init() {
        list=new ArrayList<>();
        thread = new Thread(this);
        thread.start();
    }

    public void addObject(GameObject o){
        list.add(o);
    }

    public void close() {
        keepWorking = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        while (keepWorking) {
            long time_start = System.currentTimeMillis();
            flush();
            if (!sleeper_draw.flag) {
                synchronized (sleeper_draw) {
                    try {
                        sleeper_draw.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            long time_end = System.currentTimeMillis();
            long during = time_end - time_start;
            if (during < (1000d / GameParams.frame_cal)) {//限帧操作
                try {
                    //如果在规定的执行时间内完成了操作,则多余的时间必须消耗完
                    Thread.sleep((long) (1000d / GameParams.frame_cal - during));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    class Sleeper {
        public boolean flag = true;
    }

    private final Sleeper sleeper_draw = new Sleeper();

}
