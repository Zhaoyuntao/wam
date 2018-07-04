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
    //当前门所在的房间的id
    private int nextRoomId;

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public void roll() {

    }



    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onChangeSize(float w, float h) {

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


    public void setNextRoomId(int nextRoomId) {
        this.nextRoomId = nextRoomId;
    }

    public int getNextRoomId() {
        return nextRoomId;
    }
}
