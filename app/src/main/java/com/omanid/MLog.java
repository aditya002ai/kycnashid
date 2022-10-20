package com.omanid;

import android.util.Log;

public class MLog {
    public static void d(String msg) {
        Log.d(Constants_java.TAG, msg);
        System.out.println(msg);
    }

    public static void v(String msg) {
        Log.v(Constants_java.TAG, msg);
        System.out.println(msg);
    }

    public static void e(String msg) {
        Log.e(Constants_java.TAG, msg);
        System.err.println(msg);
    }
}
