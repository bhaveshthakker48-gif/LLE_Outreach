package org.impactindia.llemeddocket;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.impactindia.llemeddocket.setting.Settings;

public class LLEApplication extends Application {

    private Settings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        settings = new Settings(this);
    }

    public Settings getSettings() {
        return settings;
    }
}
