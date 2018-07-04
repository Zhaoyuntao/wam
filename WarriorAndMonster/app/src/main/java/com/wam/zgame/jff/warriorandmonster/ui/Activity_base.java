package com.wam.zgame.jff.warriorandmonster.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wam.zgame.jff.warriorandmonster.tools.S;


/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Activity_base extends Activity {
    public int activity_num =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S.s("onCreate"+" "+ activity_num);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        S.s("onSaveInstanceState"+" "+ activity_num);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        S.s("onRestoreInstanceState"+" "+ activity_num);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        S.s("onConfigurationChanged"+" "+ activity_num);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        S.s("onStart"+" "+ activity_num);
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        S.s("onRestart"+" "+ activity_num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        S.s("onResume"+" "+ activity_num);
    }

    @Override
    protected void onPause() {
        super.onPause();
        S.s("onPause"+" "+ activity_num);
    }

    @Override
    protected void onStop() {
        super.onStop();
        S.s("onStop"+" "+ activity_num);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        S.s("onDestroy"+" "+ activity_num);
    }
}
