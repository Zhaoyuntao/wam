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
        margin_right=B.dip2px(context,40);
    }

    Bitmap bitmap;

    /**
     * 刷新画面
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
        Bitmap bitmap=this.bitmap;
//        this.bitmap=null;
        if (bitmap == null) {
            return;
        }
        Paint p = new Paint();

        Rect rect_src = new Rect();
        float w_bitmap = bitmap.getWidth();
        float h_bitmap = bitmap.getHeight();
        float prop_bitmap = w_bitmap / h_bitmap;
        rect_src.set(0, 0, (int)w_bitmap, (int)h_bitmap);

        Rect rect_des = new Rect();
        float w_draw = canvas.getWidth();
        float h_draw = canvas.getHeight();
        float prop_canvas = w_draw / h_draw;
        if (prop_bitmap > prop_canvas) {
            h_draw = w_draw / prop_bitmap;
        } else {
            w_draw = h_draw * prop_bitmap;
        }
        rect_des.set(0, 0, (int) w_draw, (int) h_draw);

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
        canvas.drawText(text, getWidth() -margin_right, h_text, p);
        canvas.drawText(text2, getWidth() -margin_right, h_text*3, p);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(0.2f);
        p.setTextSize(textsize);
        canvas.drawText(text, getWidth() -margin_right, h_text, p);
        canvas.drawText(text2, getWidth() -margin_right, h_text*3, p);
    }

}
