package com.pe.delicias.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesSingleton {
    private static PreferencesSingleton INSTANCE;
    private SharedPreferences sharedPreferences;

    public PreferencesSingleton(Context context) {
        sharedPreferences = context.getSharedPreferences("delicias", Context.MODE_PRIVATE);
    }

    public static PreferencesSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesSingleton(context);
        }
        return INSTANCE;
    }

    public void save(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public String read(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
}
