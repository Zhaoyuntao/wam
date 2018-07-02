package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wam.zgame.jff.warriorandmonster.tools.S;
import com.wam.zgame.jff.warriorandmonster.tools.Sleeper;

/**
 * Created by zhaoyuntao on 2018/5/15.
 */

public class TestMapView extends BaseView implements Runnable {
    private Bitmap bitmapOfMap;
    private Bitmap bitmapOfSmallMap;

    private float x_creature, y_creature;
    private int w_map = 3000;
    private int h_map = 2000;
    private float w_visual;
    private float h_visual;

    //当点距离取景框边界小于这个值的时候，要移动取景框
    private float distance_min_x;
    private float distance_min_y;

    //取景框
    private float left, top, right, bottom;


    public TestMapView(Context context) {
        super(context);
    }

    public TestMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据,
     *
     * @param context
     */
    protected void init(Context context) {
        S.s("init ..."+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());

        sleeper = new Sleeper();

        //初始化地图
        bitmapOfMap = initMap();
        //初始化取景框大小
        initWHOfVisual((float) w_map / 3, (float) h_map / 3);
        //初始化点的位置
        setPositionOfCreature(25, 25);

        //开启计算线层
        createCalculator();
    }

    private Bitmap initMap() {
        w_map = 3000;
        h_map = 2000;
        //初始化地图
        Bitmap bitmapOfMap = Bitmap.createBitmap(w_map, h_map, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapOfMap);
        canvas.drawColor(Color.parseColor("#000000"));
        int w_index = w_map / 10;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        for (int i = 0; i < w_map; i += w_index) {
            for (int j = 0; j < h_map; j += w_index) {
                canvas.drawLine(i, 0, i, h_map, paint);
                canvas.drawLine(0, j, w_map, j, paint);
                canvas.drawText("(" + i + "," + j + ")", i, (j - 5), paint);
            }
        }
        return bitmapOfMap;
    }

    /**
     * 初始化取景框大小
     *
     * @param w
     * @param h
     */
    public void initWHOfVisual(float w, float h) {
        this.w_visual = w;
        this.h_visual = h;
        this.left = 0;
        this.right = left + w_visual;
        this.top = 0;
        this.bottom = top + h_visual;
    }

    /**
     * 设置点的位置
     *
     * @param x_tmp
     * @param y_tmp
     */
    public void setPositionOfCreature(float x_tmp, float y_tmp) {
        if (x_tmp < 0) {
            this.x_creature = 0;
        } else if (x_tmp > w_map) {
            this.x_creature = w_map;
        } else {
            this.x_creature = x_tmp;
        }
        if (y_tmp < 0) {
            this.y_creature = 0;
        } else if (y_tmp > h_map) {
            this.y_creature = h_map;
        } else {
            this.y_creature = y_tmp;
        }
        //初始化取景框位置
        setVisualPosition();
    }

    /**
     * 设置取景框位置
     */
    public void setVisualPosition() {


        float left_visual_tmp = left;
        float top_visual_tmp = top;

        float left_min = 0;
        float left_max = w_map - w_visual;
        float top_min = 0;
        float top_max = h_map - h_visual;

        //点距离取景框四周边界的距离
        float distance_from_left = Math.abs(x_creature - left);
        float distance_from_top = Math.abs(y_creature - top);

        //点距离边界最小距离
        float distance_min_x = w_visual * 0.25f;
        float distance_min_y = h_visual * 0.25f;

        //点距离边界最大距离
        float distance_max_x = w_visual * 0.75f;
        float distance_max_y = h_visual * 0.75f;

        //取景框是否移动
        if (distance_from_left < distance_min_x) {
            left_visual_tmp -= (distance_min_x - distance_from_left);
        } else if (distance_from_left > distance_max_x) {
            left_visual_tmp += (distance_from_left - distance_max_x);
        }
        if (left_visual_tmp < left_min) {
            left_visual_tmp = left_min;
        } else if (left_visual_tmp > left_max) {
            left_visual_tmp = left_max;
        }
        this.left = left_visual_tmp;
        this.right = left + w_visual;

//        S.s("distance_from_top:" + distance_from_top + "   distance_min_y:" + distance_min_y + "  distance_max_y:" + distance_max_y);
        if (distance_from_top < distance_min_y) {
            top_visual_tmp -= (distance_min_y - distance_from_top);
        } else if (distance_from_top > distance_max_y) {
            top_visual_tmp += (distance_from_top - distance_max_y);
        }
//        S.s("top_tmp: " + top_visual_tmp + "   top_min:" + top_min + "  top_max:" + top_max);
        if (top_visual_tmp < top_min) {
            top_visual_tmp = top_min;
        } else if (top_visual_tmp > top_max) {
            top_visual_tmp = top_max;
        }
        this.top = top_visual_tmp;
        this.bottom = top + h_visual;
    }

    /**
     * 以某个矢量移动点
     *
     * @param x_distance_move
     * @param y_distance_move
     */
    public void moveCreature(float x_distance_move, float y_distance_move) {
        float x_tmp = x_creature + x_distance_move;
        float y_tmp = y_creature + y_distance_move;
        setPositionOfCreature(x_tmp, y_tmp);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w_canvas = getWidth();
        int h_canvas = getHeight();
        if (w_canvas == 0 || h_canvas == 0) {
            return;
        }
        Paint paint = new Paint();
        //初始化取景框大小
        Rect rect_visual = new Rect();
//        S.s("left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
        rect_visual.set((int) left, (int) top, (int) right, (int) bottom);


        Rect rect_draw = new Rect();
        rect_draw.set(0, 0, w_canvas, h_canvas);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmapOfMap, rect_visual, rect_draw, paint);

        //绘制点
        paint.setColor(Color.WHITE);

        float percent_draw_x = w_canvas / w_visual;
        float percent_draw_y = h_canvas / h_visual;
        float x_creature_draw = (x_creature - left) * percent_draw_x;
        float y_creature_draw = (y_creature - top) * percent_draw_y;
        canvas.drawCircle(x_creature_draw, y_creature_draw, 50, paint);
        //fps
        paint.setTextSize(20);
        canvas.drawText("" + FPS_map_draw, 50, 50, paint);

        //
        bitmapOfSmallMap = bitmapOfMap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas_tmp = new Canvas(bitmapOfSmallMap);


        //预览图的取景框
        Rect rect_smallMap_visual = new Rect();
        rect_smallMap_visual.set((int) left, (int) top, (int) right, (int) bottom);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(100, 255, 0, 0));
        paint.setStrokeWidth(5);
        canvas_tmp.drawRect(rect_smallMap_visual, paint);

        //预览图绘制点
        paint.setColor(Color.WHITE);
        canvas_tmp.drawCircle(x_creature, y_creature, 50, paint);

        //预览图
        Rect rect_smallMap_src = new Rect();
        rect_smallMap_src.set(0, 0, w_map, h_map);
        Rect rect_smallMap_draw = new Rect();
        float percent_wh = ((float) w_map) / h_map;
        float h_smallMap = h_canvas / 4;
        float w_smallMap = h_smallMap * percent_wh;
        rect_smallMap_draw.set((int) (w_canvas - w_smallMap), 0, w_canvas, ((int) h_smallMap));
//        S.s("(int) (w_canvas - w_smallMap):" + (int) (w_canvas - w_smallMap) + "   w_canvas: " + w_canvas + "  h_smallMap: " + h_smallMap);
//        rect_smallMap_draw.set(0, 0, w_canvas,  h_canvas);
        canvas.drawBitmap(bitmapOfSmallMap, rect_smallMap_src, rect_smallMap_draw, paint);

        //预览图边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        canvas.drawRect(rect_smallMap_draw, paint);
    }

    private float x_down;
    private float y_down;
    private float x_now;
    private float y_now;
    private final float speed_x = 0.3f;
    private final float speed_y = 0.3f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下位置
                x_down = event.getX();
                y_down = event.getY();
                //当前位置
                x_now = event.getX();
                y_now = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算本次比上次的位移
                x_now = event.getX();
                y_now = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //全部参数归零
                x_now = 0;
                y_now = 0;
                x_down = 0;
                y_down = 0;
                break;
        }
        synchronized (sleeper) {
            sleeper.notifyAll();
        }
        return true;
    }


    private Thread thread;


    @Override
    public void destroyDrawingCache() {
        S.s("====== destroyDrawingCache");
        closeCalculator();
        super.destroyDrawingCache();
    }

    @Override
    public void releasePointerCapture() {
        S.s("========= releasePointerCapture");
        super.releasePointerCapture();
    }

    /**
     * 开启计算线程
     */
    private void createCalculator() {
        closeCalculator();
        flag = true;
        pause = false;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * 关闭计算线程
     */
    public void closeCalculator() {
        if (thread != null) {
            flag = false;
            thread.interrupt();
        }
    }

    /**
     * 计算点的位移
     */
    private void calculatePosition() {
        //计算本次位移
        float x_move_now = x_now - x_down;
        float y_move_now = y_now - y_down;

        //计算点临时位置
        float x_move_cal = 0;
        float y_move_cal = 0;
        boolean isMove = false;
        //如果x方向位移大于0
        if (x_move_now > 0) {
            x_move_cal = speed_x;
            isMove = true;
        } else if (x_move_now < 0) {
            x_move_cal = -speed_x;
            isMove = true;
        }

        //如果y方向位移大于0
        if (y_move_now > 0) {
            y_move_cal += speed_y;
            isMove = true;
        } else if (y_move_now < 0) {
            y_move_cal -= speed_y;
            isMove = true;
        }

//        S.s("x_creature:" + x_creature + " y:" + y_creature);
        if (isMove) {
            moveCreature(x_move_cal, y_move_cal);
            //刷新画面
            postInvalidate();
        }
    }

    private Sleeper sleeper;
    //自动移动点的帧率
    float frame_drawMap = 300;
    boolean flag = true;
    boolean pause = false;
    private float FPS_map_draw;

    @Override
    public void run() {

        int during_flushMove = (int) (1000 / 600);
//        S.s("during_flushMove: " + during_flushMove + " 1000/60:  " + (int) (1000 / 600f));
        while (flag) {
//            S.s("----------------------------------------------------------------");
//            if (pause) {
            synchronized (sleeper) {
                try {
                    sleeper.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
//            }
            long time_now = System.currentTimeMillis();

            //执行开始
            calculatePosition();
            //执行结束
//            S.s("-------------------------------------------------------------------");
            long time_end = System.currentTimeMillis();//结束时间
            long during = time_end - time_now;//持续时间

            if (during < during_flushMove) {//限帧操作:如果持续时间小于规定时间
                try {
//                    S.s(" " + during_flushMove + " - " + during + ":" + (during_flushMove - during));
                    Thread.sleep(during_flushMove - during);
                } catch (InterruptedException e) {
                    break;
                }
            }
            during = System.currentTimeMillis() - time_now;
            FPS_map_draw = (float) 1000 / during;
        }
    }


}
