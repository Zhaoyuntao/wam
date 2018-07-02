package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;

import com.wam.zgame.jff.warriorandmonster.controller.RoomLoader;

/**
 * Created by zhaoyuntao on 2018/5/16.
 */

public class Door extends Element {
    // 入口的范围
    private int range_x, range_y;
    //是否可以通过
    private boolean open;
    //自身id
    private int id;
    //当前门所在的房间
    private Room room_current;
    //当前门所在的房间的id
    private int nextRoomId;

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public void roll() {
        if (room_current.isMonsterClear()) {
            setOpen(open);
        } else {
            setOpen(false);
        }
    }



    @Override
    public void draw(Canvas canvas) {
        if (open) {

        } else {

        }
    }
    /**
     * 通过此门
     */
    private void pass() {
        room_current.leave(this);
    }
    //---------------------------------------

    public int getRange_x() {
        return range_x;
    }

    public void setRange_x(int range_x) {
        this.range_x = range_x;
    }

    public int getRange_y() {
        return range_y;
    }

    public void setRange_y(int range_y) {
        this.range_y = range_y;
    }

    public boolean isOpen() {
        return open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom_current() {
        return room_current;
    }

    public void setRoom_current(Room room_current) {
        this.room_current = room_current;
    }

    public void setNextRoomId(int nextRoomId) {
        this.nextRoomId = nextRoomId;
    }

    public int getNextRoomId() {
        return nextRoomId;
    }
}
