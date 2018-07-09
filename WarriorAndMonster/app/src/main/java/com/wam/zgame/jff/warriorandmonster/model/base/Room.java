package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoyuntao on 2017/9/2.
 */

public class Room extends GameObject {
    //room id, 用来定位房间
    private int id;
    //房间属性
    private int state = TOWN;
    public static final int TOWN = 0;
    public static final int DUNGEONS = 1;

    //入口
    private List<Door> list_doors;
    //地板图
    private ZBitmap zbitmap_floor;
    //风景墙
    private ZBitmap zbitmap_wall;
    //背景
    private ZBitmap zbitmap_back;

    //房间的总虚拟大小
    private float w_room;
    private float h_room;

    //活动区域的高度
    private float w_floor;
    private float h_floor;
    private float x_floor;
    private float y_floor;

    //风景墙的高度
    private float w_wall;
    private float h_wall;
    private float x_wall;
    private float y_wall;

    //背景区域的高度
    private float w_back;
    private float h_back;
    private float x_back;
    private float y_back;

    //打击队列
    private List<Attack> list_attack;

    public Room(int state) {
        this.state = state;
        this.list_attack = new ArrayList<>();
        this.list_doors = new ArrayList<>();
    }

    /**
     * 添加打击
     *
     * @param attack
     */
    public void addAttack(Attack attack) {
        list_attack.add(attack);
    }


    @Override
    public void roll() {
        //刷新门的状态
        for (int i = 0; i < list_doors.size(); i++) {
            Door door = list_doors.get(i);
            door.roll();
        }

//        //
//        for (Creature monster : list_creature) {
//            for (Attack attack : list_attack) {
//                //攻击中心到地面的坐标
//                float bottom = attack.y + attack.range_y / 2 + attack.range_z / 2;
//                //怪物中心到攻击点中心的最大有效距离.
//                float distance_x_max = attack.range_x / 2 + monster2.range_x / 2 * monster2.percent_pic;
//                if (Math.abs(monster2.x - attack.x) <= distance_x_max && Math.abs(monster2.y - bottom) <= attack.range_z / 2) {
//                    monster2.reduceHp_percent(0.2f);
//                }
//            }
//            monster.roll();
//        }

    }

    public void draw(Canvas canvas) {
        Bitmap bitmap_back = null;
        if (zbitmap_back != null) {
            bitmap_back = zbitmap_back.getBitmap();
            if (bitmap_back != null) {
                float w_bitmap = bitmap_back.getWidth();
                float h_bitmap = bitmap_back.getHeight();

                int left = (int) x_back;
                int top = (int) y_back;
                int right = (int) (x_back + w_back);
                int bottom = (int) (y_back + h_back);

                Rect rect1 = new Rect();
                rect1.set(0, 0, (int) w_bitmap, (int) h_bitmap);
                Rect rect2 = new Rect();
                rect2.set(left, top, right, bottom);
                Paint p = new Paint();
                canvas.drawBitmap(bitmap_back, rect1, rect2, p);
            }
        }
        Bitmap bitmap_floor = null;
        if (zbitmap_floor != null) {
            bitmap_floor = zbitmap_floor.getBitmap();
            if (bitmap_floor != null) {
                float w_bitmap = bitmap_floor.getWidth();
                float h_bitmap = bitmap_floor.getHeight();

                int left = (int) x_floor;
                int top = (int) y_floor;
                int right = (int) (x_floor + w_floor);
                int bottom = (int) (y_floor + h_floor);

                Rect rect1 = new Rect();
                rect1.set(0, 0, (int) w_bitmap, (int) h_bitmap);
                Rect rect2 = new Rect();
                rect2.set(left, top, right, bottom);
                Paint p = new Paint();
                canvas.drawBitmap(bitmap_floor, rect1, rect2, p);
            }
        }
        Bitmap bitmap_wall = null;
        if (zbitmap_wall != null) {
            bitmap_wall = zbitmap_wall.getBitmap();
            if (bitmap_wall != null) {
                float w_bitmap = bitmap_wall.getWidth();
                float h_bitmap = bitmap_wall.getHeight();

                int left = (int) x_wall;
                int top = (int) y_wall;
                int right = (int) (x_wall + w_wall);
                int bottom = (int) (y_wall + h_wall);

                Rect rect1 = new Rect();
                rect1.set(0, 0, (int) w_bitmap, (int) h_bitmap);
                Rect rect2 = new Rect();
                rect2.set(left, top, right, bottom);
                Paint p = new Paint();
                canvas.drawBitmap(bitmap_wall, rect1, rect2, p);
            }
        }
    }

    @Override
    public void onChangeSize(float w, float h) {

    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ZBitmap getZbitmap_floor() {
        return zbitmap_floor;
    }

    public void setZbitmap_floor(ZBitmap zbitmap_floor) {
        this.zbitmap_floor = zbitmap_floor;
    }

    public ZBitmap getZbitmap_wall() {
        return zbitmap_wall;
    }

    public void setZbitmap_wall(ZBitmap zbitmap_wall) {
        this.zbitmap_wall = zbitmap_wall;
    }

    public ZBitmap getZbitmap_back() {
        return zbitmap_back;
    }

    public void setZbitmap_back(ZBitmap zbitmap_back) {
        this.zbitmap_back = zbitmap_back;
    }

    public float getW_room() {
        return w_room;
    }

    public void setW_room(float w_room) {
        this.w_room = w_room;
    }

    public float getH_room() {
        return h_room;
    }

    public void setH_room(float h_room) {
        this.h_room = h_room;
    }

    public float getH_floor() {
        return h_floor;
    }

    public void setH_floor(float h_floor) {
        this.h_floor = h_floor;
    }

    public float getH_wall() {
        return h_wall;
    }

    public void setH_wall(float h_wall) {
        this.h_wall = h_wall;
    }

    public float getX_floor() {
        return x_floor;
    }

    public void setX_floor(float x_floor) {
        this.x_floor = x_floor;
    }

    public float getY_floor() {
        return y_floor;
    }

    public void setY_floor(float y_floor) {
        this.y_floor = y_floor;
    }

    public float getX_wall() {
        return x_wall;
    }

    public void setX_wall(float x_wall) {
        this.x_wall = x_wall;
    }

    public float getY_wall() {
        return y_wall;
    }

    public void setY_wall(float y_wall) {
        this.y_wall = y_wall;
    }

    public float getH_back() {
        return h_back;
    }

    public void setH_back(float h_back) {
        this.h_back = h_back;
    }

    public float getX_back() {
        return x_back;
    }

    public void setX_back(float x_back) {
        this.x_back = x_back;
    }

    public float getY_back() {
        return y_back;
    }

    public void setY_back(float y_back) {
        this.y_back = y_back;
    }

    public float getW_floor() {
        return w_floor;
    }

    public void setW_floor(float w_floor) {
        this.w_floor = w_floor;
    }

    public float getW_wall() {
        return w_wall;
    }

    public void setW_wall(float w_wall) {
        this.w_wall = w_wall;
    }

    public float getW_back() {
        return w_back;
    }

    public void setW_back(float w_back) {
        this.w_back = w_back;
    }
}
