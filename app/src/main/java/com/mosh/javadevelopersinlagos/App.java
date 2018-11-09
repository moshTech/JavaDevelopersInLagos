package com.mosh.javadevelopersinlagos;

import android.app.Application;

import ng.codeimpact.javadevelopersinlagos.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ChronicaPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );}
}