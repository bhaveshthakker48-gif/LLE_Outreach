package org.impactindia.llemeddocket.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    /*package*/ static final String IS_FIRST_LAUNCH   = "is_first_launch";
    /*package*/ static final String USER_ID = "user_id";
    /*package*/ static final String USER_NAME = "user_name";
    /*package*/ static final String REG_SERIES = "reg_series";
    /*package*/ static final String CAMP_END_DATE = "camp_end_date";
    /*package*/ static final String CAMP_START_DATE = "camp_start_date";

    private SharedPreferences preferences;

    public Settings(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_FIRST_LAUNCH, isFirstLaunch);
        editor.commit();
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void setUserId(Long userId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(USER_ID, userId);
        editor.commit();
    }

    public Long getUserId() {
        return preferences.getLong(USER_ID, 0);
    }

    public void setActiveUserName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public String getActiveUserName() {
        return preferences.getString(USER_NAME, "");
    }

    public void setRegSeriesCode(String code) {
        SharedPreferences.Editor editor =  preferences.edit();
        editor.putString(REG_SERIES, code);
        editor.commit();
    }

    public String getRegSeriesCode() {
        return preferences.getString(REG_SERIES, null);
    }

    public void setCampEndDate(String campEndDate) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CAMP_END_DATE, campEndDate);
        editor.commit();
    }

    public String getCampEndDate() {
        return preferences.getString(CAMP_START_DATE, null);
    }

    public void setCampStartDate(String campEndDate) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CAMP_END_DATE, campEndDate);
        editor.commit();
    }

    public String getCampStartDate() {
        return preferences.getString(CAMP_START_DATE, null);
    }
}
