package com.xiaoguang.happytoplay.utils;

import android.util.Log;

/**
 *  打印日志工具类
 *  用于编写代码时和发布后的日志输出控制，只需要修改LEVEL 就可以控制日志的输出
 * Created by 11655 on 2016/9/26
 */
public class LogUtils {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;
    public static final String TAG = "myTag";

    public static void v(String msg) {
        v(TAG,msg);
    }

    public static void d(String msg) {
        d(TAG,msg);
    }

    public static void i(String msg) {
        i(TAG,msg);
    }

    public static void w(String msg) {
        w(TAG,msg);
    }

    public static void e(String msg) {
        e(TAG,msg);
    }

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
