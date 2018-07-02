package com.wam.zgame.jff.warriorandmonster.tools;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class TextSizeManager {
    //名称字体大小
    private static float textsize_name = 20;

    public static float getTextSize_name() {
        return textsize_name;
    }

    public static void setTextsize_name(float textsize_name) {
        TextSizeManager.textsize_name = textsize_name;
    }

    private static float textSize_coolTime = 20;

    public static float getTextSize_coolTime() {
        return textSize_coolTime;
    }
}
