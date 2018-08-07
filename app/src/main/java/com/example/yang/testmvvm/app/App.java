package com.example.yang.testmvvm.app;

import android.app.Application;
import android.content.Context;

import com.example.yang.testmvvm.config.SysCfg;

import org.litepal.LitePal;

public class App extends Application {

    private static Context context;

    public static SysCfg sysCfg;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);

        context = getApplicationContext();

        sysCfg = SysCfg.getInstance(context);
        sysCfg.readConfig();


    }

    public static Context getContext(){
        return context;
    }
}
