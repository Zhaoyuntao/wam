package com.wam.zgame.jff.warriorandmonster.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.tools.ZThread;


public class FingerControlImpl extends FingerControl {


    private CallBack callBack;

    public FingerControlImpl(Context context) {
        super(context);
    }

    public FingerControlImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerControlImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {

    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void whenPress() {
        if (callBack != null) {
            callBack.whenPress();
        }
    }

    @Override
    protected void whenMove() {
        if (callBack != null) {
            callBack.send(getX_move(), getY_move());
        }
    }

    @Override
    public void whenUp() {
        if (callBack != null) {
            callBack.send(0, 0);
            callBack.whenUp();
        }
    }

    public interface CallBack {
        void whenPress();
        void whenUp();
        void send(int x, int y);
    }
}
