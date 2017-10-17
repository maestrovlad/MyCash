package com.example.vb.mycash;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by VB on 05.08.2017.
 */

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/PTN57F.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
