package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2018/5/24.
 * <p>
 * 技能的强制中断剩余次数
 */

class Broken extends Element {

    //强制中断总次数
    private int count_all;
    //现在剩余多少次
    private int count_now;

    public Broken(int count_all, long time_cool) {
        this.count_all = count_all;
    }

    @Override
    public void roll() {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //绘制剩余百分比
    }

    @Override
    public void onChangeSize(float w, float h) {

    }

}
