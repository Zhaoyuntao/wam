package com.wam.zgame.jff.warriorandmonster.tools;

import android.graphics.Paint;

/**
 * Created by zhaoyuntao on 2018/7/4.
 */

public class TextMeasure {

    public static float[] measure(String text,float textsize){
        Paint p=new Paint();
        p.setTextSize(textsize);
        float textWidth = p.measureText(text);
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        // top绝对值,最外层top,负数
        float top = Math.abs(fontMetrics.top);
        // ascent绝对值,文字上部,负数
        float ascent = Math.abs(fontMetrics.ascent);
        // descent，正值,文字下部
        float descent = fontMetrics.descent;
        // bottom，正值,最外层bottom
        float bottom = fontMetrics.bottom;
        float w_text = textWidth;
        float h_text = Math.abs(descent - ascent);
        return new float[]{w_text,h_text};
    }
}
