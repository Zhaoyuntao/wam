package com.wam.zgame.jff.warriorandmonster.ui;

import android.os.Bundle;

import com.wam.zgame.jff.warriorandmonster.R;
import com.wam.zgame.jff.warriorandmonster.component.TestMapView;

public class MainActivity extends BaseActivity {

    private TestMapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mapView=findViewById(R.id.mapview);
    }

    @Override
    protected void onDestroy() {
        mapView.closeCalculator();
        super.onDestroy();
    }
}
