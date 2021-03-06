package com.infonuascape.osrshelper.utils;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by marc_ on 2018-02-05.
 */

public class Logger {
    private static final String TAG = "OSRS Helper";

    public static void add(final String... logs) {
        StringBuilder stringBuilder = new StringBuilder();

        for(String log : logs) stringBuilder.append(log);

        String log = stringBuilder.toString();
        if(Fabric.isInitialized()) {
            Crashlytics.log(log);
        }
        Log.d(TAG, log);
    }
}
