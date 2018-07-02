package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.tools.S;

/**
 * Created by zhaoyuntao on 2018/5/15.
 */

public abstract class BaseView extends View {
    public BaseView(Context context) {
        super(context);
        init(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected abstract void init(Context context);

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        S.s( "onFinishInflate"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        S.s( "onAttachedToWindow"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        S.s( "onMeasure"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        S.s( "onSizeChanged"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        S.s( "onLayout"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        S.s( "onDraw"+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        S.s( "onWindowFocusChanged" + "  " + hasWindowFocus+"  w:"+getWidth()+"      h :"+getHeight()+  "   measureW:"+getMeasuredWidth()+"    measureH:"+getMeasuredHeight());

    }
}
