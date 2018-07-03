package com.wam.zgame.jff.warriorandmonster.model.base;


import android.graphics.Canvas;

import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;

/**
 * Created by zhaoyuntao on 2018/5/16.
 */

public class Camera extends GameObject {
    //取景框大小
    private float w_visual, h_visual;
    //取景框缩放
    private float percent = 1;
    //room的大小
    private float w_room;
    private float h_room;

    //当点距离取景框边界小于这个值的时候，要移动取景框
    private float distance_min_x;
    private float distance_min_y;
    //位置
    private float x_visual, y_visual;
    //上次的位置
    private float x_visual_last, y_visual_last;
    //用于计算位置的临时变量
    private float x_visual_tmp, y_visual_tmp;

    private Creature creature;

    private boolean lock;

    public Camera(float w_visual, float h_visual, float w_room, float h_room) {
        this.w_visual = w_visual;
        this.h_visual = h_visual;
        this.w_room = w_room;
        this.h_room = h_room;
        this.distance_min_x = w_visual / 3;
        this.distance_min_y = h_visual / 3;
    }

    /**
     * 获取绘制区域
     *
     * @return
     */
    public float[] getVisual() {
        //计算缩放以后的取景框
        float w_visual_scale = w_visual * percent;
        float h_visual_scale = h_visual * percent;
        //计算四周边界
        float left = x_visual + (w_visual - w_visual_scale) / 2;
        float top = y_visual + (h_visual - h_visual_scale) / 2;
        float right = left + w_visual_scale;
        float bottom = top + h_visual_scale;
        return new float[]{left, top, right, bottom};
    }

    /**
     * 以x,y点为中心取景
     *
     * @param x
     * @param y
     */
    public synchronized void lookAt(float x, float y) {
        lock = true;
        this.x_visual_tmp = x - w_visual / 2f;
        this.y_visual_tmp = y - h_visual / 2f;
    }

    /**
     * 以creature为中心取景
     *
     * @param creature
     */
    public synchronized void lookAt(Creature creature) {
        this.creature = creature;
    }


    /**
     * 缩放取景框
     *
     * @param percent
     */
    public synchronized void scale(float percent) {
        this.percent = percent;
    }

    /**
     * 释放取景框,取景框将会选取上次跟随的目标进行取景
     */
    public synchronized void release() {
        lock = false;
    }

    @Override
    public void roll() {

        //如果没有外部锁定取景框
        if (!lock) {
            Creature creature_now = this.creature;
            float x_creature = 0;
            float y_creature = 0;

            if (creature_now != null) {

                x_creature = creature_now.x;
                y_creature = creature_now.y;

                if (x_creature > x_visual + (w_visual - distance_min_x)) {
                    x_visual_tmp = x_creature - (w_visual - distance_min_x);
                } else if (x_creature < (x_visual + distance_min_x)) {
                    x_visual_tmp = x_creature - distance_min_x;
                }

                if (y_creature > y_visual + (h_visual - distance_min_y)) {
                    y_visual_tmp = y_creature - (h_visual - distance_min_y);
                } else if (y_creature < (y_visual + distance_min_y)) {
                    y_visual_tmp = y_creature - distance_min_y;
                }
                //确保x,y在地图内部
                if (x_creature >= 0 && x_creature <= w_room) {
                    x_visual_tmp = x_creature - w_visual / 2;
                }
                if (y_creature >= 0 && y_creature <= h_room) {
                    y_visual_tmp = y_creature - h_visual / 2;
                }
            }
        }

        if (x_visual_tmp < 0) {
            x_visual_tmp = 0;
        } else if (x_visual_tmp > (w_room - w_visual)) {
            x_visual_tmp = (w_room - w_visual);
        }
        if (y_visual_tmp < 0) {
            y_visual_tmp = 0;
        } else if (y_visual_tmp > (h_room - h_visual)) {
            y_visual_tmp = (h_room - h_visual);
        }

        //计算边界
        float margin_left = x_visual_tmp;
        float margin_right = w_room - x_visual_tmp - w_visual;
        float margin_top = y_visual_tmp;
        float margin_bottom = h_room - y_visual_tmp - h_visual;

        //赋值
        this.x_visual = x_visual_tmp;
        this.y_visual = y_visual_tmp;
        this.x_visual_last = this.x_visual;
        this.y_visual_last = this.y_visual;
    }

    @Override
    public void draw(Canvas canvas) {
    }


}
