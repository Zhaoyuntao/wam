package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhaoyuntao on 2017/9/2.
 */

public class Room extends Element {
    //room id, 用来定位房间
    private int id;
    //房间属性
    private int state = TOWN;
    public static final int TOWN = 0;
    public static final int DUNGEONS = 1;

    //所有元素
    private List<Creature> list_creature;

    //入口
    private List<Door> list_doors;
    //地板图
    private ZBitmap zbitmap_floor;
    //风景墙
    private ZBitmap zbitmap_wall;
    //背景
    private ZBitmap zbitmap_back;

    //房间的总虚拟大小
    private int w_room;
    private int h_room;

    //地板区域的高度
    private int h_floor;
    //风景墙区域的高度
    private int h_wall;

    //所属的世界
    private World world;

    //打击队列
    private List<Attack> list_attack;


    public Room(int state) {
        this.state = state;
        this.list_attack = new ArrayList<>();
        this.list_creature = new ArrayList<>();
        this.list_doors = new ArrayList<>();
    }


    /**
     * 向房间内添加元素
     *
     * @param hero
     */
    public void addCreature(Creature hero) {
        list_creature.add(hero);
    }

    /**
     * 添加打击
     *
     * @param attack
     */
    public void addAttack(Attack attack) {
        list_attack.add(attack);
    }

    /**
     * 战场是否清零
     */
    public boolean isMonsterClear() {
        boolean isMonsterClear = false;
        for (int i = 0; i < list_creature.size(); i++) {
            Creature creature = list_creature.get(i);
            if (!creature.isFriend()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void roll() {
        //刷新门的状态
        for (int i = 0; i < list_doors.size(); i++) {
            Door door = list_doors.get(i);
            door.roll();
        }
        //重新排序地图中的元素
        Collections.sort(list_creature);
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

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap_floor = null;
        if (zbitmap_floor != null) {
            bitmap_floor = zbitmap_floor.getBitmap();
            Rect rect1 = new Rect();
            rect1.set(0, 0, bitmap_floor.getWidth(), bitmap_floor.getHeight());
            Rect rect2 = new Rect();
            int w = canvas.getWidth();
            int h = canvas.getHeight();
            rect2.set(0, 0, w, h);
            Paint p = new Paint();
            canvas.drawBitmap(bitmap_floor, rect1, rect2, p);
        }

        for (Creature monster : list_creature) {
            monster.draw(canvas);
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

    public int getW_room() {
        return w_room;
    }

    public void setW_room(int w_room) {
        this.w_room = w_room;
    }

    public int getH_room() {
        return h_room;
    }

    public void setH_room(int h_room) {
        this.h_room = h_room;
    }

    public int getH_floor() {
        return h_floor;
    }

    public void setH_floor(int h_floor) {
        this.h_floor = h_floor;
    }

    public int getH_wall() {
        return h_wall;
    }

    public void setH_wall(int h_wall) {
        this.h_wall = h_wall;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void leave(Door door) {
        int id_nextRoom = door.getNextRoomId();
        world.gotoRoom(id_nextRoom);
    }
}