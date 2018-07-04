package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.TextMeasure;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Window_main extends View implements Runnable {

    private boolean flag = true;
    private float textsize = 1;

    private Thread thread;
    private World world;

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
        textsize = B.sp2px(context, 10);
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
        S.s("==================================");
        if (world != null) {
            world.draw(canvas);
        }
        Paint p = new Paint();
        String text = GameParams.fps_draw;
        float[] size = TextMeasure.measure(text, textsize);
        float w_text = size[0];
        float h_text = size[1];

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawText(GameParams.fps_draw, getWidth() - w_text-10, h_text, p);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(0.2f);
        canvas.drawText(GameParams.fps_draw, getWidth() - w_text-10, h_text, p);
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
            if (during < (1000d / GameParams.frame_draw)) {//限帧操作
                try {
                    //如果在规定的执行时间内完成了操作,则多余的时间必须消耗完
                    Thread.sleep((long) (1000d / GameParams.frame_draw - during));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            time_end = System.currentTimeMillis();
            during = time_end - time_start;
            GameParams.fps_draw = "FPS " + (int) (((double) (int) ((double) 1000 / during * 10)) / 10);
        }
    }

}
