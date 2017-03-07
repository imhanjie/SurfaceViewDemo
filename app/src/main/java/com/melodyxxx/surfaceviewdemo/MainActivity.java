package com.melodyxxx.surfaceviewdemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DynamicWeatherView mDynamicWeatherView = (DynamicWeatherView) findViewById(R.id.dynamic_weather_view);
        mDynamicWeatherView.setType(new RainTypeImpl(this, mDynamicWeatherView));
    }
}
