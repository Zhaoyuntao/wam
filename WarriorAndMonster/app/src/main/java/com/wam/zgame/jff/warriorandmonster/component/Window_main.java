package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.application.GameApplication;
import com.wam.zgame.jff.warriorandmonster.controller.GameInfo;
import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.model.base.Camera;
import com.wam.zgame.jff.warriorandmonster.model.base.Element;
import com.wam.zgame.jff.warriorandmonster.model.base.Room;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.Sleeper;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Window_main extends View implements Runnable {

    private boolean flag = true;
    private Thread thread;
    private World  world;

    public void addWorld(World world) {
        this.world = world;
    }

    public Window_main(Context context) {
        super(context);
        init(context);
    }

    public Window_main(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Window_main(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if(world!=null) {
            world.draw(canvas);
        }
        Paint p = new Paint();
        p.setTextSize(20);
        p.setColor(Color.BLACK);
        canvas.drawText(GameInfo.fps_draw, getWidth() - 400, 20, p);
    }

    public synchronized void flush_back() {
        postInvalidate();
    }

    @Override
    public void run() {
        while (flag) {
            long time_start = System.currentTimeMillis();
            flush_back();
            long time_end = System.currentTimeMillis();
            long during = time_end - time_start;
            if (during < (1000d / GameInfo.frame_draw)) {//限帧操作
                try {
                    //如果在规定的执行时间内完成了操作,则多余的时间必须消耗完
                    Thread.sleep((long) (1000d / GameInfo.frame_draw - during));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            time_end = System.currentTimeMillis();
            during = time_end - time_start;
            GameInfo.fps_draw = "drawBitmap FPS:" + (int) (((double) (int) ((double) 1000 / during * 10)) / 10);
        }
    }

}
