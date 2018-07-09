package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;
import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.model.expand.Monster;
import com.wam.zgame.jff.warriorandmonster.model.expand.Player;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class World {

    public static int frame_draw = 100;
    public static int frame_cal = 300;
    //房间
    private Room room;
    //第一人称
    private Player player;
    //取景框
    private Camera camera;
    //所有元素
    private List<GameObject> list_object;
    //所有房间
    private Map<Integer, Room> list_room;
    //所有怪物
    private List<Monster> list_creature;

    public World() {
        this.list_room = new HashMap<>();
        this.list_creature = new ArrayList<>();
        this.list_object = new ArrayList<>();
    }

    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 向房间内添加怪物
     *
     * @param o
     */
    public void addMonster(Monster o) {
        addObject(o);
        list_creature.add(o);
    }

    /**
     * 向房间添加元素
     *
     * @param o
     */
    public void addObject(GameObject o) {
        list_object.add(o);
    }

    /**
     * 添加一个房间
     *
     * @param room
     */
    public void addRoom(Room room) {
        addObject(room);
        list_room.put(room.getId(), room);
        this.room = room;
        if (this.camera != null && this.room != null) {
            this.camera.setRoom(this.room);
        }
    }

    /**
     * 添加一个取景框
     *
     * @param camera
     */
    public void addCamera(Camera camera) {
        addObject(camera);
        this.camera = camera;
        if (this.camera != null && room != null) {
            this.camera.setRoom(this.room);
        }
    }



    /**
     * 添加第一人称
     *
     * @param player
     */
    public void addPlayer(Player player) {
        this.player = player;
        if(this.player!=null){
            this.player.setWorld(this);
        }
        addObject(player);
    }

    /**
     * 战场是否清零
     */
    public boolean isMonsterClear() {
        for (int i = 0; i < list_creature.size(); i++) {
            GameObject gameObject = list_creature.get(i);
            if (gameObject instanceof Creature) {
                Creature creature = (Creature) gameObject;
                if (!creature.isFriend()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 进入某个房间
     *
     * @param id_room
     */

    public void gotoRoom(int id_room) {
        if (!list_room.containsKey(id_room)) {
            //如果房间不存在,向副本添加房间
            Room room = RoomLoader.downloadRoom(id_room);
            list_room.put(room.getId(), room);
        }
        room = list_room.get(id_room);
    }

    /**
     * 清空所有房间
     */
    public void clearRoom() {
        list_room.clear();
    }

    ZThread zThread_roll;
    ZThread zThread_draw;

    public synchronized void start() {
        if (zThread_roll == null) {
            zThread_roll = new ZThread(frame_cal) {
                @Override
                protected void todo() {
                    roll();
                }
            };
            zThread_roll.start();
        }
        if (zThread_draw == null) {
            zThread_draw = new ZThread(frame_draw) {
                @Override
                protected void todo() {
                    draw();
                }
            };
            zThread_draw.start();
        }
    }

    public float getFps_draw() {
        return fps_draw;
    }

    public float getFps_cal() {
        return fps_cal;
    }

    private float fps_draw, fps_cal;

    private void roll() {
        //重新排序地图中的元素
        Collections.sort(list_creature);
        //刷新状态
        for (GameObject gameObject : list_object) {
            gameObject.roll();
        }
        if(zThread_roll!=null) {
            fps_cal = zThread_roll.getFrame_real();
        }
        if(zThread_draw!=null) {
            fps_draw = zThread_draw.getFrame_real();
        }
    }

    private void draw() {
        Bitmap bitmap_src = Bitmap.createBitmap((int) room.getW_room(), (int) room.getH_room(), Bitmap.Config.ARGB_8888);
        Canvas canvas_src = new Canvas(bitmap_src);

        canvas_src.drawColor(Color.YELLOW);

        for (GameObject gameObject : list_object) {
            gameObject.draw(canvas_src);
        }
        Bitmap bitmap_visual = null;
        if (camera != null) {
            bitmap_visual = camera.getVisual(bitmap_src);
        } else {
            bitmap_visual = bitmap_src;
        }
        if (callBack != null) {
            callBack.onDraw(bitmap_visual);
        }
    }

    public void onChangeSize(float w, float h) {
        //刷新状态
        for (GameObject gameObject : list_object) {
            gameObject.onChangeSize(w,h);
        }
    }

    public Room getRoom() {
        return room;
    }

    public void pauseDraw() {
        if (zThread_draw != null) {
            zThread_draw.pauseThread();
        }
    }

    public void resumeDraw() {
        if (zThread_draw != null) {
            zThread_draw.resumeThread();
        }
    }

    public synchronized void destroy() {
        if (zThread_roll != null) {
            zThread_roll.close();
            zThread_roll = null;
        }
        if (zThread_draw != null) {
            zThread_draw.close();
            zThread_draw = null;
        }
    }

    public interface CallBack {
        void onDraw(Bitmap bitmap);
    }
}
