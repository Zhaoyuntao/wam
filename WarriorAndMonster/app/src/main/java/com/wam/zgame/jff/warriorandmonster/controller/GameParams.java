package com.wam.zgame.jff.warriorandmonster.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class GameParams {
    public static String fps_draw = "FPS";
    public static String fps_cal = "FPS";

    public static float w_visual;
    public static float h_visual;
    public static float w_px;

    public static ZBitmap bitmap;

    public synchronized static ZBitmap getBitmap() {
        if(bitmap==null){
            float w_bitmap=100;
            float h_bitmap=100;
           Bitmap bitmap_tmp = Bitmap.createBitmap((int)w_bitmap, (int)h_bitmap, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap_tmp);
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setAntiAlias(true);
            canvas.drawCircle(w_bitmap/2, h_bitmap/2, (w_bitmap>h_bitmap?w_bitmap:h_bitmap)/2, p);
            bitmap=new ZBitmap(bitmap_tmp);
        }
        return bitmap;
    }
}
