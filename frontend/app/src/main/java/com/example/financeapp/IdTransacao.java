package com.example.financeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class IdTransacao {
    private static final String PREF_NAME = "transacao";
    private static final String KEY_ID = "id_transacao";
    private static SharedPreferences prefs;
    public static void init_trans(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }
    public static void salvarId_trans(String id) {
        if (prefs != null) {
            prefs.edit().putString(KEY_ID, id).apply();
        }
    }

    public static String getId_trans() {
        if (prefs != null) {
            return prefs.getString(KEY_ID, "-1");
        }
        return "-1";
    }
}