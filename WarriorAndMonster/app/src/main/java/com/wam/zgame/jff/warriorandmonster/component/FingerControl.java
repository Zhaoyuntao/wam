package com.wam.zgame.jff.warriorandmonster.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FingerControl extends View {

    public CallBack callBack;
    //绘制模式:圆心或者矩形
    private int mode = 1;
    public static final int MODE_CIRCLE = 1;
    public static final int MODE_RECT = 2;

    // 摇杆初始坐标
    private double x_origin, y_origin;
    // 上一次手指有效坐标
    private double x_move_last, y_move_last;
    // 摇杆总偏移量
    private double x_sum, y_sum;
    // 摇杆每次移动的偏移量
    private double x_change, y_change;
    // 触点半径
    private double radius_pointCircle;

    //控件的大小
    private double width, height;

    //是否需要自动归中
    private boolean returnZero = true;

    public FingerControl(Context context) {
        super(context);
        init();
    }

    public FingerControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FingerControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        x_origin = getWidth() / 2;
        y_origin = getHeight() / 2;
        width = getWidth();
        height = getHeight();
        //计算半径
        radius_pointCircle = width > height ? height / 6 : width / 6;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取控件的大小
        width = getWidth();
        height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        //初始化画笔
        Paint paint = new Paint();

        //计算中心点
        x_origin = width / 2;
        y_origin = height / 2;
        //圆心的半径
        radius_pointCircle = width > height ? height / 6 : width / 6;


        RectF rec_oval = new RectF();
        rec_oval.set(0, 0, (float) width, (float) height);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(55, 00, 00, 00));
        switch (mode) {
            case MODE_CIRCLE:
//                canvas.drawCircle((float) x_origin, (float) y_origin, (float) (width > height ? y_origin : x_origin), paint);
                break;
            case MODE_RECT:
//                canvas.drawRoundRect(rec_oval, 15f, 15f, paint);
                break;
        }


        RectF rec_oval3 = new RectF();
        rec_oval3.set(0, 0, (float) width, (float) height);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(80, 255, 255, 255));
        switch (mode) {
            case MODE_CIRCLE:
//                canvas.drawCircle((float) x_origin, (float) y_origin, (float) (width > height ? y_origin : x_origin), paint);
                break;
            case MODE_RECT:
//                canvas.drawRoundRect(rec_oval3, 15f, 15f, paint);
                break;
        }

        RectF rec_oval2 = new RectF();
        rec_oval2.set((float) (x_origin - radius_pointCircle + x_sum), (float) (y_origin - radius_pointCircle + y_sum), (float) (x_origin + radius_pointCircle + x_sum), (float) (y_origin + radius_pointCircle + y_sum));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(80, 255, 255, 255));

        switch (mode) {
            case MODE_CIRCLE:
//                canvas.drawOval(rec_oval2, paint);
                break;
            case MODE_RECT:
//                canvas.drawRoundRect(rec_oval2, 15f, 15f, paint);
                break;
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(50, 255, 255, 255));

        switch (mode) {
            case MODE_CIRCLE:
//                canvas.drawCircle((float) (x_origin + x_sum), (float) (y_origin + y_sum), (float) radius_pointCircle, paint);
                break;
            case MODE_RECT:
//                canvas.drawRoundRect(rec_oval2, 15f, 15f, paint);
                break;

        }
    }

    public int getX_move() {
        if (Math.abs(x_sum) < x_origin - radius_pointCircle) {
            return (int) ((x_sum / (x_origin - radius_pointCircle)) * 100);
        } else {
            return x_sum > 0 ? -100 : 100;
        }
    }

    public int getY_move() {
        if (Math.abs(y_sum) < y_origin - radius_pointCircle) {
            return (int) ((y_sum / (y_origin - radius_pointCircle)) * 100);
        } else {
            return y_sum > 0 ? -100 : 100;
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x_move_last = event.getX();
                y_move_last = event.getY();
                whenPress();
                break;
            case MotionEvent.ACTION_MOVE:
                x_change = event.getX() - x_move_last;
                y_change = event.getY() - y_move_last;
                x_sum += x_change;
                y_sum += y_change;
                x_move_last = event.getX();
                y_move_last = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (returnZero) {
                    x_move_last = x_origin;
                    y_move_last = y_origin;
                    x_sum = 0;
                    y_sum = 0;
                }
                whenUp();
                break;
        }
        switch (mode) {
            case MODE_CIRCLE:
                if (Math.pow(x_sum, 2) + Math.pow(y_sum, 2) > Math.pow((width > height ? height / 2 : width / 2) - radius_pointCircle, 2)) {
                    x_sum -= x_change;
                    y_sum -= y_change;
                    return true;//不再重绘
                }
                break;
            case MODE_RECT:
                int i = 0;
                if (x_origin - radius_pointCircle + x_sum < 0 || width < x_origin + radius_pointCircle + x_sum) {
                    x_sum -= x_change;
                    i++;
                }
                if (y_origin - radius_pointCircle + y_sum < 0 || height < y_origin + radius_pointCircle + y_sum) {
                    y_sum -= y_change;
                    i++;
                }
                if (i >= 2) {
                    return true;//不再重绘
                }
                break;
        }
        invalidate();
        return true;
    }


    public void setReturnZero(boolean returnZero) {
        this.returnZero = returnZero;
    }

    public void whenPress() {

    }

    public void whenUp() {

    }

    public interface CallBack {
        void send(int x, int y);

        void whenTouch();
    }
}
