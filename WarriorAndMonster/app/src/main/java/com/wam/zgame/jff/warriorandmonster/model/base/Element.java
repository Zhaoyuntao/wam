package com.wam.zgame.jff.warriorandmonster.model.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.ZBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyuntao on 2018/5/18.
 */

public abstract class Element extends GameObject {

    protected long time_last;
    protected long time_now;
    protected long time_during;

    //朝向
    protected int x_direction = 0;
    protected int y_direction = 0;
    public final int direction_reduce = -1;
    public final int direction_add = 1;

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
    protected float left_real, top_real, right_real, bottom_real;
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


    @Override
    public void roll() {
        if (time_last == 0) {
            time_last = S.currentTimeMillis();
        }
        time_now = S.currentTimeMillis();
        time_during = time_now - time_last;
        float w = 15;
        if (range_x_forward_pic == 0) {
            range_x_forward_pic = w ;
        }
        if (range_x_back_pic == 0) {
            range_x_back_pic = w;
        }
        if (range_y_top_pic == 0) {
            range_y_top_pic = w;
        }
        if (range_y_bottom_pic == 0) {
            range_y_bottom_pic = w;
        }
        if (range_x_forward_real == 0) {
            range_x_forward_real = w;
        }
        if (range_x_back_real == 0) {
            range_x_back_real = w;
        }
        if (range_y_top_real == 0) {
            range_y_top_real = w;
        }
        if (range_y_bottom_real == 0) {
            range_y_bottom_real = w;
        }

        float x = this.x;
        float y = this.y;
        //计算绘制范围---------------------------
        //计算left,right方向的绘制边界,因为元素朝向左右会改变绘制边界,所以要分开来计算
        //如果朝向左边
        if (x_direction == direction_reduce) {
            //绘制边界
            left = x - range_x_forward_pic * percent_pic;
            right = x + range_x_back_pic * percent_pic;

            //范围
            left_real = x - range_x_forward_real * percent_real;
            right_real = x + range_x_back_real * percent_real;
        } else if (x_direction == direction_add) {
            //绘制边界
            left = x - range_x_back_pic * percent_pic;
            right = x + range_x_forward_pic * percent_pic;

            //范围
            left_real = x - range_x_back_real * percent_real;
            right_real = x + range_x_forward_real * percent_real;
        }
        //计算顶部和底部的绘制边界, 由于元素朝向左右不影响上下的边界,所以左右的计算方式一致
        top = y - (range_y_top_pic + range_y_bottom_pic) * percent_pic;
        bottom = y;
        //元素朝向左右不影响上下的边界
        top_real = y - (range_y_top_real + range_y_bottom_real) * percent_real;
        bottom_real = y;

        for (Pose pose : list) {
            pose.roll();
        }
    }

    public void rollEnd() {
        time_last = time_now;
        time_during = 0;
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
        ZBitmap bitmap_current = null;
        //计算动作--------------------------------------
        if (pictures != null && pictures.length != 0) {
            int index = -1;
            //如果第一次获取pose,默认为0
            if (this.pose_now == null) {
                index = 0;
            } else {
                index = pose_now.getPicIndex();
            }
            if (list.size() > 0 && index < list.size() && index >= 0) {
                this.pose_now = list.get(index);
            } else {
                this.pose_now = new Pose(new int[]{0}, 1, -1) {
                    @Override
                    protected float getPosibility() {
                        return 1;
                    }

                    @Override
                    protected long getDuring_action() {
                        return 1000;
                    }

                    @Override
                    public void onChangeSize(float w, float h) {

                    }
                };
            }
            //获取该图片下标
            int index_pic_now = pose_now.getPicIndex();
            //获取本次应该绘制的bitmap位图
            if (index_pic_now < pictures.length) {
                bitmap_current = pictures[index_pic_now];
            }
        }
        if (bitmap_current == null) {
            bitmap_current = GameParams.getBitmap();
        }

        return bitmap_current;
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        ZBitmap bitmap_current = getCurrentBitmap();
        if (bitmap_current != null) {
            float left = this.left;
            float right = this.right;
            float top = this.top;
            float bottom = this.bottom;
            float left_real = this.left_real;
            float top_real = this.top_real;
            float right_real = this.right_real;
            float bottom_real = this.bottom_real;

            int w_pic = bitmap_current.getW();
            int h_pic = bitmap_current.getH();

            if (w_pic > 0 && h_pic > 0) {
                //计算图片长宽比
                float percent = w_pic / h_pic;
                //计算绘制区域的长宽比
                float w_area_draw = right - left;
                float h_area_draw = bottom - top;
                float percent_canvas = w_area_draw / h_area_draw;
                //防止图片比例失衡,按比例居中缩放
                if (percent < percent_canvas) {
                    w_area_draw = h_area_draw * percent;
                    left = (left + right) / 2 - w_area_draw / 2;
                } else {
                    h_area_draw = w_area_draw / percent;
                    top = (top + bottom) / 2 - h_area_draw / 2;
                }

                Rect rect_pic = new Rect();
                rect_pic.set(0, 0, w_pic, h_pic);

                Rect rect_draw = new Rect();
                rect_draw.set((int) left, (int) top, (int) right, (int) bottom);

                Bitmap bitmap_draw=bitmap_current.getBitmap();
                if (x_direction == direction_reduce) {
                    bitmap_draw=B.overTurnBitmap(bitmap_current.getBitmap());
                }
                canvas.drawBitmap(bitmap_draw, rect_pic, rect_draw, p);
            }

            //实体范围边框绘制的区域
            Rect rect_range = new Rect();
            rect_range.set((int) left_real, (int) top_real, (int) right_real, (int) bottom_real);
            //绘制实体范围边框--------------------------------
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(1);
            canvas.drawRect(rect_range, p);
        }
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawCircle(x, y, 1, p);

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
