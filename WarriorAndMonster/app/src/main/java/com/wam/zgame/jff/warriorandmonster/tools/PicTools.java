package com.wam.zgame.jff.warriorandmonster.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public class PicTools {

    /**
     * 水平翻转图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap overTurnBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Matrix matrix = canvas.getMatrix();
        matrix.postScale(-1, 1);
        matrix.postTranslate(bitmap.getWidth(), 0);//平移
        canvas.drawBitmap(bitmap, matrix, null);
        return bitmap;
    }

}
