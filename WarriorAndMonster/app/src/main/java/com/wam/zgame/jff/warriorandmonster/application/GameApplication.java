package com.wam.zgame.jff.warriorandmonster.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.wam.zgame.jff.warriorandmonster.controller.GameInfo;
import com.wam.zgame.jff.warriorandmonster.controller.GameParams;
import com.wam.zgame.jff.warriorandmonster.tools.B;
import com.wam.zgame.jff.warriorandmonster.tools.GapSizeManager;
import com.wam.zgame.jff.warriorandmonster.tools.TextSizeManager;

/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WindowManager windowmanager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display d = windowmanager.getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        GameParams.w_visual = p.x;// 屏幕长（像素）
        GameParams.h_visual = p.y;// 屏幕宽（像素）
        GameParams.w_px = GameParams.h_visual / 1080f;

        //设置名称字体大小
        TextSizeManager.setTextsize_name(GameParams.w_px * 20);
        //设置名称与body之间的间隙
        GapSizeManager.setGapSize_name_body(GameParams.w_px * 5);
    }
}
