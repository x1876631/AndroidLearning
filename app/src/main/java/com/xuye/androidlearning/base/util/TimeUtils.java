package com.xuye.androidlearning.base.util;

import java.util.Locale;

/**
 * Created by xuye on 18/1/27
 */

public class TimeUtils {

    public static String getTimeConsuming(long beginTime) {
        double time = (System.currentTimeMillis() - beginTime) / 1000.0;
        return String.format(Locale.getDefault(), "%.2f", time);
    }
}
