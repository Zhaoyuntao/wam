package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public abstract class Element extends GameObject  {

    //底部中心的x坐标
    protected float x;
    //底部中心的y坐标
    protected float y;
    //距离地面的距离
    protected float z;

    //朝向, 超左或者右面
    protected int direction = 1;
    public final int direction_left = 0;
    public final int direction_right = 1;

    //全部贴图------------------------
    private ZBitmap[] pictures;

    //全部姿态
    private List<Pose> list;
    //当前正在执行的动作
    private Pose pose_now;

    //x轴范围:前侧0
    protected float range_x_forward_real;
    //x轴范围:背后
    protected float range_x_back_real;
    //y轴范围:上部
    protected float range_y_top_real;
    //y轴范围:下部
    protected float range_y_bottom_real;
    //z轴范围:上部
    protected float range_z_top_real;
    //z轴范围:下部
    protected float range_z_bottom_real;

    //---------------------------------------绘制相关
    //绘制的四个边界
    protected float left, top, right, bottom;
    //x轴绘制范围:前侧
    protected float range_x_forward_pic;
    //x轴绘制范围:背后
    protected float range_x_back_pic;
    //y轴绘制范围:上部
    protected float range_y_top_pic;
    //y轴绘制范围:下部
    protected float range_y_bottom_pic;
    //z轴绘制范围:上部
    protected float range_z_top_pic;
    //z轴绘制范围:下部
    protected float range_z_bottom_pic;


    //元素绘制缩放百分比
    protected float percent_pic = 1.0f;
    //元素实际范围缩放百分比
    protected float percent_real = 1.0f;

    public Element() {
        list = new ArrayList<>();
    }




    /**
     * 重新设置一组动作
     *
     * @param stateOfPose
     */
    public void resetPose(Pose... stateOfPose) {
        addPose(stateOfPose);
    }

    /**
     * 在原有的基础上添加一组新动作
     *
     * @param stateOfPose
     */
    public void addPose(Pose... stateOfPose) {
        list.clear();
        for (Pose pose : stateOfPose) {
            list.add(pose);
        }
    }


    protected ZBitmap getCurrentBitmap() {
        //计算动作--------------------------------------
        if(pictures==null||pictures.length==0){
            return null;
        }
        int index = -1;
        //获取当前应该执行的一连串动作的数组
        if (this.pose_now == null) {
            index = 0;
        } else {
            index = pose_now.getPicIndex();
        }
        this.pose_now = list.get(index);
        //获取该图片下标
        int index_pic_now = pose_now.getPicIndex();
        //获取本次应该绘制的bitmap位图
        if(index_pic_now>pictures.length-1){
            return null;
        }
        ZBitmap bitmap_current = pictures[index_pic_now];
        //bitmap位图要绘制的区域
        int w_bitmap = bitmap_current.getWidth();
        int h_bitmap = bitmap_current.getHeight();
        bitmap_current.setSrc(0, 0, w_bitmap, h_bitmap);

        //图片长宽比
        float percent = (float) w_bitmap / h_bitmap;

        //计算绘制范围---------------------------
        //计算left,right方向的绘制边界,因为元素朝向左右会改变绘制边界,所以要分开来计算
        //如果朝向左边
        if (direction == direction_left) {
            //左边界等于中心点坐标减去前边范围
            left = x - range_x_forward_pic * percent_pic;
            //右边界等于中心点坐标加上背后范围
            right = x + range_x_back_pic * percent_pic;
            bitmap_current.setBitmap(B.overTurnBitmap(bitmap_current.getBitmap()));
        } else if (direction == direction_right) {
            //如果朝向右边,则左边界等于中心点坐标加上背后范围
            left = x + range_x_back_pic * percent_pic;
            //右边界等于中心点坐标减去前面范围
            right = x - range_x_forward_pic * percent_pic;
        }
        //计算顶部和底部的绘制边界, 由于元素朝向左右不影响上下的边界,所以左右的计算方式一致
        top = y - (range_y_top_pic + range_y_bottom_pic) * percent_pic;
        bottom = y;
        if (left == right || top == bottom) {
            return null;
        }

        //防止图片比例失衡,按比例居中缩放
        float w_area_draw = right - left;
        float h_area_draw = bottom - top;
        float percent_canvas = w_area_draw / h_area_draw;
        if (percent < percent_canvas) {
            w_area_draw = h_area_draw * percent;
            left = (left + right) / 2 - w_area_draw / 2;
        } else {
            h_area_draw = w_area_draw / percent;
            top = (top + bottom) / 2 - h_area_draw / 2;
        }

        bitmap_current.setDes((int) left, (int) top, (int) right, (int) bottom);
        return bitmap_current;
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        ZBitmap bitmap_current = getCurrentBitmap();
        if (bitmap_current != null) {
            float left_real = 0;
            float right_real = 0;
            float top_real = 0;
            float bottom_real = 0;
            int w_pic = bitmap_current.getW();
            int h_pic = bitmap_current.getH();
            //如果朝向左边
            if (direction == direction_left) {
                //左边界等于中心点坐标减去前边范围
                left = x - range_x_forward_pic * percent_real;
                //右边界等于中心点坐标加上背后范围
                right = x + range_x_back_pic * percent_real;
                //左右翻转图片
                bitmap_current.setBitmap(B.overTurnBitmap(bitmap_current.getBitmap()));

                //计算实际范围
                left_real = x - range_x_forward_real;
                right_real = x + range_x_back_real;

            } else if (direction == direction_right) {
                //如果朝向右边,则左边界等于中心点坐标加上背后范围
                left = x + range_x_back_pic * percent_real;
                //右边界等于中心点坐标减去前面范围
                right = x - range_x_forward_pic * percent_real;

                //计算实际范围
                left_real = x - range_x_back_real;
                right_real = x + range_x_forward_real;
            }
            Paint p = new Paint();
            Rect rect_pic = new Rect();
            rect_pic.set(0, 0, w_pic, h_pic);

            Rect rect_draw = new Rect();
            rect_draw.set((int) left, (int) top, (int) right, (int) bottom);
            canvas.drawBitmap(bitmap_current.getBitmap(), rect_pic, rect_draw, p);

            //元素朝向左右不影响上下的边界
            top_real = y - (range_y_top_real + range_y_bottom_real) * percent_real;
            bottom_real = y;
            //实体范围边框绘制的区域
            Rect rect_range = new Rect();
            rect_range.set((int) left_real, (int) top_real, (int) right_real, (int) bottom_real);
            //绘制实体范围边框--------------------------------

            p.setAntiAlias(true);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(1);
            canvas.drawRect(rect_range, p);
        } else {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setAntiAlias(true);
            canvas.drawCircle(x, y, 30, p);
            S.s("==============");
        }


    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRange_draw(int i1, int i2, int i3, int i4) {
        this.range_x_forward_pic = i1;
        this.range_x_back_pic = i2;
        this.range_y_top_pic = i3;
        this.range_y_bottom_pic = i4;
    }

    public float getRange_x_forward_real() {
        return range_x_forward_real;
    }

    public void setRange_x_forward_real(float range_x_forward_real) {
        this.range_x_forward_real = range_x_forward_real;
    }

    public float getRange_x_back_real() {
        return range_x_back_real;
    }

    public void setRange_x_back_real(float range_x_back_real) {
        this.range_x_back_real = range_x_back_real;
    }

    public float getRange_y_top_real() {
        return range_y_top_real;
    }

    public void setRange_y_top_real(float range_y_top_real) {
        this.range_y_top_real = range_y_top_real;
    }

    public float getRange_y_bottom_real() {
        return range_y_bottom_real;
    }

    public void setRange_y_bottom_real(float range_y_bottom_real) {
        this.range_y_bottom_real = range_y_bottom_real;
    }

    public float getRange_z_top_real() {
        return range_z_top_real;
    }

    public void setRange_z_top_real(float range_z_top_real) {
        this.range_z_top_real = range_z_top_real;
    }

    public float getRange_z_bottom_real() {
        return range_z_bottom_real;
    }

    public void setRange_z_bottom_real(float range_z_bottom_real) {
        this.range_z_bottom_real = range_z_bottom_real;
    }

    public float getRange_x_forward_pic() {
        return range_x_forward_pic;
    }

    public void setRange_x_forward_pic(float range_x_forward_pic) {
        this.range_x_forward_pic = range_x_forward_pic;
    }

    public float getRange_x_back_pic() {
        return range_x_back_pic;
    }

    public void setRange_x_back_pic(float range_x_back_pic) {
        this.range_x_back_pic = range_x_back_pic;
    }

    public float getRange_y_top_pic() {
        return range_y_top_pic;
    }

    public void setRange_y_top_pic(float range_y_top_pic) {
        this.range_y_top_pic = range_y_top_pic;
    }

    public float getRange_y_bottom_pic() {
        return range_y_bottom_pic;
    }

    public void setRange_y_bottom_pic(float range_y_bottom_pic) {
        this.range_y_bottom_pic = range_y_bottom_pic;
    }

    public ZBitmap[] getPictures() {
        return pictures;
    }

    public void setPictures(ZBitmap[] pictures) {
        this.pictures = pictures;
    }
}
