package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;
import com.wam.zgame.jff.warriorandmonster.model.base2.Creature;
import com.wam.zgame.jff.warriorandmonster.model.expand.Monster;
import com.wam.zgame.jff.warriorandmonster.model.expand.Player;
import com.wam.zgame.jff.warriorandmonster.tools.S;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class World extends GameObject {

    private Room room;

    private Map<Integer, Room> list_room;

    private Player player;

    private Camera camera;

    //所有元素集合
    private List<Monster> list_creature;

    public World() {
        this.list_room = new HashMap<>();
        this.list_creature = new ArrayList<>();
    }

    /**
     * 向房间内添加怪物
     *
     * @param o
     */
    public void addMonster(Monster o) {
        list_creature.add(o);
    }

    /**
     * @param room
     */
    public void addRoom(Room room) {
        list_room.put(room.getId(), room);
        this.room = room;
        if (camera != null) {
            room.setCamera(camera);
        }
    }

    public void addCamera(Camera camera) {
        this.camera = camera;
        if (room != null) {
            room.setCamera(camera);
        }
    }

    public void addPlayer(Player player) {
        this.player = player;
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


    @Override
    public void roll() {
        //重新排序地图中的元素
        Collections.sort(list_creature);
        //刷新状态
        for (GameObject gameObject : list_creature) {
            gameObject.roll();
        }
        if (room != null) {
            room.roll();
        }
        if (player != null) {
            player.roll();
        }
        if (camera != null) {
            camera.roll();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        S.s("----------------------------------------------------------------------------------");
        if (room != null) {
            room.draw(canvas);
        }
        for (GameObject gameObject : list_creature) {
            gameObject.draw(canvas);
        }
        if (player != null) {
            player.draw(canvas);
        }
    }


    public Camera getCamera() {
        return camera;
    }

}
