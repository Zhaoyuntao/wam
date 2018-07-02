package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public abstract class GameObject{

    private  int id;


    public abstract void roll();

    protected long ct(){
        return System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
