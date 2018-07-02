package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;

import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class World extends Element {

    private Map<Integer, Room> list_room;

    private Room room_current;

    public World() {
        list_room = new HashMap<>();
    }

    /**
     * @param room
     */
    private void addRoom(Room room) {

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
            room.setWorld(this);
            list_room.put(room.getId(), room);
        }
        room_current = list_room.get(id_room);
    }

    /**
     * 清空所有房间
     */
    public void clearRoom() {
        list_room.clear();
    }


    @Override
    public void roll() {
        //刷新房间状态
        Room room = this.room_current;
        room.roll();
    }

    @Override
    public void draw(Canvas canvas) {

    }

}
