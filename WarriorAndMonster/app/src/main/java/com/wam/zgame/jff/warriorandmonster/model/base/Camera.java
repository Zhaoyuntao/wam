package com.wam.zgame.jff.warriorandmonster.model.base;


import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;

/**
 * Created by zhaoyuntao on 2018/5/16.
 */

public class Camera {
    //取景框大小
    private float w_visual, h_visual;
    private float w_visual_last, h_visual_last;
    //取景框缩放
    private float percent = 1;
    //room的大小
    private float w_room;
    private float h_room;

    //当点距离取景框边界小于这个值的时候，要移动取景框
    private float distance_min_x;
    private float distance_min_y;
    //取景框范围
    private float left, top, right, bottom;
    //位置
    private float x_virsual, y_virsual;
    //上次的位置
    private float x_virsual_last, y_virsual_last;
    //用于计算位置的临时变量
    private float x_virsual_tmp, y_virsual_tmp;

    private Creature creature;

    private World world;

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
        float left = x_virsual + (w_visual - w_visual_scale) / 2;
        float top = y_virsual + (h_visual - h_visual_scale) / 2;
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
        this.x_virsual_tmp = x - w_visual / 2f;
        this.y_virsual_tmp = y - h_visual / 2f;
    }

    /**
     * 以creature为中心取景
     *
     * @param creature
     */
    public synchronized void lookAt(Creature creature) {
        this.creature=creature;
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

    public void roll() {

        //如果没有移动过,则不需要计算
        if (x_virsual == x_virsual_last && y_virsual == y_virsual_last && w_visual == w_visual_last && h_visual == h_visual_last) {
            return;
        }
        //如果没有外部锁定取景框
        if (!lock) {
            Creature creature_now = this.creature;
            float x_creature = 0;
            float y_creature = 0;

            if (creature_now != null) {
                x_creature = creature_now.x;
                y_creature = creature_now.y;
            }

            //确保x,y在地图内部
            if (x_creature >= 0 && x_creature <= w_room) {
                x_virsual_tmp = x_creature;

            }
            if (y_creature >= 0 && y_creature <= h_room) {
                y_virsual_tmp = y_creature;
            }
        }

        //计算边界
        float x_virtual = 0;
        float y_virtual = 0;
        float margin_left = x_virsual_tmp;
        float margin_right = w_room - x_virsual_tmp;
        float margin_top = y_virsual_tmp;
        float margin_bottom = h_room - y_virsual_tmp;
        float half_w = w_room / 2f;
        float half_h = h_room / 2f;
        //如果取景框x没有抵达room边界,则移动
        if (margin_left > half_w && margin_right > half_w) {
            x_virtual = x_virsual_tmp - half_w;
        }
        //如果取景框y没有抵达room边界,则移动
        if (margin_top > half_h && margin_bottom > half_h) {
            y_virtual = y_virsual_tmp - half_h;
        }
        //位置矫正
        if (x_virtual < 0) {
            x_virtual = 0;
        }
        if (x_virtual > (w_room - w_visual)) {
            x_virtual = w_room - w_visual;
        }
        if (y_virtual < 0) {
            y_virtual = 0;
        }
        if (y_virtual > (h_room - h_visual)) {
            y_virtual = h_room - h_visual;
        }

        //赋值
        this.x_virsual = x_virtual;
        this.y_virsual = y_virtual;
        this.x_virsual_last = this.x_virsual;
        this.y_virsual_last = this.y_virsual;
    }


}
