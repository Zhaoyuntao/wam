package com.wam.zgame.jff.warriorandmonster.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by zhaoyuntao on 2017/9/29.
 */

public class ZBitmap {

    private Bitmap bitmap;

    private int w;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    private int h;

    public ZBitmap() {

    }

    public ZBitmap(Bitmap bitmap) {
        setBitmap(bitmap);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if(bitmap!=null){
            this.w = bitmap.getWidth();
            this.h = bitmap.getHeight();
        }else{
            S.e("error: bitmap is null!");
        }
    }

}
