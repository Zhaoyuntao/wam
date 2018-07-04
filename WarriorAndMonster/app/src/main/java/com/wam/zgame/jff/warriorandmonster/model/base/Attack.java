package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;

import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;

import java.util.List;

/**
 * Created by zhaoyuntao on 2017/9/17.
 */

public class Attack extends Element {
    private float x, y;
    //前面的范围
    private float range_x_forword_attack;
    //后面的范围
    private float range_x_back_attack;
    //y轴方向上部范围
    private float range_y_forword_attack;
    //y轴方向下部范围
    private float range_y_back_attack;
    //z轴方向上部范围
    private float range_z_forword_attack;
    //z轴方向下部范围
    private float range_z_back_attack;
    //力度,影响弹飞距离
    private float power;


    public boolean isTouch(List<Creature> list) {

        for (int i = 0; i < list.size(); i++) {

            Creature creature = list.get(i);

            float left_attack = x + range_x_back_attack;
            float right_attack = x;
            float top_attack = y - range_y_back_attack;
            float bottom_attack = y;
        }
        return false;
    }


    @Override
    public void roll() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onChangeSize(float w, float h) {

    }
}
