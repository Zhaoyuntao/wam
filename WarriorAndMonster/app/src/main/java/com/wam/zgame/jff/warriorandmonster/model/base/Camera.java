package com.wam.zgame.jff.warriorandmonster.model.base;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.tools.S;

/**
 * Created by zhaoyuntao on 2018/5/16.
 */

public class Camera extends GameObject {
    //取景框大小
    private float w_visual, h_visual;
    //room的大小
    private float w_room, h_room;
    //取景框范围
    private float left, top, right, bottom;
    //当视点距离取景框边界小于这个值的时候，要移动取景框
    private float distance_min_x;
    private float distance_min_y;
    //位置
    private float x_visual, y_visual;
    //上次的位置
    private float x_visual_last, y_visual_last;
    //用于计算位置的临时变量
    private float x_visual_tmp, y_visual_tmp;
    //视角
    private Creature creature;
    //锁定取景框, 取景框无法自动移动
    private boolean lock;

    public Camera() {
    }

    public void setRoom(Room room) {
        this.w_room = room.getW_room();
        this.h_room = room.getH_room();
    }

    public void setSize(float w_visual, float h_visual) {
        this.w_visual = w_visual;
        this.h_visual = h_visual;
        this.distance_min_x = w_visual * 0.48f;
        this.distance_min_y = h_visual * 0.49f;
    }

    /**
     * 获取绘制区域
     *
     * @return
     */
    public float[] getVisualRange() {
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
     * 释放取景框,取景框将会选取上次跟随的目标进行取景
     */
    public synchronized void release() {
        lock = false;
    }

    @Override
    public void roll() {

        float w_visual = this.w_visual;
        float h_visual = this.h_visual;
        float x_visual = this.x_visual;
        float y_visual = this.y_visual;
        //如果没有外部锁定取景框
        if (!lock) {
            Creature creature_now = this.creature;
            float x_creature = 0;
            float y_creature = 0;

            if (creature_now != null) {

                x_creature = creature_now.x;
                y_creature = creature_now.y;

                //如果x坐标超过取景框的右边界
                if (x_creature > x_visual + (w_visual - distance_min_x)) {
                    x_visual_tmp = x_creature - (w_visual - distance_min_x);
                } else if (x_creature < x_visual + distance_min_x) {//如果x坐标超过取景框的左边界
                    x_visual_tmp = x_creature - distance_min_x;
                }

                //如果y坐标超过取景框的下边界
                if (y_creature > y_visual + (h_visual - distance_min_y)) {
                    y_visual_tmp = y_creature - (h_visual - distance_min_y);
                } else if (y_creature < (y_visual + distance_min_y)) {//如果y坐标超过取景框的上边界
                    y_visual_tmp = y_creature - distance_min_y;
                }
            }
        }

        //确保取景框不会跑到地图外面去
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
        synchronized (Camera.class) {
            left = x_visual_tmp;
            right = left + w_visual;
            top = y_visual_tmp;
            bottom = top + h_visual;

            //赋值
            this.x_visual = x_visual_tmp;
            this.y_visual = y_visual_tmp;
            this.x_visual_last = this.x_visual;
            this.y_visual_last = this.y_visual;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onChangeSize(float w, float h) {
        if (w == 0 || h == 0 || w_visual == 0 || h_visual == 0) {
            return;
        }
        float prop_view = w / h;
        float width_virsual = h * prop_view;

        setSize(width_virsual, h_visual);
    }


    public Bitmap getVisual(Bitmap bitmap_src) {
        if (right == 0 || bottom == 0) {
            return null;
        }
        float x_visual = 0;
        float y_visual = 0;
        float w_visual = 0;
        float h_visual = 0;
        synchronized (Camera.class) {
            x_visual = this.x_visual;
            y_visual = this.y_visual;
            w_visual = this.w_visual;
            h_visual = this.h_visual;
        }
        Bitmap bitmap_visual = Bitmap.createBitmap(bitmap_src, (int) x_visual, (int) y_visual, (int) (w_visual), (int) (h_visual));

//        Bitmap bitmap_visual = Bitmap.createBitmap((int) w_visual, (int) h_visual, Bitmap.Config.ARGB_8888);
//        Canvas canvas_visual = new Canvas(bitmap_visual);
//
//        Rect rect_src = new Rect();
//        rect_src.set((int) left, (int) top, (int) right, (int) bottom);
//        Rect rect_des = new Rect();
//        rect_des.set(0, 0, canvas_visual.getWidth(), canvas_visual.getHeight());
////
//        canvas_visual.drawColor(Color.parseColor("#55006600"));
//        Paint p = new Paint();
//        canvas_visual.drawBitmap(bitmap_src, rect_src, rect_des, p);
//        p.setStyle(Paint.Style.STROKE);
//        p.setStrokeWidth(2);
//        p.setColor(Color.BLUE);
//        canvas_visual.drawRect(new Rect((int) (distance_min_x), (int) (distance_min_y), (int) (w_visual -distance_min_x), (int) (h_visual-distance_min_y)), p);

        return bitmap_visual;
    }
}
