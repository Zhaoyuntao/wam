package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Skill extends Element {
    //名称
    private String name;
    //技能id
    private int id = -1;
//    //柔化管理器
//    private Broken rouhua;

    public Skill(long time_all) {
    }

    public void roll() {
    }

    @Override
    public void draw(Canvas canvas) {
//        Rect rect_bitmap = new Rect();
//        int width_bitmap = bitmap.getWidth();
//        int height_bitmap = bitmap.getHeight();
//        rect_bitmap.set(0, 0, width_bitmap, height_bitmap);
//        RectF rect_draw = new RectF();
//        int width_border = 15;
//        rect_draw.set(left + width_border, top + width_border, right - width_border, bottom - width_border);
//        p.setAlpha(150);
//        canvas.drawBitmap(bitmap, rect_bitmap, rect_draw, p);
//        p.setAlpha(255);
//        long time = skill.time_rest;
//        if (time > 0) {
//
//        }
    }

    @Override
    public void onChangeSize(float w, float h) {

    }

}
