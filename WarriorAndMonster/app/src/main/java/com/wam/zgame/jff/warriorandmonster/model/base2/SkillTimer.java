package com.wam.zgame.jff.warriorandmonster.model.base2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wam.zgame.jff.warriorandmonster.model.base.Element;
import com.wam.zgame.jff.warriorandmonster.tools.TextSizeManager;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created by zhaoyuntao on 2018/6/6.
 */

public class SkillTimer extends Element{
    //是否需要绘制结束动画
    protected boolean isDrawFinish;
    //剩余时间
    private long time_rest;

    public SkillTimer(long time_cool) {
    }

    protected void whenInProgress(long time_rest) {
        this.time_rest = time_rest;
    }

    protected void whenFinished() {
        this.time_rest=0;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        if (time_rest > 0) {
            String str_time = "" + time_rest / 1000;
            if (time_rest < 1000) {
                Format format = new DecimalFormat("#.#");
                str_time = format.format((float) time_rest / 1000);
            }
            float textsize = TextSizeManager.getTextSize_coolTime();
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.argb(200, 255, 255, 255));
            p.setTextSize(textsize);
            float x_text = (left + right) / 2 - textsize / 2 * ((float) str_time.length() / 2);
            canvas.drawText(str_time, x_text, (top + bottom) / 2 + textsize / 3, p);
            p.setStyle(Paint.Style.STROKE);
            p.setColor(Color.argb(200, 0, 0, 0));
            p.setStrokeWidth(1);
            canvas.drawText(str_time, x_text, (top + bottom) / 2 + textsize / 3, p);
        } else {
            if (isDrawFinish) {
                isDrawFinish = false;
                //绘制冷却结束的特效
            }
        }
    }

    @Override
    public void onChangeSize(float w, float h) {

    }

    @Override
    public void roll() {

    }
}
