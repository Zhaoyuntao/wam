package com.wam.zgame.jff.warriorandmonster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wam.zgame.jff.warriorandmonster.R;


/**
 * Created by zhaoyuntao on 2017/8/30.
 */

public class Activity_login extends Activity_base {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.activity_num =1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        test_login();
    }
    public void test_login(){
        Intent i=new Intent(Activity_login.this,Activity_window_main.class);
        startActivity(i);
        finish();
    }
    public void login(View view){
        Intent i=new Intent(Activity_login.this,Activity_window_main.class);
        startActivity(i);
        finish();
    }
}
