package com.example.financeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "auth";
    private static final String KEY_TOKEN = "token";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void salvarToken(String token) {
        if (prefs != null) {
            prefs.edit().putString(KEY_TOKEN, token).apply();
        }
    }

    public static String getToken() {
        if (prefs != null) {
            return prefs.getString(KEY_TOKEN, null);
        }
        return null;
    }

    public static void removerToken() {
        if (prefs != null) {
            prefs.edit().remove(KEY_TOKEN).apply();
        }
    }
}
