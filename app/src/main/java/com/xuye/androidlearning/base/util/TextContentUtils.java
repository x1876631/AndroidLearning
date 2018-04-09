package com.xuye.androidlearning.base.util;

/**
 * Created by xuye on 2018/4/9.
 * 文本相关工具类
 */

public class TextContentUtils {

    /**
     * double值是否为0
     * 解决0.00...1==0为false的问题....
     */
    public static boolean isDoubleZero(double d) {
        return Math.abs(d) < 1e-6;
    }

    /**
     * double值是否为0
     */
    public static boolean isDoubleZeroTest(double d) {
        //测试了一下，这个没法解决0.00...1==0为false的问题....
        return Double.compare(d, 0) == 0;
    }
}
