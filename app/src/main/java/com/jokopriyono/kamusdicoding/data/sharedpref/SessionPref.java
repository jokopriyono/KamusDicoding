package com.jokopriyono.kamusdicoding.data.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionPref {
    private SharedPreferences sharedPreferences;
    private static final String KEY_PAYLOAD = "payload";

    public SessionPref(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setPayload(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_PAYLOAD, true);
        editor.apply();
    }

    public boolean checkPayload(){
        return sharedPreferences.getBoolean(KEY_PAYLOAD, false);
    }
}
