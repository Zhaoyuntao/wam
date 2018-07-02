package com.wam.zgame.jff.warriorandmonster.tools;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class SizeManager {

    /**
     * hp绘制宽度
     */
    private static  float w_hp=100;
    /**
     * hp绘制高度
     */
    private static  float h_hp=10;

    public static float getW_hp() {
        return w_hp;
    }

    public static float getH_hp() {
        return h_hp;
    }

    public static void setW_hp(float w_hp) {
        SizeManager.w_hp = w_hp;
    }

    public static void setH_hp(float h_hp) {
        SizeManager.h_hp = h_hp;
    }


    /**
     * hp边框宽度
     */
    private static float w_border_hp=2;

    public static float getW_border_hp() {
        return w_border_hp;
    }

    public static void setW_border_hp(float w_border_hp) {
        SizeManager.w_border_hp = w_border_hp;
    }
}
