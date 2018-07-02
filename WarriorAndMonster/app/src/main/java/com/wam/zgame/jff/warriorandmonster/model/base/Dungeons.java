package com.wam.zgame.jff.warriorandmonster.model.base;

import com.wam.zgame.jff.warriorandmonster.tools.S;

/**
 * Created by zhaoyuntao on 2017/9/2.
 */

public class Dungeons {
    /**
     * 当前所在房间
     */
    private Room room;

    /**
     * 设置当前房间
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * 获取当前房间
     * @return
     */
    public Room getRoom() {
        if(room==null){
            S.e("room is null:in Dungeons.getRoom()");
        }
        return room;
    }
}
