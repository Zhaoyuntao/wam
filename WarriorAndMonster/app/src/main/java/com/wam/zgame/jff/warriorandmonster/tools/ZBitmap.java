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


    public int getW() {
        if (bitmap != null) {
            return bitmap.getWidth();
        } else {
            return 0;
        }
    }


    public int getH() {
        if (bitmap != null) {
            return bitmap.getHeight();
        } else {
            return 0;
        }
    }


    public ZBitmap() {

    }

    public ZBitmap(Bitmap bitmap) {
        setBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
