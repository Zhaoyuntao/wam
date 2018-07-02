package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.model.base.Skill;

import java.text.DecimalFormat;
import java.text.Format;


public class Window_skill extends View {

    private int w_view, h_view;
    private float w_skill;
    private float width_border;
    private int number_x = 2;
    private int number_y = 3;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private CallBack callBack;

    public Window_skill(Context context) {
        super(context);
        init(context);
    }

    public Window_skill(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public Window_skill(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
    }

    public void flush() {

    }

    public void flush_back() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(true)
        {
            return;
        }
        w_view = getWidth();
        h_view = getHeight();
        if (w_view == 0 || h_view == 0) {
            return;
        }
        w_skill = (float) h_view / 18 * 5;
        Paint p = new Paint();
        width_border = w_skill / 5;
        canvas.drawColor(Color.argb(10, 0, 0, 0));
        int number_all = number_x * number_y;
        for (int i = 0; i < number_all; i++) {
            float left = w_view - (i % 2 + 1) * w_skill - width_border;
            float top = h_view - (i / 2 + 1) * w_skill - width_border;
            float right = left + w_skill - width_border;
            float bottom = top + w_skill - width_border;
            RectF rect = new RectF();
            rect.set((int) left, (int) top, (int) right, (int) bottom);
            p.setColor(Color.argb(55, 0, 0, 0));
            p.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 10, 10, p);
            p.setColor(Color.argb(80, 255, 255, 255));
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(3);
            canvas.drawRoundRect(rect, 10, 10, p);
//            Skill skill = callBack.getSkill(i);
//            skill.draw(canvas);
//            Rect rect_bitmap = new Rect();
//            int width_bitmap = getWidth();
//            int height_bitmap = bitmap.getHeight();
//            rect_bitmap.set(0, 0, width_bitmap, height_bitmap);
//            RectF rect_draw = new RectF();
//            int width_border = 15;
//            rect_draw.set(left + width_border, top + width_border, right - width_border, bottom - width_border);
//            p.setAlpha(150);
//            canvas.drawBitmap(bitmap, rect_bitmap, rect_draw, p);
//            p.setAlpha(255);
//            long time = skill.time_rest;
//            if (time > 0) {
//                String str_time = "" + time / 1000;
//                if (time < 1000) {
//                    Format format = new DecimalFormat("#.#");
//                    str_time = format.format((float) time / 1000);
//                }
//                int textsize = 50;
//                p.setAntiAlias(true);
//                p.setStyle(Paint.Style.FILL);
//                p.setColor(Color.argb(200, 255, 255, 255));
//                p.setTextSize(textsize);
//                float x_text = (left + right) / 2 - textsize / 2 * ((float) str_time.length() / 2);
//                canvas.drawText(str_time, x_text, (top + bottom) / 2 + textsize / 3, p);
//                p.setStyle(Paint.Style.STROKE);
//                p.setColor(Color.argb(200, 0, 0, 0));
//                p.setStrokeWidth(1);
//                canvas.drawText(str_time, x_text, (top + bottom) / 2 + textsize / 3, p);
//            }
        }

    }

    private int getSkill(float x, float y) {
        int number_all = number_x * number_y;
        for (int i = 0; i < number_all; i++) {
            float left = w_view - (i % 2 + 1) * w_skill - width_border;
            float top = h_view - (i / 2 + 1) * w_skill - width_border;
            float right = left + w_skill - width_border;
            float bottom = top + w_skill - width_border;
            if (x > left && x < right && y > top && y < bottom) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (callBack != null) {
                    float x = event.getX();
                    float y = event.getY();
                    int index = getSkill(x, y);
                    if (index != -1) {
                        callBack.whenPress(index);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public interface CallBack {
        public void whenPress(int number);

        public Bitmap getBitmap_skill(int index);

        public Skill getSkill(int i);
    }
}
