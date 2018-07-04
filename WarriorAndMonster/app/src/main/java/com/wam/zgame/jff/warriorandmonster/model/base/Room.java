package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
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

    //地板区域的高度
    private float h_floor;
    //风景墙区域的高度
    private float h_wall;

    //camera
    private Camera camera;
    //打击队列
    private List<Attack> list_attack;

    public Room(int state) {
        this.state = state;
        this.list_attack = new ArrayList<>();

        this.list_doors = new ArrayList<>();
    }


    public void setCamera(Camera camera){
        this.camera=camera;
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
        Bitmap bitmap_floor = null;
        if (zbitmap_floor != null) {
            bitmap_floor = zbitmap_floor.getBitmap();
            float w_bitmap = 0;
            float h_bitmap = 0;
            float left = 0;
            float top = 0;
            float right = 0;
            float bottom = 0;
            if (bitmap_floor != null) {
                w_bitmap = bitmap_floor.getWidth();
                h_bitmap = bitmap_floor.getHeight();
            }
            if (camera != null) {
                float[] visual = camera.getVisualRange();
                left = visual[0] ;
                top = visual[1];
                right = visual[2] ;
                bottom = visual[3] ;
                S.s("camera 不为空: w:"+right+" h:"+bottom);
            } else {
                left = 0;
                top = 0;
                right = w_bitmap;
                bottom = h_bitmap;
                S.s("camera 为空: w:"+w_bitmap+" h:"+h_bitmap);
            }
            S.s(" GameParams.w_visual:"+ GameParams.w_visual+"  GameParams.h_visual:"+ GameParams.h_visual);
            S.s("percent_visual:"+ GameParams.w_visual/ GameParams.h_visual);
            S.s("percent_right/bottom:"+ right/bottom);
            S.s("range: left:"+left+" top:"+top+" right:"+right+" bottom:"+bottom);
            Rect rect1 = new Rect();
            rect1.set((int) left, (int) top, (int) right, (int) bottom);
            Rect rect2 = new Rect();
            int w = canvas.getWidth();
            int h = canvas.getHeight();
            rect2.set(0, 0, w, h);
            Paint p = new Paint();
            canvas.drawBitmap(bitmap_floor, rect1, rect2, p);
        }

    }


    //------------------------ set and get
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
}
