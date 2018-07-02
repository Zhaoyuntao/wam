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

    //x轴绘制范围:左
    private int left_src;
    //x轴绘制范围:右
    private int right_src;
    //y轴绘制范围:上
    private int top_src;
    //y轴绘制范围:下
    private int bottom_src;

    //x轴画布绘制范围:左
    private int left_des;
    //x轴画布绘制范围:右
    private int right_des;
    //y轴画布绘制范围:上
    private int top_des;
    //y轴画布绘制范围:下
    private int bottom_des;

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
        this.bitmap = bitmap;
        if(bitmap!=null){
            this.w = bitmap.getWidth();
            this.h = bitmap.getHeight();
        }else{
            S.e("error: bitmap is null!");
        }
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
    }

    public void setSrc(int left, int top, int right, int bottom) {
        this.left_src = left;
        this.top_src = top;
        this.right_src = right;
        this.bottom_src = bottom;
    }

    public void setDes(int left, int top, int right, int bottom) {
        this.left_des = left;
        this.top_des = top;
        this.right_des = right;
        this.bottom_des = bottom;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        //绘制贴图
        Rect rect_src = new Rect();
        rect_src.set(left_src, top_src, right_src, bottom_src);
        Rect rect_des = new Rect();
        rect_des.set(left_src, top_src, right_src, bottom_src);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, rect_src, rect_des, p);
        }
    }
}
