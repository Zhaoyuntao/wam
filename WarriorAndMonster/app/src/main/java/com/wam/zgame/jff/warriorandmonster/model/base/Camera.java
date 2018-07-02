package com.wam.zgame.jff.warriorandmonster.model.base;


import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;

/**
 * Created by zhaoyuntao on 2018/5/16.
 */

public class Camera {
    //取景框大小
    private float w_visual;
    private float h_visual;
    private float w_view;
    private float h_view;

    //当点距离取景框边界小于这个值的时候，要移动取景框
    private float distance_min_x;
    private float distance_min_y;
    //取景框范围
    private float left, top, right, bottom;
    //位置
    private float x,y;

    private Creature creature;

    private World world;

    public Camera(float w_visual, float h_visual) {
        this.w_visual = w_visual;
        this.h_visual = h_visual;
        distance_min_x = w_visual / 3;
        distance_min_y = h_visual / 3;
    }

    public float[]getPosition(){
        return new float[]{x,y};
    }

    public void roll(){
        if(){

        }
    }
}
