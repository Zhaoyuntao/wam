package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public abstract class GameObject implements Comparable {

    private int id;
    //底部中心的x坐标
    protected float x;
    //底部中心的y坐标
    protected float y;

    public abstract void roll();

    public abstract void draw(Canvas canvas);

    protected long ct() {
        return System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //比较
    @Override
    public int compareTo(Object o) {
        if (o instanceof Element) {
            return (((Element) o).y - this.y) > 0 ? -1 : 1;
        }
        return -1;
    }
}
