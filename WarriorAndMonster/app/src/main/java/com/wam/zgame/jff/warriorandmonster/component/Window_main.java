package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.model.base.World;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.TextMeasure;
import com.wam.zgame.jff.warriorandmonster.tools.ZThread;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Window_main extends View {


    private float textsize;
    private float margin_right;
    private float x_visual, y_visual;
    private int gravity;
    private static final int gravity_center = 0;
    private static final int gravity_left = 1;
    private static final int gravity_right = 2;

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
        textsize = B.sp2px(context, 15);
        margin_right = B.dip2px(context, 40);
    }

    Bitmap bitmap;

    /**
     * 刷新画面
     *
     * @param bitmap
     */
    public synchronized void flush(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.bitmap;
        float w_canvas = canvas.getWidth();
        float h_canvas = canvas.getHeight();
        if (bitmap == null || w_canvas == 0 || h_canvas == 0) {
            return;
        }


        Paint p = new Paint();

        Rect rect_src = new Rect();
        float w_bitmap = bitmap.getWidth();
        float h_bitmap = bitmap.getHeight();
        float prop_bitmap = w_bitmap / h_bitmap;
        rect_src.set(0, 0, (int) w_bitmap, (int) h_bitmap);

        Rect rect_des = new Rect();


        float w_draw=w_canvas;
        float h_draw=h_canvas;
        float prop_canvas = w_canvas / h_canvas;
        if (prop_bitmap > prop_canvas) {
            h_draw = w_canvas / prop_bitmap;
        } else {
            w_draw = h_canvas * prop_bitmap;
        }

        switch (gravity) {
            case gravity_left:
                x_visual = 0;
                y_visual = 0;
                break;
            case gravity_center:
                x_visual = w_canvas / 2 - w_draw / 2;
                break;
            case gravity_right:
                x_visual = h_canvas / 2 - h_draw / 2;
                break;
        }
        float left = x_visual;
        float top = y_visual;
        float right = w_draw + x_visual;
        float bottom = h_draw + y_visual;
        rect_des.set((int) (left), (int) (top), (int) (right), (int) (bottom));

        p.setColor(Color.WHITE);
        canvas.drawRect(rect_des, p);
        canvas.drawBitmap(bitmap, rect_src, rect_des, p);


        String text = GameParams.fps_draw;
        String text2 = GameParams.fps_cal;
        float[] size = TextMeasure.measure(text, textsize);
        float w_text = size[0];
        float h_text = size[1];

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawText(text, getWidth() - margin_right, h_text, p);
        canvas.drawText(text2, getWidth() - margin_right, h_text * 3, p);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(0.2f);
        p.setTextSize(textsize);
        canvas.drawText(text, getWidth() - margin_right, h_text, p);
        canvas.drawText(text2, getWidth() - margin_right, h_text * 3, p);
    }

    public float getX_visual() {
        return x_visual;
    }

    public void setX_visual(float x_visual) {
        this.x_visual = x_visual;
    }

    public float getY_visual() {
        return y_visual;
    }

    public void setY_visual(float y_visual) {
        this.y_visual = y_visual;
    }
}
