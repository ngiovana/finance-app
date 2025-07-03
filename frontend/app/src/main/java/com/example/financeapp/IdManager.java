package com.example.financeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class IdManager {
    private static final String PREF_NAME = "user";
    private static final String KEY_ID = "id_user";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void salvarId(int id) {
        if (prefs != null) {
            prefs.edit().putInt(KEY_ID, id).apply();
        }
    }

    public static int getId() {
        if (prefs != null) {
            return prefs.getInt(KEY_ID, -1);
        }
        return -1;
    }

    public static void removerId() {
        if (prefs != null) {
            prefs.edit().remove(KEY_ID).apply();
        }
    }
}
